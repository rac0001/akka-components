package com.rakesh.component.akka.router.customrouter;

import akka.actor.ActorRef;
import akka.routing.Routee;

/**
 * Created by ranantoju on 4/13/2017.
 */
public final class TestRoutee implements Routee {

    public final int n;

    public TestRoutee(int n) {
        this.n = n;
    }

    @Override
    public void send(Object message, ActorRef sender) {

    }

    @Override
    public int hashCode() {
        return n;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TestRoutee) &&
                n == ((TestRoutee) obj).n;
    }
}
