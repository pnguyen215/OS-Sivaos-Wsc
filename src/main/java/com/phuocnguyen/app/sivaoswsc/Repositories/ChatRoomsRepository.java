package com.phuocnguyen.app.sivaoswsc.Repositories;

import com.phuocnguyen.app.sivaoswsc.Entitties.ChatRoomsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomsRepository extends JpaRepository<ChatRoomsEntity, Long> {

    boolean existsById(Long id);

    Optional<ChatRoomsEntity> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
}
