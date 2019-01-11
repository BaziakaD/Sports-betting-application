package com.training.sportbetting.repository.jpa;

import com.training.sportbetting.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface extend CrudRepository and add finding user by email functionality.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find user in repository be their email.
     * @param email User email.
     * @return If user in a repository, optional will contains user else null.
     */
    Optional<User> findByEmail(String email);
}
