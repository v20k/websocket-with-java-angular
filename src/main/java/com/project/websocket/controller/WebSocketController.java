package com.project.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

//	@MessageMapping("/send")
//	@SendTo("/topic/receive")
//	public String send(String message) {
//		System.out.println("controllermessage "+message);
//		return "SpringBootVinoth "+message;
//	}
}
