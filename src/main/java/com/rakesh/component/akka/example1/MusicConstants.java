package com.rakesh.component.akka.example1;

import akka.actor.Props;

/**
 * Created by ranantoju on 3/11/2017.
 */
public class MusicConstants {

    public static final Props musicControllerProps = Props.create(MusicController.class);
}
