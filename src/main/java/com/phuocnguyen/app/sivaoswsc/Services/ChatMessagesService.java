package com.phuocnguyen.app.sivaoswsc.Services;

import com.sivaos.Model.Request.Original.ChatMessagesRequest;
import com.sivaos.Model.Request.Original.ChatRoomsRequest;
import com.sivaos.Model.Response.ChatMessagesResponseDTO;
import com.sivaos.Model.UserDTO;
import com.sivaos.Service.IERSIVAOSGenericService;

import java.util.List;

public interface ChatMessagesService extends IERSIVAOSGenericService<ChatMessagesResponseDTO, Long, Long, ChatMessagesRequest> {

    long countNewMessages(ChatMessagesResponseDTO chatMessagesResponse);

    ChatMessagesResponseDTO findOnesById(Long id);

    List<ChatMessagesResponseDTO> findChatMessages(ChatRoomsRequest chatRoomsRequest, UserDTO userDTO);
}
