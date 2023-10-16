package com.project.websocket.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

	private long id;
	private String messageId;
	private String sender;
	private String content;
	private LocalDateTime timeStamp;
	
}
