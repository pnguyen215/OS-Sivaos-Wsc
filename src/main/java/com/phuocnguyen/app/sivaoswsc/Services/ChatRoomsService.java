package com.phuocnguyen.app.sivaoswsc.Services;

import com.sivaos.Model.Request.Original.ChatRoomsRequest;
import com.sivaos.Model.Response.ChatRoomsResponseDTO;
import com.sivaos.Model.UserDTO;
import com.sivaos.Service.IERSIVAOSGenericService;

import java.util.Optional;

public interface ChatRoomsService extends IERSIVAOSGenericService<ChatRoomsResponseDTO, Long, Long, ChatRoomsRequest> {

    Optional<String> getChatId(ChatRoomsRequest chatRoomsRequest, UserDTO userDTO, boolean createChatRoomsIfNotExists);
}
