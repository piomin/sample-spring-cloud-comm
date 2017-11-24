package pl.piomin.services.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import pl.piomin.services.customer.model.Customer;
import pl.piomin.services.customer.model.CustomerType;
import pl.piomin.services.customer.repository.CustomerRepository;

@SpringBootApplication
@RibbonClient(name = "account-service")
public class CustomerApplication {

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(CustomerApplication.class).web(true).run(args);
	}

	@Bean
	CustomerRepository repository() {
		CustomerRepository repository = new CustomerRepository();
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1L, "John Scott", CustomerType.NEW));
		customers.add(new Customer(1L, "Adam Smith", CustomerType.REGULAR));
		customers.add(new Customer(1L, "Jacob Ryan", CustomerType.VIP));
		repository.addAll(customers);
		return repository;
	}
	
}
