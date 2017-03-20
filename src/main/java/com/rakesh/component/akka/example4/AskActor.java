package com.rakesh.component.akka.example4;

import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by mac on 3/19/17.
 */
public class AskActor extends AbstractActor {

    public AskActor(){

        Props selProp = Props.create(SelectionActor.class);
        ActorRef selActor = getContext().actorOf(selProp,"sel");

        ActorSelection actorSel = getContext().actorSelection("/user/ask/sel");

        actorSel.tell(new Identify("selection"),self());

        receive(ReceiveBuilder
                        .match(String.class,msg -> {

//                    Thread.currentThread().sleep(5000);
                            System.out.println(" message is : "+msg);
                        }).build()
        );

        getContext().system().shutdown();

    }

}
