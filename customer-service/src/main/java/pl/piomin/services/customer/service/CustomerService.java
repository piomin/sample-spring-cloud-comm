package pl.piomin.services.customer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import pl.piomin.services.customer.model.Account;
import pl.piomin.services.customer.model.Customer;
import pl.piomin.services.customer.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	RestTemplate template;
	@Autowired
	CustomerRepository repository;

	public Customer add(Customer customer) {
		return repository.add(customer);
	}

	public Customer update(Customer customer) {
		return repository.update(customer);
	}

	public Customer findById(Long id) {
		return repository.findById(id);
	}

	public List<Customer> find(List<Long> ids) {
		return repository.find(ids);
	}

	public void delete(Long id) {
		repository.delete(id);
	}

	@HystrixCommand(fallbackMethod = "findCustomerAccountsFallback")
	public List<Account> findCustomerAccounts(Long id) {
		Account[] accounts = template.getForObject("http://account-service/customer/{customerId}", Account[].class, id);
		return Arrays.stream(accounts).collect(Collectors.toList());
	}
	
	public List<Account> findCustomerAccountsFallback(Long id) {
		return new ArrayList<>();
	}
	
}
