package com.vitorpandini.dscatalog.repositories;

import com.vitorpandini.dscatalog.entities.Category;
import com.vitorpandini.dscatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

}
