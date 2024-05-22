package com.example.library.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"productName","productImage"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String productDesc;
    private double productPrice;
    private int productQuantity;
    private String productType;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String productImage;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId")
    private Category category;
    private boolean productIsDeleted;
    private boolean productIsActivated;
}
