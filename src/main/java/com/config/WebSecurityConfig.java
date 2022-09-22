package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //kích hoạt việc tích hợp Spring Security với Spring MVC.

/*
Có 3 annotation có thể sử dụng là:
@EnableWebSecurity -->dùng cho bất kỳ web application
@EnableWebMVCSecurity -->dùng cho SpringMVC web application
@EnableWebSecurity = @EnableWebMVCSecurity + Extra features
*/

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/*
   @Autowired
   @Qualifier("userDetailsServiceImpl")
   private UserDetailsService userDetailsService;

    //dùng để mã hoá password của người dùng
    //Ví dụ người dùng nhập password là abc@123 thì nó sẽ mã hoá là $2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu
   
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
       return new BCryptPasswordEncoder();
    }*/

    @Override
	protected void configure(HttpSecurity http) throws Exception {
		//CSRF ( Cross Site Request Forgery) là kĩ thuật tấn công bằng cách sử dụng quyền chứng thực của người sử dụng đối với 1 website khác
		http.csrf().disable(); 
		
		//những url-pattern ko cần phải login -->ai cũng đều truy cập đc những @RequestMapping này
		
		
		// khong can 
        //http.authorizeRequests().antMatchers("/home", "/login123", "/logout123").permitAll(); //mỗi url-pattern cách nhau bởi dấu ,
        
       //Cách 1/
        http.authorizeRequests().antMatchers("/customer/list").hasRole("ADMIN123");
       http.authorizeRequests().antMatchers("/customer/**").hasAnyRole("USER123", "ADMIN123");
       
       //Cách 2/
       // http.authorizeRequests().antMatchers("/customer/list").access("hasRole('ADMIN123')");
       // http.authorizeRequests().antMatchers("/customer/**").access("hasAnyRole('USER123', 'ADMIN123')");
        
        //Cách 3/
        //http.authorizeRequests().antMatchers("/customer/list").hasAuthority("ROLE_ADMIN123");
        //http.authorizeRequests().antMatchers("/customer/**").hasAnyAuthority("ROLE_USER123", "ROLE_ADMIN123");
        
        // Khi người dùng đã login với vai trò user, nhưng cố ý truy cập vào trang admin
        // Ngoại lệ AccessDeniedException sẽ ném ra -->gọi đến @RequestMapping /denied
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/denied");
        
        // Cấu hình cho Login Form.
        http.authorizeRequests().and().formLogin()//nếu ko khai báo sẽ báo lỗi
                // Submit URL của trang login 
        		//mặc định khi submit form sẽ có url là /login hoặc j_spring_security_check tùy theo version của Spring
                .loginProcessingUrl("/j_spring_security_check") //đường dẫn mặc định được gọi khi bấm submit trong form login
                .loginPage("/login123")//gọi đến @RequestMapping /login123, nếu ko khai báo thì sẽ sử dụng trang login mặc định của Spring security
                .defaultSuccessUrl("/customer/list")//khi đăng nhập thành công thì gọi @RequestMapping /customer/list
                .failureUrl("/login123?error=true")// khi đăng nhập sai username và password thì gọi đến @RequestMapping /login123?error=true
                .usernameParameter("username123")// tham số này nhận từ form login123 có input name='username123'
                .passwordParameter("password123")// tham số này nhận từ form login123 có input name='password123'
                // Cấu hình cho Logout Page. Khi logout mình trả về trang
                .and()
                .logout()
                .logoutUrl("/j_spring_security_logout")
                .logoutSuccessUrl("/home"); //khi đăng xuất thành công thì gọi @RequestMapping /home
     
                
        //tất cả những url-pattern đều phải login
        //phải để dưới cùng = antMatchers("/*")
        //http.authorizeRequests().anyRequest().authenticated(); 
	}
   
    
    //tên của method: registerGlobal, configureGlobal, configureGlobalSecurity -->ko quan trọng
    //quan trọng phải có đủ 3 điều kiện
    //1. @Autowired method này
    //2. class phải có 1 trong 4 annotation:  @EnableWebSecurity, @EnableWebMvcSecurity, @EnableGlobalMethodSecurity, @EnableGlobalAuthentication
    //3. method phải có 1 tham số là: AuthenticationManagerBuilder auth
    @Autowired
    public void configureGlobal123(AuthenticationManagerBuilder auth) throws Exception {
    	//nếu username, password trong inmemory ko đúng thì sẽ tiếp tục time kiếm trong userDetailsService
    	// Các User trong bộ nhớ (MEMORY).
    	//Lưu ý
    	//việc đặt tên role ko quan trọng, miễn là ở trong đây đặt role tên gì thì khi dùng .access, .hasAuthority, .hasRole, .hasAnyRole phải để để tên tương ứng
        //{noop}pankaj123 = %7Bnoop%7Dpankaj123
    	
    	//auth.inMemoryAuthentication().passwordEncoder(bCryptPasswordEncoder()).withUser("pankaj123").password("pankaj123").roles("ADMIN123");
    	auth.inMemoryAuthentication().withUser("pankaj123").password("{noop}pankaj123").roles("ADMIN123");
        // 
       
    	//auth.inMemoryAuthentication().withUser("pankaj456").password("pankaj456").authorities("ROLE_USER123", "ROLE_ADMIN123");//phải có ROLE_
    	
    	// Các User trong Database
    	//Cách 1/
        //auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());//giải mã
        
    	//Cách 2
    	//auth.authenticationProvider(authProvider());
    }

    //có thể @Override method này, sẽ giống như configureGlobal123
    /*
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.authenticationProvider(authProvider());
    }
    */
    
    /*
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }*/
    
    /*
     SHOW CREATE TABLE ROLE1;
SHOW CREATE TABLE USER1;

INSERT INTO USER1(USERNAME,PASSWORD) VALUES("Tom", 123);
INSERT INTO ROLE1(name,user_id) VALUES("USER123", 1);
INSERT INTO ROLE1(name,user_id) VALUES("ADMIN123", 1);

UPDATE USER1 SET PASSWORD = '$2a$12$ggQmF8Bfdlq6YRF3acoYmuKWVw5Agiv2HR4YHMJgOV2JkcbBnjZH2'

SET SQL_SAFE_UPDATES = 0;
*/
    
    
}
