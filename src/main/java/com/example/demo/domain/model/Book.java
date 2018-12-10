package com.example.demo.domain.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column
    @CreatedDate
    private Timestamp createdAt;
}
