package com.example.blog.post.exceptions;

import java.util.Map;

public class PostNotFoundException extends RuntimeException{
    private String message ;

    public PostNotFoundException(String message){
        super(message);
        this.message = message;
    }

    public PostNotFoundException(){

    }




}
