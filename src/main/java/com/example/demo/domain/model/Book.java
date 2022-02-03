package com.example.demo.domain.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // for initial instantiation
public class Book {

    public static Book of(String name, double price) {
        return new Book(null, name, price, null, null);
    }

    public static Book ofId(Long id) {
        return new Book().withId(id);
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

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
