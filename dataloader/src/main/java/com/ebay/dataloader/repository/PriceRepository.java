package com.ebay.dataloader.repository;

import com.ebay.dataloader.entity.Price;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Integer> {
    List<Price> findByProductIdIn(List<Integer> productIds);


}