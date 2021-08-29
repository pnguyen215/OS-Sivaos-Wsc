package com.phuocnguyen.app.sivaoswsc.entitties;

import com.sivaos.Entities.ChatMessagesBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

import static com.sivaos.Variables.TablesVariable.CHAT_MESSAGES;

@Entity
@Table(name = CHAT_MESSAGES)
public class ChatMessagesEntity extends ChatMessagesBaseEntity {

    public ChatMessagesEntity() {
        super();
    }
}
