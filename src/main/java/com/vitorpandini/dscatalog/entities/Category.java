package com.vitorpandini.dscatalog.entities;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor()
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@RequiredArgsConstructor

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products=new HashSet<>();

    @PrePersist
    public void prePersist(){
        createdAt=Instant.now();
    }

    @PreUpdate
    public void preUpdated(){
        updatedAt = Instant.now();
    }


    public Category(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

}
