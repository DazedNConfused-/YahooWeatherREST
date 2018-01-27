package com.example.weather.auth.repository;

import com.example.weather.auth.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AccountRepository extends JpaRepository<Account, String> {
  
  Account findByUsername(String username);

}