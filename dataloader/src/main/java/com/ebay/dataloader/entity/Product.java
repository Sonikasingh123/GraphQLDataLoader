package com.ebay.dataloader.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull private String name;
    @NonNull private String category;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PRICEID",referencedColumnName = "priceid")
    private Price price;

    @NonNull
    private Integer stock;



}
