package com.rakesh.component.akka.example1;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ranantoju on 3/11/2017.
 */
public class MusicPlayer extends UntypedActor {

    public static final Logger logger = LoggerFactory.getLogger(MusicPlayer.class);

    @Override
    public void onReceive(Object message) throws Throwable {

        if(message instanceof String && ((String) message).equalsIgnoreCase("start player")){
            final ActorRef musicPlayer = getContext().actorOf(MusicConstants.musicControllerProps,"musiccontroller");
            musicPlayer.tell("start controller", getSelf());
        }else if(message instanceof String && ((String) message).equalsIgnoreCase("finished")){
            logger.info("----finished playing music-----");
            getContext().system().shutdown();
        } else
            unhandled(message);
    }
}
