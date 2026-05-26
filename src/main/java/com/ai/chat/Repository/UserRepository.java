package com.ai.chat.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.chat.modules.AppUser;


@Repository

public interface UserRepository extends JpaRepository<AppUser, Long>{ 
	Optional<AppUser> findByUsername(String username);

}
