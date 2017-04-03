package com.rakesh.component.akka.workerdialin;

/**
 * Created by ranantoju on 3/25/2017.
 */
public interface TransformationMessages {

    public static  String BACKEND_REGISTRATION = "BackendWorkers";

    class TransformationJob{

        public String transformString;

        public TransformationJob(String transformString) {
            this.transformString = transformString;
        }

        public String getTransformString() {
            return transformString;
        }
    }

    class TransformationResult {

        public String transformResultString;

        public TransformationResult(String transformResultString) {
            this.transformResultString = transformResultString;
        }

        public String getTransformResultString() {
            return transformResultString;
        }

        @Override
        public String toString() {
            return "TransformationResult(" + transformResultString + ")";
        }

    }

    class FailedJob {

        public String result;
        public TransformationJob transformationJob;

        public FailedJob(String result, TransformationJob transformationJob) {
            this.result = result;
            this.transformationJob = transformationJob;
        }

        public TransformationJob getTransformationJob() {
            return transformationJob;
        }

        public String getResult() {
            return result;
        }
        @Override
        public String toString() {
            return "JobFailed(" + result + ")";
        }

    }

}
