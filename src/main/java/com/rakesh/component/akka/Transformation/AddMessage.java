package com.rakesh.component.akka.Transformation;

import java.io.Serializable;

/**
 * Created by ranantoju on 4/8/2017.
 */
public class AddMessage implements Serializable{

    private String msg;

    AddMessage(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
