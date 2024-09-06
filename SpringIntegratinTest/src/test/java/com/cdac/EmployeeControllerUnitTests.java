package com.cdac;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cdac.controller.EmployeeController;
import com.cdac.dao.*;
import com.cdac.model.Employee;
import com.cdac.model.Employees;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerUnitTests 
{ 
	@InjectMocks
	EmployeeController employeeController;
	
	@Mock
	EmployeeRepository employeeRepository;
	
	@Test
	public void testAddEmployee() 
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		Employee employee = new Employee();
		employee.setId(1);
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
		
		Employee employeeToAdd = new Employee("Prajakta", "Patil", "pp@gmail.com");
		ResponseEntity<Object> responseEntity = employeeController.addEmployee(employeeToAdd);
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
		assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
	}
	
	@Test
	public void testFindAll() 
	{
		// given
		Employee employee1 = new Employee("Prajakta", "Patil", "ppp@gmail.com");
		Employee employee2 = new Employee("Jaipur", "cdac", "act@gmail.com");
		List<Employee> list = new ArrayList<Employee>();
		list.addAll(Arrays.asList(employee1, employee2));

		when(employeeRepository.findAll()).thenReturn(list);

		// when
		Employees result = employeeController.getEmployees();

		// then
		assertThat(result.getEmployeeList().size()).isEqualTo(2);
		
		assertThat(result.getEmployeeList().get(0).getFirstName())
						.isEqualTo(employee1.getFirstName());
		
		assertThat(result.getEmployeeList().get(1).getFirstName())
						.isEqualTo(employee2.getFirstName());
	}
}