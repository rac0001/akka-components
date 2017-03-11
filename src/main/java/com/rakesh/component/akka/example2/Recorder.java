package com.rakesh.component.akka.example2;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ranantoju on 3/11/2017.
 */
public class Recorder extends UntypedActor {

    public static final Logger logger = LoggerFactory.getLogger(Recorder.class);

    private ActorRef checker;
    private ActorRef storge;

    public Recorder(ActorRef checker,ActorRef storge){
        this.checker = checker;
        this.storge = storge;
    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if(message instanceof User){
            User givenUser = (User)message;
            checker.tell(givenUser,getSelf());
        }else if(message instanceof WhiteUser){
            storge.tell(message,getSelf());
        }else if(message instanceof BlackUser){
            logger.info("---Black user found---"+getSelf()+"-"+((BlackUser) message).blackUser.name);
        }else if(message instanceof String &&((String) message).equalsIgnoreCase("storageDone")){
            getContext().system().shutdown();
        } else {
            unhandled(message);
        }

    }
}
