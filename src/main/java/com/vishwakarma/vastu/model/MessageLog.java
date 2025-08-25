package com.vishwakarma.vastu.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.vishwakarma.vastu.utils.MessageMode;

@Entity(name = "MessageLog")
public class MessageLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date sendTimestamp;

	private String mode;	// SMS/Email/Voice
	private String destination;	// Phone number for SMS/Voice, Email ID for email

	private Long senderUserId;	// Sending User 
	
	private String tag;
	
	// For display
	@Transient private String email;
	
	public MessageLog() {		
	}
	
	public MessageLog(Long userId, MessageMode messageMode,
			String destination, Date timestamp, String tag) {
		super();
		this.senderUserId = userId;
		this.destination = destination;
		this.mode = messageMode.name();
		this.sendTimestamp = timestamp;
		this.tag = tag;
	}

	public String toString() {
		return "user:"+senderUserId+", timestamp:"+sendTimestamp+", destination: "+destination+", mode: "+mode;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(Long userId) {
		this.senderUserId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getSendTimestamp() {
		return sendTimestamp;
	}

	public void setSendTimestamp(Date sendTimestamp) {
		this.sendTimestamp = sendTimestamp;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}