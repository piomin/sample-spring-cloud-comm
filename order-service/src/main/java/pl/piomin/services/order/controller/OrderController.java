package pl.piomin.services.order.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import pl.piomin.services.order.model.Account;
import pl.piomin.services.order.model.Customer;
import pl.piomin.services.order.model.Order;
import pl.piomin.services.order.model.OrderStatus;
import pl.piomin.services.order.model.Product;
import pl.piomin.services.order.repository.OrderRepository;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderRepository repository;	
	@Autowired
	RestTemplate template;
	
	@PostMapping
	public Order prepare(@RequestBody Order order) {
		int price = 0;
		Product[] products = template.postForObject("http://product-service/{id}", order.getProductIds(), Product[].class);
		Customer customer = template.getForObject("http://customer-service/withAccounts/{id}", Customer.class, order.getCustomerId());
		for (Product product : products) 
			price += product.getPrice();
		final int priceDiscounted = priceDiscount(price, customer);
		Optional<Account> account = customer.getAccounts().stream().filter(a -> (a.getBalance() > priceDiscounted)).findFirst();
		if (account.isPresent()) {
			order.setAccountId(account.get().getId());
			order.setStatus(OrderStatus.ACCEPTED);
			order.setPrice(priceDiscounted);
		} else {
			order.setStatus(OrderStatus.REJECTED);
		}
		return order;
	}
	
	@PutMapping("/{id}")
	public Order accept(@PathVariable Long id, @RequestBody Order order) {
		return order;
	}
	
	private int priceDiscount(int price, Customer customer) {
		double discount = 0;
		switch (customer.getType()) {
		case REGULAR:
			discount += 0.05;
			break;
		case VIP:
			discount += 0.1;
			break;
			
		default:
			break;
		}
		int ordersNum = repository.countByCustomerId(customer.getId());
		discount += (ordersNum*0.01);
		return (int) (price - (price * discount));
	}
	
}
