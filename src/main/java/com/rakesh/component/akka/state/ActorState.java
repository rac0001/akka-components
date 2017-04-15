package com.rakesh.component.akka.state;

import akka.actor.AbstractActor;
import akka.japi.Procedure;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by mac on 4/14/17.
 */
public class ActorState extends AbstractActor {

    public ActorState(){

        receive(ReceiveBuilder.match(

                String.class, msg->{

                    if(msg.equals("foo")) {
                        context().become(angry());
                    }else if(msg.equals("bar")){
                        context().become(happy());
                    }

                }
                ).build()
        );
    }

    final PartialFunction<Object,BoxedUnit> happy(){
        return ReceiveBuilder.match(String.class , message -> {
            if(message.equals("bar")){
                System.out.println("I am already happy");
            }else if(message.equals("foo")){
                context().become(angry());
            }
        }).build();
    }


    final PartialFunction<Object,BoxedUnit> angry(){
        return ReceiveBuilder.match(String.class , message -> {
            if(message.equals("foo")){
                System.out.println("I am already angry");
            }else if(message.equals("bar")){
                context().become(happy());
            }
        }).build();
    }

/*
    Procedure<Object> happy = new Procedure<Object>() {
        @Override
        public void apply(Object message) throws Exception {

            if(message.equals("bar")){
                sender().tell("I am already angry",self());
            }else if(message.equals("foo")){
                context().become(angry);
            }

        }
    };

    Procedure<Object> angry = new Procedure<Object>() {
        @Override
        public void apply(Object message) throws Exception {

            if(message.equals("bar")){
                sender().tell("I am already happy",self());
            }else if(message.equals("foo")){
                context().become(happy);

            }

        }
    };
*/



}
