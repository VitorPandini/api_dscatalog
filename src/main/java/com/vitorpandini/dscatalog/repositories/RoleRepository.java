package com.vitorpandini.dscatalog.repositories;

import com.vitorpandini.dscatalog.entities.Product;
import com.vitorpandini.dscatalog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
