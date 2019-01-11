package com.training.sportbetting.repository.jpa;

import com.training.sportbetting.domain.wager.Wager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WagerRepository extends CrudRepository<Wager, Integer> {
    List<Wager> findByPlayerEmail(String email);
}
