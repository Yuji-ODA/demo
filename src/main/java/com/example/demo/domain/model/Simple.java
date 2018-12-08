package com.example.demo.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Simple")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Simple implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    int age;

    @Column
    @CreatedDate
    Timestamp createdAt;
}
