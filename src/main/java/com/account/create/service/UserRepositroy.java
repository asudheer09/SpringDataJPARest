package com.account.create.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.account.create.model.User;

@Repository
public interface UserRepositroy extends JpaRepository<User, Long>{

}
