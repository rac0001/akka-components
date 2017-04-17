package com.rakesh.component.akka.router.cluster;

import java.io.Serializable;

/**
 * Created by mac on 4/17/17.
 */
public class MessageProcess implements Serializable{

    private String message;

    public MessageProcess(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
