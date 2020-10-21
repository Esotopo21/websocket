package it.burlac.webscoket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DelivererMessage {

    @JsonProperty("message")
    String message;
    @JsonProperty("receiverId")
    String receiverId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
