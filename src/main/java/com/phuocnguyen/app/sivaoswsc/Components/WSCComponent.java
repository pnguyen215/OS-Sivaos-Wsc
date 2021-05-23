package com.phuocnguyen.app.sivaoswsc.Components;

import com.sivaos.Model.Request.Original.PbxWsRequest;
import com.sivaos.Model.Response.Original.PbxWsResponse;
import com.sivaos.Utils.LoggerUtils;
import com.sivaos.Utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

/*
Define: url websocket here
Take response from WebSocket
*/

@Component
@ConditionalOnProperty(name = "sivaos.scheduler.wsc.enable")
public class WSCComponent {

    private static final Logger logger = LoggerFactory.getLogger(WSCComponent.class);

    @Value("${sivaos.scheduler.wsc.enable}")
    private boolean isActiveWsc;

    @Value("${wsc.url-ws-callback}")
    private String urlWscCallbacks;

    @Value(("${sivaos.scheduler.wsc.buffer-size}"))
    private Integer bufferSize;

    // @Scheduled(cron = "${sivaos.scheduler.wsc.cron.expressions}")
    @Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 5000)
    public void scheduleWscJobs() {
        logger.info("Current WSCs Thread : {}", Thread.currentThread().getName());
        if (ObjectUtils.allNotNull(isActiveWsc)) {
            if (isActiveWsc) {
                doHandshakeTask();
            }
        }
    }

    private void doHandshakeTask() {
        logger.info("URL ws callback: {}", urlWscCallbacks);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(ObjectUtils.allNotNull(bufferSize) ? bufferSize : 20 * 1024 * 1024);
        WebSocketClient socketClient = new StandardWebSocketClient(container);
        socketClient.doHandshake(new AbstractWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                super.afterConnectionEstablished(session);
            }

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                super.handleMessage(session, message);

                PbxWsResponse pbxWsResponse = LoggerUtils.parseStringToObject((String) message.getPayload(), PbxWsResponse.class);

                PbxWsRequest pbxWsRequest = PbxWsRequest.convertTo(pbxWsResponse);
                logger.info("Body response from WSC: {}", LoggerUtils.snagAsLogJson(pbxWsRequest));
            }

            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                super.handleTextMessage(session, message);
            }

            @Override
            protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
                super.handleBinaryMessage(session, message);
            }

            @Override
            protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
                super.handlePongMessage(session, message);
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                super.handleTransportError(session, exception);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
                super.afterConnectionClosed(session, status);
            }

            @Override
            public boolean supportsPartialMessages() {
                return super.supportsPartialMessages();
            }
        }, urlWscCallbacks);

    }
}
