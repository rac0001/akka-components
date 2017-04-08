package com.rakesh.component.akka.workerdialin;

/**
 * Created by ranantoju on 3/25/2017.
 */
public class TransformationApp {

    public static void main(String argts[]){

        TransformationBackendMain.main(new String[] { "2557" });
        TransformationBackendMain.main(new String[] { "2558" });
        TransformationBackendMain.main(new String[] { "0" });
        TransformationFrontendMain.main(new String[] { "0" });

    }

}
