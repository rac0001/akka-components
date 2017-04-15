package com.rakesh.component.akka.router.roundrobinpool;

import java.io.Serializable;

/**
 * Created by ranantoju on 4/13/2017.
 */
public class Result implements Serializable{

    public String getName() {
        return name;
    }

    String name;
    Result(String name){
        this.name = name;
    }

}
