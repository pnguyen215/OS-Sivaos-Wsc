package com.phuocnguyen.app.sivaoswsc.repositories;

import com.phuocnguyen.app.sivaoswsc.entitties.ChatMessagesEntity;
import com.sivaos.Model.ObjectEnumeration.Original.MessagesStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessagesEntity, Long> {

    boolean existsById(Long id);

    List<ChatMessagesEntity> findByChatId(String chatId);

    long countBySenderIdAndRecipientIdAndStatus(Long senderId, Long recipientId, MessagesStatus status);

    List<ChatMessagesEntity> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
}
