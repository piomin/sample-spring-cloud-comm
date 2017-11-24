package pl.piomin.services.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import pl.piomin.services.product.model.Product;
import pl.piomin.services.product.repository.ProductRepository;

@SpringBootApplication
@RibbonClients({
	@RibbonClient(name = "account-service"),
	@RibbonClient(name = "customer-service"),
	@RibbonClient(name = "product-service")
})
public class ProductApplication {

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ProductApplication.class).web(true).run(args);
	}

	@Bean
	ProductRepository repository() {
		ProductRepository repository = new ProductRepository();
		List<Product> products = new ArrayList<>();
		products.add(new Product(1L, "Test1", 1000));
		products.add(new Product(2L, "Test2", 1500));
		products.add(new Product(3L, "Test3", 2000));
		products.add(new Product(4L, "Test4", 3000));
		products.add(new Product(5L, "Test5", 1300));
		products.add(new Product(6L, "Test6", 2700));
		products.add(new Product(7L, "Test7", 3500));
		products.add(new Product(8L, "Test8", 1250));
		products.add(new Product(9L, "Test9", 2450));
		products.add(new Product(10L, "Test10", 800));
		repository.addAll(products);
		return repository;
	}

}
