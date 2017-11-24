package pl.piomin.services.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.account.repository.AccountRepository;
import pl.piomin.services.account.model.Account;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountRepository repository;
	
	@PostMapping
	public Account add(@RequestBody Account account) {
		return repository.add(account);
	}
	
	@PutMapping
	public Account update(@RequestBody Account account) {
		return repository.update(account);
	}
	
	@GetMapping("/{id}")
	public Account findById(@PathVariable("id") Long id) {
		return repository.findById(id);
	}
	
	@GetMapping("/customer/{customerId}")
	public List<Account> findByCustomerId(@PathVariable("customerId") Long customerId) {
		return repository.findByCustomer(customerId);
	}
	
	@PostMapping("/ids")
	public List<Account> find(@RequestBody List<Long> ids) {
		return repository.find(ids);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		repository.delete(id);
	}
	
}
