package com.ebay.dataloader.controller;

import com.ebay.dataloader.entity.Price;
import com.ebay.dataloader.entity.Product;
import com.ebay.dataloader.exception.ProductNotFoundException;
import com.ebay.dataloader.repository.ProductRepository;
import com.ebay.dataloader.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
@Slf4j
@RestController
public class ProductController {

    private ProductService productService;

    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductService productService,ProductRepository productRepository,BatchLoaderRegistry batchLoaderRegistry) {
        this.productService = productService;
        this.productRepository=productRepository;
        //Data Loader approach
     batchLoaderRegistry
                .forTypePair(Integer.class, Price.class)
                .registerMappedBatchLoader(
                        (productIds, env) -> {
                            log.info("Calling loader for products {}", productIds);
                            Map priceListMap = productService.pricesForProducts(List.copyOf(productIds));
                            return Mono.just(priceListMap);
                        });

    }

    @QueryMapping
    public List<Product> products() {
        return productService.getProducts();
    }

    @QueryMapping
    public Product getProductById(@Argument Integer id ) {
        return productService.getProductById(id);
    }

    @MutationMapping
    public Product updateStock(@Argument int productId, @Argument int stock)  {
        return productService.updateStock(productId, stock);
    }

    @MutationMapping
    public Product receiveNewShipment(@Argument int productId, @Argument int quantity)  {
        return productService.receiveNewShipment(productId, quantity);
    }


   @SchemaMapping(typeName = "Product", field = "price")
    public CompletableFuture<Price> getPrice(Product product, DataLoader<Integer, Price> loader) {
        //  We load price for the current product
      log.info("Fetching price for product, id {}", product.getId());
        return loader.load(product.getId());
    }




}
