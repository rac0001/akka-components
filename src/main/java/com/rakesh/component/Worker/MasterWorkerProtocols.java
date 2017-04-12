package com.rakesh.component.Worker;

import java.io.Serializable;

/**
 * Created by ranantoju on 4/11/2017.
 */
public class MasterWorkerProtocols {
    public static class MasterWorkerProtocol implements Serializable {
    }

    // worker registry master
    public static class WorkerRegistryMasterProtocol extends MasterWorkerProtocol {
        public final String workerId;

        public WorkerRegistryMasterProtocol(String workerId) {
            this.workerId = workerId;
        }

        @Override
        public String toString() {
            return "WorkerRegistryMasterProtocol { workerId='" + workerId + "' }";
        }
    }

    // master ask worker : i have a job for you, are you ready ?
    public static class WorkerIsJobReadyProtocol extends MasterWorkerProtocol {
        private static final WorkerIsJobReadyProtocol instance = new WorkerIsJobReadyProtocol();

        public static WorkerIsJobReadyProtocol getInstance() {
            return instance;
        }
    }

    // worker reply master : i'm ready, send me [jobCount] new job
    public static class WorkerRequestJobProtocol extends MasterWorkerProtocol {
        public final String workerId;
        public final Integer jobCount;

        public WorkerRequestJobProtocol(String workerId, Integer jobCount) {
            this.workerId = workerId;
            this.jobCount = jobCount;
        }

        @Override
        public String toString() {
            return "WorkerRequestJobProtocol { workerId='" + workerId + "' }";
        }
    }

    // master send worker a job: here is your new job
    public static class MasterSendJobProtocol extends MasterWorkerProtocol {
        public static final MasterSendJobProtocol instance = new MasterSendJobProtocol();
    }

    /*public static class MasterSendJobsProtocol extends MasterWorkerProtocol {
        public final ArrayList<JobPackage> jobs;

        public MasterSendJobsProtocol(ArrayList<JobPackage> jobs) {
            this.jobs = jobs;
        }

        @Override
        public String toString() {
            return "MasterSendJobsProtocol { jobs=" + jobs + " }";
        }
    }*/
}
