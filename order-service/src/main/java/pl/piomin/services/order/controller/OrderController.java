package pl.piomin.services.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import pl.piomin.services.order.model.Customer;
import pl.piomin.services.order.model.Order;
import pl.piomin.services.order.model.Product;

@RestController
@RequestMapping("/order")
public class OrderController {

	private List<Order> orders = new ArrayList<>();
	
	@Autowired
	RestTemplate template;
	
	@PostMapping
	public Order prepare(@RequestBody Order order) {
		Product p = template.getForObject("http://product-service/{id}", Product.class, order.getProductId());
		Customer c = template.getForObject("http://customer-service/{id}", Customer.class, order.getCustomerId());
		return order;
	}
	
	@PutMapping("/{id}")
	public Order accept(@PathVariable Long id, @RequestBody Order order) {
		return order;
	}
	
}
