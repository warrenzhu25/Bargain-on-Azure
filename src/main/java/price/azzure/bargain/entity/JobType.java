package price.azzure.bargain.entity;

public enum JobType {
    HADOOP("Hadoop"),
    MACHINE_LEARNING("MachineLearning");

    private String name;

    JobType(String name) {
        this.name = name;
    }
}
