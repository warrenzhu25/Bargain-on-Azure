package price.azzure.bargain.controller;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import price.azzure.bargain.entity.ResourceSupply;
import price.azzure.bargain.entity.ResourceType;
import price.azzure.bargain.repository.ResourceSupplyRepository;

import java.util.Arrays;
import java.util.List;

import static price.azzure.bargain.entity.ResourceType.*;

@Component
public class ResourceSupplyController {

    private static final long CPU_ID = 1;
    private static final long MEMORY_ID = 2;
    private static final long DISK_ID = 3;

    private static final int CPU_COUNT = 10000;
    private static final int MEMORY_COUNT = 10000;
    private static final int DISK_COUNT = 10000;

    private static final int CPU_COST = 100;
    private static final int MEMORY_COST = 95;
    private static final int DISK_COST = 105;

    private static final int CPU_BASIC_PRICE = CPU_COST + 10;
    private static final int MEMORY_BASIC_PRICE = MEMORY_COST + 10;
    private static final int DISK_BASIC_PRICE = DISK_COST + 10;

    private static final ResourceSupply CPU_RESOURCE = new ResourceSupply(
            CPU_ID, CPU_COUNT, CPU_COST, CPU_BASIC_PRICE, CPU);
    private static final ResourceSupply MEMORY_RESOURCE = new ResourceSupply(
            MEMORY_ID, MEMORY_COUNT, MEMORY_COST, MEMORY_BASIC_PRICE, MEMORY);
    private static final ResourceSupply DISK_RESOURCE = new ResourceSupply(
            DISK_ID, DISK_COUNT, DISK_COST, DISK_BASIC_PRICE, DISK);

    private ResourceSupplyRepository repository;

    public ResourceSupplyController(@NonNull ResourceSupplyRepository repository) {
        this.repository = repository;
        this.repository.saveAll(Arrays.asList(CPU_RESOURCE, MEMORY_RESOURCE, DISK_RESOURCE));
    }

    public ResourceSupply saveResourceSupply(@NonNull ResourceSupply resourceSupply) {
        return this.repository.save(resourceSupply);
    }

    public Integer getResourceCountByType(ResourceType type) {
        List<ResourceSupply> resources = this.repository.findByType(type);

        Assert.isTrue(resources.size() == 1, "should be only record in database");

        return resources.get(0).getCount();
    }

    public Integer getResourceCostByType(ResourceType type) {
        List<ResourceSupply> resources = this.repository.findByType(type);

        Assert.isTrue(resources.size() == 1, "should be only record in database");

        return resources.get(0).getCost();
    }

    public Integer getResourceBasicPriceByType(ResourceType type) {
        List<ResourceSupply> resources = this.repository.findByType(type);

        Assert.isTrue(resources.size() == 1, "should be only record in database");

        return resources.get(0).getBasicPrice();
    }
}

