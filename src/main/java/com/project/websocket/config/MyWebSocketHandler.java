package com.project.websocket.config;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.websocket.entity.ChatMessage;

public class MyWebSocketHandler extends TextWebSocketHandler{

	private long idd=1;
	List<ChatMessage> chatMessages=new ArrayList<ChatMessage>();
	private final List<WebSocketSession> sessions=new ArrayList<WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
//		super.afterConnectionEstablished(session);
		
		
		
		
//		Principal principal = session.getPrincipal();
//		if(principal instanceof Authentication/*security.core.Authentication*/) {
//			Authentication authentication = (Authentication) principal;
//			String userName = authentication.getUsername();
//			session.getAttributes().put("UserNameA", userName);
//		}
		
		
		
		
		
		sessions.add(session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
//		super.afterConnectionClosed(session, status);
//		System.out.println("connection closed closed "+session.getId());
		sessions.remove(session);
	}
	
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
//		super.handleTextMessage(session, message);
		System.out.println(session);
		System.out.println("before payload");
		String receivedMessage = message.getPayload();
		if(NumberUtils.isCreatable(receivedMessage)) {
		try{
			System.out.println("BeforeNUmberNumber");
			long number = Long.parseLong(receivedMessage);
			System.out.println("NumberNumber :" +number);
			Iterator<ChatMessage> iter = chatMessages.iterator();
			while(iter.hasNext()) {
				System.out.println("Inside IteratorIterator");
			     ChatMessage chatmessage = iter.next();
			     System.out.println("AfterNext in Iterator");
			     System.out.println("chatmessage is  "+chatmessage);
			     System.out.println(chatmessage.getId());                       /////////////////////
			     if(chatmessage.getId()==number) {
			    	 System.out.println("InsideIteratorIfCondition");
			    	 chatMessages.remove(chatmessage);
			    	 System.out.println("chatmessages :"+ chatMessages);
			     
			     ObjectMapper objectMapper = new ObjectMapper();
					String jsonMessage = objectMapper.writeValueAsString(chatMessages);
			     
			     session.sendMessage(new TextMessage(jsonMessage));
			     return;
			     }
			}
		}catch (Exception e) {
			// TODO: handle 
System.out.println("ErrorError");
		}
	}else {
		
		
		
//		receivedMessage=receivedMessage.substring(1, receivedMessage.length()-1);
		ObjectMapper ob = new ObjectMapper();
		JsonNode inputJson = ob.readTree(receivedMessage);
		System.out.println(inputJson);
		
//		 String id = inputJson.get("id").asText();
//               long idd = Long.parseLong(id);
		 String content = inputJson.get("content").asText();
		System.out.println("inputJsonContent :"+content);
		
		String timeStamp = inputJson.get("timeStamp").asText();
//		  LocalDateTime date = LocalDateTime.parse(timeStamp);
		  
		ChatMessage newMessage = new ChatMessage();
		
		try {
		String id = inputJson.get("id").asText();
         long iddd = Long.parseLong(id);
         newMessage.setId(iddd);
//        if(iddd==0) {
		}catch (Exception e) {
			// TODO: handle exception
			newMessage.setId(idd);
			idd++;
		}
		
//		}
//		newMessage.setMessageId();
//		chatMessage.setSender(userName);
		newMessage.setContent(content);
//		newMessage.setTimeStamp(date);
		
		chatMessages.add(newMessage);
//////////////		
		
//		System.out.println("Received mm message : "+receivedMessage);
		
//		 String username = (String) session.getAttributes().get("UserNameA");
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonMessage = objectMapper.writeValueAsString(chatMessages);
		
		for(WebSocketSession ss:sessions) {
			
		 ss.sendMessage(new TextMessage(/*username+":"+*/jsonMessage));
		 System.out.println("sent message : "+jsonMessage);
		}
	
		System.out.println("return message sent");
	}
	
	
	
	}
}
