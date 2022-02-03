package com.example.demo.domain.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true) // for initial instantiation
public class Book {

    public static Book of(String name, double price, String isbnCode) {
        return new Book(null, name, price, isbnCode, null, null);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @With
    Long id;

    @Column(nullable = false)
    @With
    String name;

    @Column(nullable = false)
    @With
    Double price;

    String isbnCode;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
