package com.rakesh.component.akka.example2;

import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ranantoju on 3/11/2017.
 */
public class Storage extends UntypedActor {
    public static final Logger logger = LoggerFactory.getLogger(Storage.class);

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof WhiteUser){
            logger.info("---White user found---"+getSelf()+"-"+((WhiteUser) message).whiteUser.name);
            getSender().tell("storageDone",getSelf());
        }
    }
}
