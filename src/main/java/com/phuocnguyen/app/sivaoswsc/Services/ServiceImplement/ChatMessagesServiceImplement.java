package com.phuocnguyen.app.sivaoswsc.Services.ServiceImplement;

import com.phuocnguyen.app.sivaoswsc.Entitties.ChatMessagesEntity;
import com.phuocnguyen.app.sivaoswsc.Repositories.ChatMessagesRepository;
import com.phuocnguyen.app.sivaoswsc.Services.ChatMessagesService;
import com.phuocnguyen.app.sivaoswsc.Services.ChatRoomsService;
import com.sivaos.Exception.ResourceNotFoundException;
import com.sivaos.Model.ObjectEnumeration.Original.MessagesStatus;
import com.sivaos.Model.Request.Original.ChatMessagesRequest;
import com.sivaos.Model.Request.Original.ChatRoomsRequest;
import com.sivaos.Model.Request.PageAttributeOrderRequestDTO;
import com.sivaos.Model.Request.PageRequestDTO;
import com.sivaos.Model.Request.PdfRequestDTO;
import com.sivaos.Model.Response.ChatMessagesResponseDTO;
import com.sivaos.Model.UserDTO;
import com.sivaos.Utility.CollectionsUtility;
import com.sivaos.Utility.StringUtility;
import com.sivaos.Utils.LoggerUtils;
import com.sivaos.Utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "chatMessagesService")
@Transactional
public class ChatMessagesServiceImplement implements ChatMessagesService {

    private static final Logger logger = LoggerFactory.getLogger(ChatMessagesServiceImplement.class);
    private final ChatMessagesRepository chatMessagesRepository;
    private final ChatRoomsService chatRoomsService;

    @Autowired
    public ChatMessagesServiceImplement(ChatMessagesRepository chatMessagesRepository, ChatRoomsService chatRoomsService) {
        this.chatMessagesRepository = chatMessagesRepository;
        this.chatRoomsService = chatRoomsService;
    }


    @Override
    public ChatMessagesResponseDTO findOneById(Long aLong, Long aLong2) {
        return null;
    }

    @Override
    public ChatMessagesResponseDTO saveAsPayloads(ChatMessagesRequest chatMessagesRequest) {
        return null;
    }

    private ChatMessagesEntity preSave(ChatMessagesRequest chatMessagesRequest, UserDTO userDTO) {
        ChatMessagesEntity chatMessagesEntity = new ChatMessagesEntity();
        chatMessagesEntity.setCreatedBy(userDTO.getId());
        chatMessagesEntity.setModifiedBy(userDTO.getId());
        chatMessagesEntity.setOwner(userDTO.getId());
        chatMessagesEntity.setStatus(MessagesStatus.RECEIVED);
        chatMessagesEntity.setChatId(StringUtility.trimSingleWhitespace(chatMessagesRequest.getChatId()));
        chatMessagesEntity.setSenderId(chatMessagesRequest.getSenderId());
        chatMessagesEntity.setSenderName(StringUtility.trimSingleWhitespace(chatMessagesRequest.getSenderName()));
        chatMessagesEntity.setSenderJsonUser(LoggerUtils.toJson(userDTO));
        chatMessagesEntity.setRecipientId(chatMessagesRequest.getRecipientId());
        chatMessagesEntity.setRecipientName(StringUtility.trimSingleWhitespace(chatMessagesRequest.getRecipientName()));
        chatMessagesEntity.setContent(StringUtility.trimSingleWhitespace(chatMessagesRequest.getContent()));
        chatMessagesEntity.setTimestamp(new Date());
        return chatMessagesEntity;
    }

    @Override
    public ChatMessagesResponseDTO saveAsPayloads(ChatMessagesRequest chatMessagesRequest, UserDTO userDTO) {
        ChatMessagesEntity chatMessagesEntity = preSave(chatMessagesRequest, userDTO);
        chatMessagesRepository.save(chatMessagesEntity);
        return findOneById(chatMessagesEntity);
    }

    @Override
    public void saves(ChatMessagesRequest chatMessagesRequest) {

    }

    @Override
    public void saves(ChatMessagesRequest chatMessagesRequest, UserDTO userDTO) {
        ChatMessagesEntity chatMessagesEntity = preSave(chatMessagesRequest, userDTO);
        chatMessagesRepository.save(chatMessagesEntity);
    }

    @Override
    public byte[] exportExcelUserEnabled(String s, Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelUserArchived(String s, Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelUserDisabled(String s, Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public ByteArrayInputStream exportCSVUserEnabled(Long aLong, List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVUserDisabled(Long aLong, List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVUserArchived(Long aLong, List<Long> list) {
        return null;
    }

    @Override
    public byte[] exportJsonUserEnabled(Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonUserDisabled(Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonUserArchived(Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public List<ChatMessagesResponseDTO> findAll() {
        return null;
    }

    @Override
    public List<ChatMessagesResponseDTO> findAllByPayload(ChatMessagesResponseDTO chatMessagesResponseDTO) {
        return null;
    }

    @Override
    public List<ChatMessagesResponseDTO> findAllByPayload(ChatMessagesResponseDTO chatMessagesResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public ChatMessagesResponseDTO saveAsPayload(ChatMessagesResponseDTO chatMessagesResponseDTO) {
        return null;
    }

    @Override
    public ChatMessagesResponseDTO saveAsPayload(ChatMessagesResponseDTO chatMessagesResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public void save(ChatMessagesResponseDTO chatMessagesResponseDTO) {

    }

    @Override
    public void save(ChatMessagesResponseDTO chatMessagesResponseDTO, UserDTO userDTO) {

    }

    @Override
    public ChatMessagesResponseDTO updateAsPayload(ChatMessagesResponseDTO chatMessagesResponseDTO) {
        return null;
    }

    @Override
    public ChatMessagesResponseDTO updateAsPayload(ChatMessagesResponseDTO chatMessagesResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public void update(ChatMessagesResponseDTO chatMessagesResponseDTO) {
        if (!ObjectUtils.allNotNull(chatMessagesResponseDTO.getSenderId(),
                chatMessagesResponseDTO.getRecipientId())) {
            logger.error("[ERROR-1] Can't continue cause's senderId or recipientId is null!");
            return;
        }

        List<ChatMessagesEntity> chatMessagesEntities = chatMessagesRepository.findBySenderIdAndRecipientId(
                chatMessagesResponseDTO.getSenderId(),
                chatMessagesResponseDTO.getRecipientId());

        if (CollectionsUtility.isEmpty(chatMessagesEntities)) {
            logger.warn("[WARN-1] Messages is empty then return!");
            return;
        }

        for (ChatMessagesEntity chatMessagesEntity : chatMessagesEntities) {
            if (StringUtility.isNotEmpty(chatMessagesResponseDTO.getStatus())) {
                chatMessagesEntity.setStatus(StringUtility.isEmpty(chatMessagesResponseDTO.getStatus()) ?
                        MessagesStatus.DELIVERED :
                        MessagesStatus.valueOf(StringUtility.trimSingleWhitespace(chatMessagesResponseDTO.getStatus().toUpperCase())));
                chatMessagesRepository.save(chatMessagesEntity);
            } else {
                logger.warn("[WARN-2] Can't update status of messages! Cause's input 'Status' is null!");
            }
        }

    }

    @Override
    public void update(ChatMessagesResponseDTO chatMessagesResponseDTO, UserDTO userDTO) {

    }

    @Override
    public ChatMessagesResponseDTO updateAsPayload(Long id, ChatMessagesResponseDTO chatMessagesResponseDTO) {
        return null;
    }

    @Override
    public ChatMessagesResponseDTO updateAsPayload(Long id, ChatMessagesResponseDTO chatMessagesResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public void update(Long aLong, ChatMessagesResponseDTO chatMessagesResponseDTO) {

    }

    @Override
    public void update(Long aLong, ChatMessagesResponseDTO chatMessagesResponseDTO, UserDTO userDTO) {

    }

    /**
     * @param id - id messages
     * @apiNote - find messages via id. After that update status DELIVERED
     */
    @Override
    public ChatMessagesResponseDTO findOnesById(Long id) {
        if (!existsOneById(id)) {
            return new ChatMessagesResponseDTO();
        }
        return chatMessagesRepository.findById(id).map(chatMessage -> {
            chatMessage.setStatus(MessagesStatus.DELIVERED);
            return findOneById(chatMessagesRepository.save(chatMessage).getId());
        }).orElseThrow(() ->
                new ResourceNotFoundException("Can't find message (" + id + ")"));
    }

    @Override
    public List<ChatMessagesResponseDTO> findChatMessages(ChatRoomsRequest chatRoomsRequest, UserDTO userDTO) {
        Optional<String> chatId = chatRoomsService.getChatId(chatRoomsRequest, userDTO, false);

        /*
                List<List<ChatMessagesEntity>> messages = chatId.stream().map(chatMessageId -> chatMessagesRepository.findByChatId(chatMessageId))
                .collect(Collectors.toList());
        */

        List<ChatMessagesEntity> messages = chatId.map(chatMessagesRepository::findByChatId).orElse(Collections.emptyList());

        /* begin::Set body update status for messages */
        if (CollectionsUtility.isNotEmpty(messages)) {
            ChatMessagesResponseDTO chatMessagesRequest = new ChatMessagesResponseDTO();
            chatMessagesRequest.setSenderId(chatRoomsRequest.getSenderId());
            chatMessagesRequest.setRecipientId(chatRoomsRequest.getRecipientId());
            chatMessagesRequest.setStatus(MessagesStatus.DELIVERED.getName().toUpperCase());
            update(chatMessagesRequest);
        }
        /* end::Set body update status for messages */

        return CollectionsUtility.isEmpty(messages) ? Collections.emptyList() :
                messages.stream().map(this::findOneById).collect(Collectors.toList());
    }

    @Override
    public ChatMessagesResponseDTO findOneById(Long id) {
        ChatMessagesEntity chatMessagesEntity = chatMessagesRepository.getOne(id);
        return findOneById(chatMessagesEntity);
    }

    public ChatMessagesResponseDTO findOneById(ChatMessagesEntity chatMessagesEntity) {
        ChatMessagesResponseDTO chatMessagesResponse = new ChatMessagesResponseDTO();
        chatMessagesResponse.setId(chatMessagesEntity.getId());
        chatMessagesResponse.setDeleted(chatMessagesEntity.getDeleted());
        chatMessagesResponse.setArchived(chatMessagesEntity.getArchived());
        chatMessagesResponse.setOwner(chatMessagesEntity.getOwner());
        chatMessagesResponse.setCreatedTime(chatMessagesEntity.getCreatedTime());
        chatMessagesResponse.setCreatedBy(chatMessagesEntity.getCreatedBy());
        chatMessagesResponse.setModifiedTime(chatMessagesEntity.getModifiedTime());
        chatMessagesResponse.setModifiedBy(chatMessagesEntity.getModifiedBy());
        chatMessagesResponse.setDescription(chatMessagesEntity.getDescription());
        chatMessagesResponse.setChatId(chatMessagesEntity.getChatId());
        chatMessagesResponse.setSenderId(chatMessagesEntity.getSenderId());
        chatMessagesResponse.setSenderName(chatMessagesEntity.getSenderName());
        chatMessagesResponse.setSenderJsonUser(chatMessagesEntity.getSenderJsonUser());
        chatMessagesResponse.setRecipientId(chatMessagesEntity.getRecipientId());
        chatMessagesResponse.setRecipientName(chatMessagesEntity.getRecipientName());
        chatMessagesResponse.setContent(chatMessagesEntity.getContent());
        chatMessagesResponse.setTimestamp(chatMessagesEntity.getTimestamp());
        chatMessagesResponse.setStatus(String.valueOf(chatMessagesEntity.getStatus().getName()));
        return chatMessagesResponse;
    }

    @Override
    public ChatMessagesResponseDTO findOneById(Long aLong, UserDTO userDTO) {
        return null;
    }

    @Override
    public List<ChatMessagesResponseDTO> finAllByPreconditions(Long aLong) {
        return null;
    }

    @Override
    public void deleteOneById(Long id) {
        if (existsOneById(id)) {
            logger.error("[ERROR] Can not delete with id: {}", id);
            return;
        }
        chatMessagesRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        chatMessagesRepository.deleteAll();
    }

    @Override
    public Long countAll() {
        return chatMessagesRepository.count();
    }

    @Override
    public Page<ChatMessagesResponseDTO> findPage(int i, int i1) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findPages(int i, int i1, String s, String s1) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findPages(int i, int i1, Date date, Date date1, String s, String s1) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findPages(int i, int i1, Date date, String s, String s1) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findPages(int i, int i1, Date date, String s) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findPages(int i, int i1, String s, String s1, String s2) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findPages(int i, int i1, String s, String[] strings, String s1) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findPages(int i, int i1, Object o, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findPages(int i, int i1, String[] strings) {
        return null;
    }

    @Override
    public Page<List<ChatMessagesResponseDTO>> findPages(int i, int i1) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findPages(PageRequestDTO pageRequestDTO) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findPages(PageRequestDTO pageRequestDTO, PageAttributeOrderRequestDTO pageAttributeOrderRequestDTO) {
        return null;
    }

    @Override
    public Boolean existsOneById(Long id) {
        if (!ObjectUtils.allNotNull(id)) {
            return false;
        }
        return chatMessagesRepository.existsById(id);
    }

    @Override
    public Page<ChatMessagesResponseDTO> findEnabled(int i, int i1, String s, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findDisabled(int i, int i1, String s, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findArchived(int i, int i1, String s, String[] strings) {
        return null;
    }

    @Override
    public byte[] exportExcelEnabled(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelDisabled(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelArchived(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelUserEnabled(String s, String s1, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelUserArchived(String s, String s1, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelUserDisabled(String s, String s1, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelTemplate(String s) {
        return new byte[0];
    }

    @Override
    public ByteArrayInputStream exportCSVTemplate() {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVTemplateWith(List<ChatMessagesResponseDTO> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVEnabled(List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVDisabled(List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVArchived(List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVUserEnabled(String s, List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVUserDisabled(String s, List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVUserArchived(String s, List<Long> list) {
        return null;
    }

    @Override
    public byte[] exportJsonTemplateWith(List<ChatMessagesResponseDTO> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonEnabled(List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonDisabled(List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonArchived(List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonUserEnabled(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonUserDisabled(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonUserArchived(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public ByteArrayInputStream exportPdfTemplateWith(List<ChatMessagesResponseDTO> list, PdfRequestDTO pdfRequestDTO) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportPdfEnabled(List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportPdfDisabled(List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportPdfArchived(List<Long> list) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findUserEnabled(int i, int i1, String s, String s1, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findUserDisabled(int i, int i1, String s, String s1, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findUserArchived(int i, int i1, String s, String s1, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findUserEnabled(int i, int i1, String s, UserDTO userDTO, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findUserDisabled(int i, int i1, String s, UserDTO userDTO, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatMessagesResponseDTO> findUserArchived(int i, int i1, String s, UserDTO userDTO, String[] strings) {
        return null;
    }

    @Override
    public long countNewMessages(ChatMessagesResponseDTO chatMessagesResponse) {
        if (!ObjectUtils.allNotNull(chatMessagesResponse.getSenderId(), chatMessagesResponse.getRecipientId())) {
            return 0;
        }
        return chatMessagesRepository.countBySenderIdAndRecipientIdAndStatus(
                chatMessagesResponse.getSenderId(),
                chatMessagesResponse.getRecipientId(),
                MessagesStatus.RECEIVED);
    }
}
