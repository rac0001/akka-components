package com.rakesh.component.akka;

import akka.actor.Actor;
import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mac on 3/11/17.
 */
public class Greeter extends UntypedActor {

    public static Logger logger = LoggerFactory.getLogger(Greeter.class);

    public void onReceive(Object message) throws Throwable {
        if(message instanceof String){
            logger.info("---"+message);
        }
    }


}
