package com.phuocnguyen.app.sivaoswsc.Services.ServiceImplement;

import com.phuocnguyen.app.sivaoswsc.Entitties.ChatRoomsEntity;
import com.phuocnguyen.app.sivaoswsc.Repositories.ChatRoomsRepository;
import com.phuocnguyen.app.sivaoswsc.Services.ChatRoomsService;
import com.sivaos.Entities.ChatRoomsBaseEntity;
import com.sivaos.Model.Request.Original.ChatRoomsRequest;
import com.sivaos.Model.Request.PageAttributeOrderRequestDTO;
import com.sivaos.Model.Request.PageRequestDTO;
import com.sivaos.Model.Request.PdfRequestDTO;
import com.sivaos.Model.Response.ChatRoomsResponseDTO;
import com.sivaos.Model.UserDTO;
import com.sivaos.Utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service(value = "chatRoomsService")
@Transactional
public class ChatRoomsServiceImplement implements ChatRoomsService {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoomsServiceImplement.class);
    private final ChatRoomsRepository chatRoomsRepository;

    @Autowired
    public ChatRoomsServiceImplement(ChatRoomsRepository chatRoomsRepository) {
        this.chatRoomsRepository = chatRoomsRepository;
    }


    @Override
    public ChatRoomsResponseDTO findOneById(Long aLong, Long aLong2) {
        return null;
    }

    @Override
    public ChatRoomsResponseDTO saveAsPayloads(ChatRoomsRequest chatRoomsRequest) {
        return null;
    }


    private ChatRoomsEntity preSave(ChatRoomsRequest chatRoomsRequest, UserDTO userDTO) {
        ChatRoomsEntity chatRoomsEntity = new ChatRoomsEntity();
        chatRoomsEntity.setCreatedBy(userDTO.getId());
        chatRoomsEntity.setModifiedBy(userDTO.getId());
        chatRoomsEntity.setOwner(userDTO.getId());
        chatRoomsEntity.setChatId(chatRoomsRequest.getChatId());
        chatRoomsEntity.setSenderId(chatRoomsRequest.getSenderId());
        chatRoomsEntity.setRecipientId(chatRoomsRequest.getRecipientId());
        return chatRoomsEntity;
    }

    @Override
    public ChatRoomsResponseDTO saveAsPayloads(ChatRoomsRequest chatRoomsRequest, UserDTO userDTO) {
        ChatRoomsEntity chatRoomsEntity = preSave(chatRoomsRequest, userDTO);
        chatRoomsRepository.save(chatRoomsEntity);
        return findOneById(chatRoomsEntity);
    }

    @Override
    public void saves(ChatRoomsRequest chatRoomsRequest) {

    }

    @Override
    public void saves(ChatRoomsRequest chatRoomsRequest, UserDTO userDTO) {
        ChatRoomsEntity chatRoomsEntity = preSave(chatRoomsRequest, userDTO);
        chatRoomsRepository.save(chatRoomsEntity);
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
    public List<ChatRoomsResponseDTO> findAll() {
        return null;
    }

    @Override
    public List<ChatRoomsResponseDTO> findAllByPayload(ChatRoomsResponseDTO chatRoomsResponseDTO) {
        return null;
    }

    @Override
    public List<ChatRoomsResponseDTO> findAllByPayload(ChatRoomsResponseDTO chatRoomsResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public ChatRoomsResponseDTO saveAsPayload(ChatRoomsResponseDTO chatRoomsResponseDTO) {
        return null;
    }

    @Override
    public ChatRoomsResponseDTO saveAsPayload(ChatRoomsResponseDTO chatRoomsResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public void save(ChatRoomsResponseDTO chatRoomsResponseDTO) {

    }

    @Override
    public void save(ChatRoomsResponseDTO chatRoomsResponseDTO, UserDTO userDTO) {

    }

    @Override
    public ChatRoomsResponseDTO updateAsPayload(ChatRoomsResponseDTO chatRoomsResponseDTO) {
        return null;
    }

    @Override
    public ChatRoomsResponseDTO updateAsPayload(ChatRoomsResponseDTO chatRoomsResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public void update(ChatRoomsResponseDTO chatRoomsResponseDTO) {

    }

    @Override
    public void update(ChatRoomsResponseDTO chatRoomsResponseDTO, UserDTO userDTO) {

    }

    @Override
    public ChatRoomsResponseDTO updateAsPayload(Long aLong, ChatRoomsResponseDTO chatRoomsResponseDTO) {
        return null;
    }

    @Override
    public ChatRoomsResponseDTO updateAsPayload(Long aLong, ChatRoomsResponseDTO chatRoomsResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public void update(Long id, ChatRoomsResponseDTO chatRoomsResponseDTO) {

    }

    @Override
    public void update(Long id, ChatRoomsResponseDTO chatRoomsResponseDTO, UserDTO userDTO) {

    }

    @Override
    public ChatRoomsResponseDTO findOneById(Long id) {
        ChatRoomsEntity chatRoomsEntity = chatRoomsRepository.getOne(id);
        return findOneById(chatRoomsEntity);
    }

    public ChatRoomsResponseDTO findOneById(ChatRoomsEntity chatRoomsEntity) {
        ChatRoomsResponseDTO chatRoomsResponse = new ChatRoomsResponseDTO();
        chatRoomsResponse.setId(chatRoomsEntity.getId());
        chatRoomsResponse.setDeleted(chatRoomsEntity.getDeleted());
        chatRoomsResponse.setArchived(chatRoomsEntity.getArchived());
        chatRoomsResponse.setDescription(chatRoomsEntity.getDescription());
        chatRoomsResponse.setCreatedBy(chatRoomsEntity.getCreatedBy());
        chatRoomsResponse.setModifiedBy(chatRoomsEntity.getModifiedBy());
        chatRoomsResponse.setCreatedTime(chatRoomsEntity.getCreatedTime());
        chatRoomsResponse.setModifiedTime(chatRoomsEntity.getModifiedTime());
        chatRoomsResponse.setOwner(chatRoomsEntity.getOwner());
        chatRoomsResponse.setChatId(chatRoomsEntity.getChatId());
        chatRoomsResponse.setSenderId(chatRoomsEntity.getSenderId());
        chatRoomsResponse.setRecipientId(chatRoomsEntity.getRecipientId());
        return chatRoomsResponse;
    }

    @Override
    public ChatRoomsResponseDTO findOneById(Long aLong, UserDTO userDTO) {
        return null;
    }

    @Override
    public List<ChatRoomsResponseDTO> finAllByPreconditions(Long aLong) {
        return null;
    }

    @Override
    public void deleteOneById(Long id) {
        if (!existsOneById(id)) {
            logger.error("[ERROR] Can not delete with id: {}", id);
            return;
        }
        chatRoomsRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        chatRoomsRepository.deleteAll();
    }

    @Override
    public Long countAll() {
        return chatRoomsRepository.count();
    }

    @Override
    public Page<ChatRoomsResponseDTO> findPage(int i, int i1) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findPages(int i, int i1, String s, String s1) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findPages(int i, int i1, Date date, Date date1, String s, String s1) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findPages(int i, int i1, Date date, String s, String s1) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findPages(int i, int i1, Date date, String s) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findPages(int i, int i1, String s, String s1, String s2) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findPages(int i, int i1, String s, String[] strings, String s1) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findPages(int i, int i1, Object o, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findPages(int i, int i1, String[] strings) {
        return null;
    }

    @Override
    public Page<List<ChatRoomsResponseDTO>> findPages(int i, int i1) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findPages(PageRequestDTO pageRequestDTO) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findPages(PageRequestDTO pageRequestDTO, PageAttributeOrderRequestDTO pageAttributeOrderRequestDTO) {
        return null;
    }

    @Override
    public Boolean existsOneById(Long id) {
        if (!ObjectUtils.allNotNull(id)) {
            return false;
        }
        return chatRoomsRepository.existsById(id);
    }

    @Override
    public Page<ChatRoomsResponseDTO> findEnabled(int i, int i1, String s, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findDisabled(int i, int i1, String s, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findArchived(int i, int i1, String s, String[] strings) {
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
    public ByteArrayInputStream exportCSVTemplateWith(List<ChatRoomsResponseDTO> list) {
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
    public byte[] exportJsonTemplateWith(List<ChatRoomsResponseDTO> list) {
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
    public ByteArrayInputStream exportPdfTemplateWith(List<ChatRoomsResponseDTO> list, PdfRequestDTO pdfRequestDTO) {
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
    public Page<ChatRoomsResponseDTO> findUserEnabled(int i, int i1, String s, String s1, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findUserDisabled(int i, int i1, String s, String s1, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findUserArchived(int i, int i1, String s, String s1, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findUserEnabled(int i, int i1, String s, UserDTO userDTO, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findUserDisabled(int i, int i1, String s, UserDTO userDTO, String[] strings) {
        return null;
    }

    @Override
    public Page<ChatRoomsResponseDTO> findUserArchived(int i, int i1, String s, UserDTO userDTO, String[] strings) {
        return null;
    }

    @Override
    public Optional<String> getChatId(ChatRoomsRequest chatRoomsRequest, UserDTO userDTO, boolean createChatRoomsIfNotExists) {

        if (!ObjectUtils.allNotNull(createChatRoomsIfNotExists) || !ObjectUtils.allNotNull(chatRoomsRequest)) {
            return Optional.empty();
        }

        if (createChatRoomsIfNotExists) {
            ChatRoomsRequest senderChatRecipients = new ChatRoomsRequest();
            ChatRoomsRequest recipientChatSenders = new ChatRoomsRequest();

            String chatId = String.format("%s_%s", chatRoomsRequest.getSenderId(), chatRoomsRequest.getRecipientId());

            /* begin::Set body senders */
            senderChatRecipients.setChatId(chatId);
            senderChatRecipients.setSenderId(chatRoomsRequest.getSenderId());
            senderChatRecipients.setRecipientId(chatRoomsRequest.getRecipientId());
            /* end::Set body senders */

            /* begin::Set body recipients */
            recipientChatSenders.setChatId(chatId);
            recipientChatSenders.setSenderId(chatRoomsRequest.getRecipientId());
            recipientChatSenders.setRecipientId(chatRoomsRequest.getSenderId());
            /* end::Set body recipients */
            saves(senderChatRecipients, userDTO);
            saves(recipientChatSenders, userDTO);

            return Optional.of(chatId);
        }

        Optional<ChatRoomsEntity> chatRoomsEntity = chatRoomsRepository.findBySenderIdAndRecipientId(chatRoomsRequest.getSenderId(), chatRoomsRequest.getRecipientId());
        String chatId = chatRoomsEntity.map(ChatRoomsBaseEntity::getChatId).orElse("");
        return Optional.of(chatId);
    }
}
