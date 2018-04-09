package com.example.jayashankarjayan.placements;

/**
 * Created by Jayashankar Jayan on 11/29/2017.
 */
public class ChatMessage {

    private String messageId;
    private String message;
    private String messageTime;
    private String category;
    private String imageOfUser;
    private String course;

    public ChatMessage() {
    }

    public ChatMessage(String messageId, String messageUser, String messageTime,String category) {
        this.category = category;
        this.message = messageUser;
        this.messageTime = messageTime;
        this.imageOfUser = imageOfUser;
        this.messageId= messageId;
    }

    public ChatMessage(String messageId, String messageUser, String messageTime,String category, String imageOfUser) {
        this.category = category;
        this.message = messageUser;
        this.messageTime = messageTime;
        this.imageOfUser = imageOfUser;
        this.messageId= messageId;
    }

    public String getCategory() {
        return "Posted By: "+category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getImageOfUser() {
        return imageOfUser;
    }

    public void setImageOfUser(String imageOfUser) {
        this.imageOfUser = imageOfUser;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
