package com.rakesh.component.akka.example2;

import akka.actor.UntypedActor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ranantoju on 3/11/2017.
 */
public class Checker extends UntypedActor {

    public static final List<User> blackListUsers = Arrays.asList(new User("Adam","adam@gmail.com")
                                                ,new User("Rick","rick@gmail.com"));

    @Override
    public void onReceive(Object message) throws Throwable {

        if(message instanceof User){
            User givenUser = (User)message;
            if(checkUser(givenUser)){
                BlackUser blackUser = new BlackUser(givenUser);
                getSender().tell(blackUser,getSelf());
            }else{
                WhiteUser whiteUser = new WhiteUser(givenUser);
                getSender().tell(whiteUser,getSelf());
            }
        }else {
            unhandled(message);
        }
    }

    private boolean checkUser(final User user){
        return blackListUsers.stream().filter(inputUser ->
                inputUser.name.equalsIgnoreCase(user.name) && inputUser.email.equalsIgnoreCase(user.email)
        ).findFirst().isPresent();
    }
}
