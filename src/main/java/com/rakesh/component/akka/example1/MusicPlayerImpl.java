package com.rakesh.component.akka.example1;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ranantoju on 3/11/2017.
 */
public class MusicPlayerImpl {

    public static final Logger logger = LoggerFactory.getLogger(MusicPlayerImpl.class);

    public static void main(String args[]){

        ActorSystem system = ActorSystem.create("MusicPlayer");

        final ActorRef musicPlayer = system.actorOf(Props.create(MusicPlayer.class),"musicplayer");

        musicPlayer.tell("start player",musicPlayer);

//        system.shutdown();

    }

}
