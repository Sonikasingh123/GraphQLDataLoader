package com.ebay.dataloader;

import com.ebay.dataloader.entity.Price;
import com.ebay.dataloader.entity.Product;
import com.ebay.dataloader.repository.PriceRepository;
import com.ebay.dataloader.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class DataloaderApplication {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private PriceRepository priceRepository;

	@PostConstruct
	public void initDB() {
		CopyOnWriteArrayList<Product> products;
		products = Stream.of(
						new Product("Laptop", "Electronics", new Price(74999.99f,39999.99f), 50),
						new Product("Smartphone", "Electronics", new Price(39999.99f,7999.99f), 100),
						new Product("Office Chair", "Furniture", new Price(7999.99f,99.99f), 200),
						new Product("Notebook", "Stationery", new Price(99.99f,1999.99f), 500),
						new Product("Desk Lamp", "Furniture", new Price(1999.99f, 100.00f),150))
				.collect(Collectors.toCollection(CopyOnWriteArrayList::new));
		// priceRepository.saveAllAndFlush(products.stream().map(x->x.getPrice()).toList());

		productRepository.saveAll(products);
	}
	public static void main(String[] args) {
		SpringApplication.run(DataloaderApplication.class, args);
	}

}
