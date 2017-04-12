package com.rakesh.component.Worker;

import java.io.Serializable;

/**
 * Created by ranantoju on 4/11/2017.
 */
public class WorkerJobProtocols {
    public static class WorkerSendJobProtocol {
    }

    public static class JobResultDoneProtocol implements Serializable {
        public static final JobResultDoneProtocol instance = new JobResultDoneProtocol();
    }
}
