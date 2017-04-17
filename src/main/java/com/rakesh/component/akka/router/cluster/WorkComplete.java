package com.rakesh.component.akka.router.cluster;

import java.io.Serializable;

/**
 * Created by ranantoju on 4/17/2017.
 */
public class WorkComplete implements Serializable {

    private String message;

    public WorkComplete(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
