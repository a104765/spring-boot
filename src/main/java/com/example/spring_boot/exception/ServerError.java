package com.example.spring_boot.exception;

public class ServerError extends RuntimeException{
    public ServerError(String message){
        super(message);
    }
}
