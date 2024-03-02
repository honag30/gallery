package com.springbackendshoppingmall.repository;

import com.springbackendshoppingmall.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByEmailAndPassword(String email, String password);

    Account findAccountById(int id);
}
