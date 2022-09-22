package com.service;

import java.util.List;

import com.entity.Customer;

public interface CustomerService {

	public List<Customer> getAllCustomers();

	public Customer addNewCustomer(Customer customer);

	public Customer getCustomerById(int id);

	public Customer updateCustomer(Customer customer);

	public void deleteCustomer(int id);

}