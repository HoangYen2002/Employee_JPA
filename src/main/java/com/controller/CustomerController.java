package com.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Customer;
import com.service.CustomerService;
import com.validator.CustomerValidator;

@Controller
//@RestController //@Controller + @ResponseBody
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	@Qualifier("customerServiceImpl") 
	private CustomerService customerService;
	
	@Autowired
	private CustomerValidator customerValidator;
	
	@InitBinder
	private void InitBinder(WebDataBinder webDataBinder) {
		webDataBinder.setValidator(customerValidator);
	}
	
	/*
	Có 3 cách để kiểm tra dữ liệu người dùng nhập trong form có hợp lệ hay ko
	
	Cách 1/
	@InitBinder
	private void InitBinder(WebDataBinder webDataBinder) {
		webDataBinder.setValidator(customerValidator);
	}
	-->dùng cách này thì bắt buộc phải khai báo @Valid trước model muốn kiểm tra, nếu ko sẽ ko kiểm tra. Ví dụ: @Valid Customer customer)
	
	Cách 2/ 
	phải dùng customerValidator.validate(customer, bindingResult) trong method muốn kiểm tra
	-->ko cần khai báo @Valid vào model muốn kiểm tra
	
	Cách 3/
	dùng các annotation đc cung cấp sẵn như @NotEmpty,@Size,... trên các properties
	-->dùng cách này thì bắt buộc phải khai báo @Valid trước model muốn kiểm tra, nếu ko sẽ ko kiểm tra. Ví dụ: @Valid Customer customer)
	*/
	
	@RequestMapping(value = {"/list"}, method = RequestMethod.GET)
	public String getAllCustomers(Model model)  {
		List<Customer> customers = customerService.getAllCustomers();
		model.addAttribute("customers", customers);
		if (customers == null) {
			System.out.println("Empty.");
		}
		return "customers-list";//ko có @ResponseBody -->sẽ hiểu customers-list là 1 view
	}
	
	/*
	//ko dùng modelAttribute
	@GetMapping("/new")
	public String showAddNewCustomerForm(Model model) {
		return "add-new-customer-form";
	}
	
	//localhost:8080/customer-management/customer/insert?firstName=Tom&lastName=Cat&email=tomCat@gmail.com -->
	@PostMapping("/insert")
	public String addNewCustomer(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("email") String email) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setEmail(email);
		Customer customerInserted = customerService.addNewCustomer(customer);
		if (customerInserted != null) {
			System.out.println("Add New Customer Successfully.");
		}
		return "redirect:/customer/list";
	}
	*/
	
	//dùng modelAttribute
	@GetMapping("/new")
	public String showAddNewCustomerForm(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);//ko có bên JSP sẽ báo lỗi
		return "add-new-customer-form";
	}
	
	@PostMapping("/insert")
	//bindingResult phải khai báo ngay sau @Valid Customer customer -->nếu ko sẽ báo lỗi
	public String addNewCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult) {
		//customerValidator.validate(customer, bindingResult);
		if (bindingResult.hasErrors()) {
			return "add-new-customer-form";
		}
		Customer customerInserted = customerService.addNewCustomer(customer);
		if (customerInserted != null) {
			System.out.println("Insert Customer Successfully.");
		}
		return "redirect:/customer/list";
	}
	
	
	//localhost:8080/customer-management/customer/edit?customerId=1 -->
	@GetMapping("/edit")
	public String showEditCustomerForm(@RequestParam("customerId") int id, Model model) {
		Customer customer = customerService.getCustomerById(id);
		model.addAttribute("customer", customer);
		if (customer == null) {
			System.out.println("Not found.");
		}
		return "edit-customer-form";
	}
	
	//localhost:8081/customer-management/customer/edit/1 -->
	@GetMapping("edit/{customerId}")
	public String showEditCustomerForm1(@PathVariable("customerId") int id, Model model) {
		Customer customer = customerService.getCustomerById(id);
		model.addAttribute("customer", customer);
		if (customer == null) {
			System.out.println("Not found.");
		}
		return "edit-customer-form";
	}
	
	/*
	//ko dùng modelAttribute
	@PostMapping("/update")
	public String updateCustomer(@RequestParam("id") int id, @RequestParam("firstName") String firstName, 
			@RequestParam("lastName") String lastName, @RequestParam("email") String email) {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setEmail(email);
		Customer customerUpdated = customerService.updateCustomer(customer);
		if (customerUpdated != null) {
			System.out.println("Update Customer Successfully.");
		}
		return "redirect:/customer/list";
	}
	*/
	
	//dùng modelAttribute
	@PostMapping("/update")
	public String updateCustomer(@ModelAttribute("customer") Customer customer) {
		Customer customerUpdated = customerService.updateCustomer(customer);
		if (customerUpdated != null) {
			System.out.println("Update Customer Successfully.");
		}
		return "redirect:/customer/list";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id) {
		customerService.deleteCustomer(id);
		return "redirect:/customer/list";
	}

}