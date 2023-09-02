package com.vitorpandini.dscatalog.repositories;

import com.vitorpandini.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
