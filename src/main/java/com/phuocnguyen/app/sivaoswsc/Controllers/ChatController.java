package com.phuocnguyen.app.sivaoswsc.Controllers;

import com.phuocnguyen.app.sivaoswsc.Services.ChatMessagesService;
import com.phuocnguyen.app.sivaoswsc.Services.ChatRoomsService;
import com.sivaos.Configurer.SIVAJDBCConnectAutomation.SIVAJDBCConnectConfigurer;
import com.sivaos.Controller.BaseSIVAOSController;
import com.sivaos.Model.Request.Original.ChatMessagesRequest;
import com.sivaos.Model.Request.Original.ChatRoomsRequest;
import com.sivaos.Model.Response.ChatMessagesResponseDTO;
import com.sivaos.Model.Response.Extend.HttpStatusCodesResponseDTO;
import com.sivaos.Model.Response.Original.ChatNotificationResponse;
import com.sivaos.Model.Response.SIVAResponseDTO;
import com.sivaos.Model.UserDTO;
import com.sivaos.Utils.ExchangeUtils;
import com.sivaos.Utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class ChatController extends BaseSIVAOSController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessagesService chatMessagesService;
    private final ChatRoomsService chatRoomsService;
    private final SIVAJDBCConnectConfigurer connectConfigurer;

    @Value("${spring.profiles.active}")
    private String profileActive;

    @Autowired
    public ChatController(
            SimpMessagingTemplate messagingTemplate,
            ChatMessagesService chatMessagesService,
            ChatRoomsService chatRoomsService,
            SIVAJDBCConnectConfigurer connectConfigurer) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessagesService = chatMessagesService;
        this.chatRoomsService = chatRoomsService;
        this.connectConfigurer = connectConfigurer;
    }

    @MessageMapping("/chat")
    public void pushMessage(@Payload ChatMessagesRequest chatMessagesRequest) {
        UserDTO userDTO = snagUserAsQuery(connectConfigurer.getConnection(profileActive));
        ChatNotificationResponse chatNotificationResponse = new ChatNotificationResponse();
        ChatRoomsRequest chatRoomsRequest = new ChatRoomsRequest();

        chatRoomsRequest.setSenderId(chatMessagesRequest.getSenderId());
        chatRoomsRequest.setRecipientId(chatMessagesRequest.getRecipientId());

        Optional<String> chatId = chatRoomsService.getChatId(
                chatRoomsRequest,
                userDTO,
                true
        );

        chatMessagesRequest.setChatId(chatId.orElse(RandomUtils.generateRandomCodeValue(true, 6)));
        /* begin::Find out chat messages response */
        ChatMessagesResponseDTO chatMessagesResponse = chatMessagesService.saveAsPayloads(chatMessagesRequest, userDTO);
        /* end::Find out chat messages response */

        /* begin::Set Notifications */
        chatNotificationResponse.setId(chatMessagesResponse.getId());
        chatNotificationResponse.setSenderId(chatMessagesResponse.getSenderId());
        chatNotificationResponse.setSenderName(chatMessagesResponse.getSenderName());
        /* end::Set Notifications */

        messagingTemplate.convertAndSendToUser(
                ExchangeUtils.exchangeLongToStringUsingLongToString(chatMessagesResponse.getRecipientId()),
                "/queue/messages",
                chatNotificationResponse
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public @ResponseBody
    ResponseEntity<?> countNewMessages(
            @PathVariable Long senderId,
            @PathVariable Long recipientId
    ) {
        ChatMessagesResponseDTO chatMessagesResponse = new ChatMessagesResponseDTO();
        chatMessagesResponse.setSenderId(senderId);
        chatMessagesResponse.setRecipientId(recipientId);
        return new ResponseEntity<>(chatMessagesService.countNewMessages(chatMessagesResponse), HttpStatus.OK);
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public @ResponseBody
    ResponseEntity<?> findChatMessages(
            @PathVariable Long senderId,
            @PathVariable Long recipientId
    ) {
        UserDTO userDTO = snagUserAsQuery(connectConfigurer.getConnection(profileActive));
        ChatRoomsRequest chatRoomsRequest = new ChatRoomsRequest();
        chatRoomsRequest.setSenderId(senderId);
        chatRoomsRequest.setRecipientId(recipientId);
        return new ResponseEntity<>(chatMessagesService.findChatMessages(chatRoomsRequest, userDTO), HttpStatus.OK);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findOneMessage(@PathVariable Long id) {
        if (!chatMessagesService.existsOneById(id)) {
            return new ResponseEntity<>(SIVAResponseDTO.buildSIVAResponse(
                    "Id not found",
                    null,
                    HttpStatusCodesResponseDTO.NOT_IMPLEMENTED), HttpStatus.OK);
        }
        return ResponseEntity
                .ok(chatMessagesService.findOnesById(id));
    }
}
