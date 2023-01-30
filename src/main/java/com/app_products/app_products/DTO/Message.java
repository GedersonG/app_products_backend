package com.app_products.app_products.DTO;

import lombok.Data;

@Data
public class Message {
    private String message;

    public Message(){
        message = "";
    }
    public Message (String message){
        this.message = message;
    }

}
