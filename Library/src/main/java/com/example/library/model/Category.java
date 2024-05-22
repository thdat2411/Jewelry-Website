package com.example.library.model;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "categoryName"))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private boolean categoryIsDeleted;
    private boolean categoryIsActivated;

    public Category(String categoryName) {
        this.categoryName=categoryName;
        this.categoryIsActivated=true;
        this.categoryIsDeleted=false;
    }
}
