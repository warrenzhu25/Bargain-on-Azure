package price.azzure.bargain.entity;

public enum ResourceType {
    CPU("Cpu"),
    MEMORY("Memory"),
    DISK("Disk");

    private String name;

    ResourceType(String name) {
        this.name = name;
    }
}
