package com.ebay.dataloader.service;

import com.ebay.dataloader.entity.Price;
import com.ebay.dataloader.entity.Product;
import com.ebay.dataloader.exception.ProductNotFoundException;
import com.ebay.dataloader.repository.PriceRepository;
import com.ebay.dataloader.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;



    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id:"+ id+" does not exist"));
    }

    public Product updateStock(int id , int stock)  {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id:"+ id+" does not exist"));

        existingProduct.setStock(stock);
        return productRepository.save(existingProduct);
    }

    public Product receiveNewShipment(int id , int quantity)  {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id:"+ id+" does not exist"));

        existingProduct.setStock(existingProduct.getStock() + quantity);
        return productRepository.save(existingProduct);
    }
    public CompletableFuture<List<Product>> getProductsByIds(List<Integer> ids) {
        return CompletableFuture.supplyAsync(() -> productRepository.findAllById(ids));
    }

    public Map<Integer, Price> pricesForProducts(List<Integer> productIds) {

        log.info("In prices for products");
        List<Price> allPrices = priceRepository.findByProductIdIn(productIds);

        log.info("Price list,{}",productIds);
       Map<Integer,List<Price>> listt=allPrices.stream()
                .collect(Collectors.groupingBy(x->x.getProduct().getId()));
        return productIds.stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                product ->
                                        allPrices
                                                .stream()
                                                .filter(r -> r.getPriceid().equals(product))
                                                .findFirst().get()
                        ));


    }




}
