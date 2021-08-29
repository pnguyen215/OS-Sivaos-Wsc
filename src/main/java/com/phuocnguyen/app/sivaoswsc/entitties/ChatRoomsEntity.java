package com.phuocnguyen.app.sivaoswsc.entitties;

import com.sivaos.Entities.ChatRoomsBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

import static com.sivaos.Variables.TablesVariable.CHAT_ROOMS;

@Entity
@Table(name = CHAT_ROOMS)
public class ChatRoomsEntity extends ChatRoomsBaseEntity {

    public ChatRoomsEntity() {
        super();
    }
}
