package com.rakesh.component.akka.Transformation;

import java.io.Serializable;

/**
 * Created by ranantoju on 4/8/2017.
 */
public class ResultMessage implements Serializable {

    private String msg;

    ResultMessage(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
