package com.cassandra.java.client;

import java.util.UUID;

public class Book {
	private UUID id;
	private String title;
	private String author;
	private String subject;
	private String publisher;
	
	// non-parameterized constructor method
	Book() {}   
	
	// parameterized constructor method
	public Book(UUID id, String title, String author, String subject, String publisher) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.subject = subject;
		this.publisher = publisher;
	}
	
	// getter methods
	public UUID getId() { return id; }
	public String getTitle() { return title; }
	public String getAuthor() { return author; }
	public String getSubject() { return subject; }
	public String getPublisher() { return publisher; }
	
	// setter methods
	public void setId(UUID id) { this.id = id; }
	public void setTitle(String title) { this.title = title; }
	public void setAuthor(String author) { this.author = author; }
	public void setSubject(String subject) { this.subject = subject; }
	public void setPublisher(String publisher) { this.publisher = publisher; }
}