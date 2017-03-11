package com.rakesh.component.akka.example1;

import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ranantoju on 3/11/2017.
 */
public class MusicController extends UntypedActor {

    public static final Logger logger = LoggerFactory.getLogger(MusicController.class);

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof String && ((String) message).equalsIgnoreCase("start controller")){
            logger.info("----started playing music-----");
            getSender().tell("finished",getSender());
        } else
            unhandled(message);
    }
}
