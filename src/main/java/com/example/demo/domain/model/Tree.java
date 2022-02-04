package com.example.demo.domain.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tree")
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@With
public class Tree {

    public static Tree of(String name) {
        return new Tree(null, name, null, null);
    }

    public static Tree ofId(long id) {
        return new Tree().withId(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @OneToMany(mappedBy = "parent")
    List<Tree> children;

    @ManyToOne
    @JoinTable(
            name = "nodes",
            joinColumns = @JoinColumn(
                    name = "id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "parent_id",
                    referencedColumnName = "id"
            )
    )
    Tree parent;
}
