package com.rakesh.component.akka.balancing;

import java.io.Serializable;

/**
 * Created by mac on 4/15/17.
 */
public class Work implements Serializable {

    String workRequestParam;

    public Work(String workRequestParam) {
        this.workRequestParam = workRequestParam;
    }

    public String getWorkRequestParam() {
        return workRequestParam;
    }
}
