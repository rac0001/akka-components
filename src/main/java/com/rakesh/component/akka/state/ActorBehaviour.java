package com.rakesh.component.akka.state;

import akka.actor.AbstractFSM;
import com.rakesh.component.akka.state.behaviour.Data;
import com.rakesh.component.akka.state.behaviour.EmptyData;
import com.rakesh.component.akka.state.behaviour.State;

import static com.rakesh.component.akka.state.behaviour.EmptyData.EMPTY_DATA;
import static com.rakesh.component.akka.state.behaviour.State.ACTIVE;
import static com.rakesh.component.akka.state.behaviour.State.IDLE;

/**
 * Created by mac on 4/16/17.
 */
public class ActorBehaviour extends AbstractFSM<State, Data> {

    ActorBehaviour() {

        startWith(IDLE, EMPTY_DATA);

/*

        when(IDLE, matchEvent(String.class,EmptyData.class,msg -> {
                    if(msg.equalsIgnoreCase("bar")){
                        System.out.println("I am already happy");
                        return stay();
                    }else if(msg.equals("foo")){
                        return goTo(ACTIVE);
                    }
                })
        );
*/




    }

}
