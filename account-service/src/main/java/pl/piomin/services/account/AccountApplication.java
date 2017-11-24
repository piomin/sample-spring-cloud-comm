package pl.piomin.services.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import pl.piomin.services.account.model.Account;
import pl.piomin.services.account.repository.AccountRepository;

@SpringBootApplication
@RibbonClients({
	@RibbonClient(name = "account-service"),
	@RibbonClient(name = "customer-service"),
	@RibbonClient(name = "product-service")
})
public class AccountApplication {

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(AccountApplication.class).web(true).run(args);
	}
	
	@Bean
	AccountRepository repository() {
		AccountRepository repository = new AccountRepository();
		List<Account> accounts = new ArrayList<>();
		accounts.add(new Account(1L, "1234567890", 5000, 1L));
		accounts.add(new Account(2L, "1234567891", 5000, 1L));
		accounts.add(new Account(3L, "1234567892", 0, 1L));
		accounts.add(new Account(4L, "1234567893", 5000, 2L));
		accounts.add(new Account(5L, "1234567894", 0, 2L));
		accounts.add(new Account(6L, "1234567895", 5000, 2L));
		accounts.add(new Account(7L, "1234567896", 0, 3L));
		accounts.add(new Account(8L, "1234567897", 5000, 3L));
		accounts.add(new Account(9L, "1234567898", 5000, 3L));
		repository.addAll(accounts);
		return repository;
	}

}
