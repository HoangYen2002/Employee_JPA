package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Customer;
import com.repository.CustomerRepository;
import com.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired 
	private CustomerRepository customerRepository;
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();//SELECT c FROM Customer c
		//return customerRepository.getAllCustomers();
	}

	public Customer addNewCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer getCustomerById(int id) {
		return customerRepository.findById(id);
		//return customerRepository.getCustomerById(id);
		//return customerRepository.getCustomerByIdUsingNativeQuery(id);
	}
	
	public Customer updateCustomer(Customer customer) {
		return customerRepository.save(customer);
		//return customerRepository.updateCustomer(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getId());
	}

	public void deleteCustomer(int id) {
		customerRepository.deleteById(id);
	}

}