package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query(value = "SELECT c FROM Customer c")
	public List<Customer> getAllCustomers();
	
	//JPA sẽ xây dựng sẵn những format -->chỉ cần làm đúng theo format sẽ chạy đc
	//FORMAT: findBy.. , findBy..And.. , count..by.. 
	//dấu .. là tên thuộc tính của Customer với chữ cái đầu ghi hoa, phải ghi đúng 100% thì mới chạy đc
	public Customer findById(int id); //SELECT c from Customer c where c.id = :id;
	
	//dùng cách này ko cần để tham số theo thứ tự
	@Query(value = "SELECT c from Customer c WHERE c.id = :id")
	public Customer getCustomerById(@Param("id") int id);
	
	//tự viết câu SQL
	@Query(value = "select * from Customer where id = :id", nativeQuery = true)
	public Customer getCustomerByIdUsingNativeQuery(@Param("id") int id);
	
	//dùng cách này phải để param theo thứ tự như dấu ?
	//@Query(value = "SELECT c from Customer c WHERE c.id = ?1")
	//public Customer getCustomerById(int id);
	
	//khi UPDATE/INSERT/DELETE chỉ đc trả về void or int -->ko đc trả về Customer
	//nếu trả về int = 1 -->success
	//nếu trả về int = 0 -->fail
	@Modifying//bắt buộc phải có khi thực hiện UPDATE/INSERT/DELETE
	@Query(value = "UPDATE Customer c SET c.firstName = :firstName, c.lastName = :lastName, c.email = :email WHERE c.id = :id")
	public int updateCustomer(@Param("firstName") String firstName, @Param("lastName")String lastName, 
			@Param("email") String email, @Param("id") int id);
	
}