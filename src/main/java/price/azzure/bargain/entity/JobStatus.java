package price.azzure.bargain.entity;

public enum JobStatus {
    SUBMITTED("Submited"),
    STARTED("Started"),
    FINISHED("Finished");

    private String name;

    JobStatus(String name) {
        this.name = name;
    }
}
