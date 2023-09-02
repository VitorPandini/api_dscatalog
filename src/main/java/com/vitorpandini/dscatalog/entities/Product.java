package com.vitorpandini.dscatalog.entities;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor()
@Getter
@Setter

@Entity
@Table(name = "tb_product")
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
    @NonNull
    @Column(columnDefinition = "TEXT")
    private String description;
    @NonNull
    private Double price;
    @NonNull
    private String imgUrl;
    @NonNull
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant date;

    @ManyToMany
    @JoinTable(name = "tb_product_category",joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Category> categories= new HashSet<>();


    public Product(Long id, @NonNull String name, @NonNull String description, @NonNull Double price, @NonNull String imgUrl, @NonNull Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }
}
