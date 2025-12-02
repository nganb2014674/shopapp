package com.shop.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shop.shopapp.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
