package com.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.entity.Customer;

@Component// tạo ra 1 SPRING BEAN tên customerValidator trong Spring IoC Container 
public class CustomerValidator implements Validator {
	
	public boolean supports(Class clazz) {
		return Customer.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		//@NotEmpty(message = "First name is required.")
		//cách sử dụng ValidationUtils.rejectIfEmptyOrWhitespace tương tự errors.rejectValue
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstname.notempty", "First name is required.!!!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.notempty", "Last name is required.!!!");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.notempty", "Email is required.!!!");
		
		Customer customer = (Customer) target;
		if(customer.getFirstName().length() < 5 || customer.getFirstName().length() > 10) { // ngược lại với >= 5 && <=10
			//errorCode sẽ đọc từ file properties
			errors.rejectValue("firstName", "firstname.size");//nếu errorCode ko tìm thấy thì sẽ báo lỗi
			
			//errors.rejectValue("firstName", "firstname.size123", "first name must be greater than or equal to 5 and less than or equal to 10.!!!");
			//nếu errorCode ko tìm thấy thì sẽ lấy message là first name must be greater than or equal to 5 and less than or equal to 10.!!!
			
		}
		
	}

}
