package com.ai.chat.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.chat.modules.AppUser;
import com.ai.chat.modules.ChatMessage;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long>{
	//List<ChatMessage> findByOrderCreatedAt(AppUser user);

	List<ChatMessage> findByUserOrderByCreatedAtAsc(AppUser user);

}
