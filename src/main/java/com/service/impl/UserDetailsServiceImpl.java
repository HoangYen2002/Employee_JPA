package com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.entity.Role;
import com.entity.User;
import com.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);  //username = pankaj123
        if (user == null) {
            System.out.println("User not found! " + user);
            throw new UsernameNotFoundException("User " + user + " was not found in the database");//nếu không tìm thấy user thì thông báo lỗi
        }

        Set<Role> roles = user.getRoles();//EAGER
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if(roles != null) {
			for (Role role : roles) {//ROLE_ADMIN123
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getName()); //USER123, ADMIN123
				grantedAuthorities.add(grantedAuthority);
			}
			
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),//user.getUsername() -->chỗ nào có thể là user.getEmail()
				//-->phụ thuộc vào username và password nếu username là email thì user.getEmail() -->sẽ lấy email khi khi authen
				grantedAuthorities);
	}
}
