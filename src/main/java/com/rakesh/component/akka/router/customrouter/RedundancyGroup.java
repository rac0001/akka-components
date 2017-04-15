package com.rakesh.component.akka.router.customrouter;

import akka.actor.ActorSystem;
import akka.dispatch.Dispatchers;
import akka.routing.GroupBase;
import akka.routing.Router;
import com.typesafe.config.Config;

import java.util.List;

/**
 * Created by ranantoju on 4/13/2017.
 */
public class RedundancyGroup extends GroupBase {
    private final List<String> paths;
    private final int nbrCopies;

    public RedundancyGroup(List<String> paths, int nbrCopies) {
        this.paths = paths;
        this.nbrCopies = nbrCopies;
    }

    public RedundancyGroup(Config config) {
        this(config.getStringList("routees.paths"),
                config.getInt("nbr-copies"));
    }

    @Override
    public java.lang.Iterable<String> getPaths(ActorSystem system) {
        return paths;
    }

    @Override
    public Router createRouter(ActorSystem system) {
        return new Router(new RedundancyRoutingLogic(nbrCopies));
    }

    @Override
    public String routerDispatcher() {
        return Dispatchers.DefaultDispatcherId();
    }

}
