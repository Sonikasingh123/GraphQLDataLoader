package com.ebay.dataloader.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates ID for new entities
    private Integer priceid;

    @NonNull
    private Float bid;
    @NonNull
    private Float ask;


    @OneToOne(mappedBy = "price")
    private Product product;
   }
