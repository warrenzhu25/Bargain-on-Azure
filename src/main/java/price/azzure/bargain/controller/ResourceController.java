package price.azzure.bargain.controller;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import price.azzure.bargain.entity.Resource;
import price.azzure.bargain.entity.ResourceType;
import price.azzure.bargain.repository.ResourceRepository;

import java.util.Arrays;
import java.util.List;

import static price.azzure.bargain.entity.ResourceType.*;

@Component
public class ResourceController {

    private static final long CPU_ID = 1;
    private static final long MEMORY_ID = 2;
    private static final long DISK_ID = 3;

    private static final int CPU_COUNT = 10000;
    private static final int MEMORY_COUNT = 10000;
    private static final int DISK_COUNT = 10000;

    private static final double CPU_COST = 100.0;
    private static final double MEMORY_COST = 95.0;
    private static final double DISK_COST = 105.0;

    private static final double CPU_BASIC_PRICE = CPU_COST + 10;
    private static final double MEMORY_BASIC_PRICE = MEMORY_COST + 10;
    private static final double DISK_BASIC_PRICE = DISK_COST + 10;

    private static final Resource CPU_RESOURCE = new Resource(
            CPU_ID, CPU_COUNT, CPU, CPU_COST, CPU_BASIC_PRICE);
    private static final Resource MEMORY_RESOURCE = new Resource(
            MEMORY_ID, MEMORY_COUNT, MEMORY, MEMORY_COST, MEMORY_BASIC_PRICE);
    private static final Resource DISK_RESOURCE = new Resource(
            DISK_ID, DISK_COUNT, MEMORY, DISK_COST, DISK_BASIC_PRICE);

    private ResourceRepository repository;

    public ResourceController(@NonNull ResourceRepository repository) {
        this.repository = repository;
        this.repository.saveAll(Arrays.asList(CPU_RESOURCE, MEMORY_RESOURCE, DISK_RESOURCE));
    }

    public Resource saveResource(@NonNull Resource Resource) {
        return this.repository.save(Resource);
    }

    public Integer getResourceCountByType(ResourceType type) {
        List<Resource> resources = this.repository.findByType(type);

        Assert.isTrue(resources.size() == 1, "should be only record in database");

        return resources.get(0).getCount();
    }

    public double getResourceCostByType(ResourceType type) {
        List<Resource> resources = this.repository.findByType(type);

        Assert.isTrue(resources.size() == 1, "should be only record in database");

        return resources.get(0).getCost();
    }

    public double getResourcePriceByType(ResourceType type) {
        List<Resource> resources = this.repository.findByType(type);

        Assert.isTrue(resources.size() == 1, "should be only record in database");

        return resources.get(0).getPrice();
    }
}

