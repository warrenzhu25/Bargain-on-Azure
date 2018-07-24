package price.azzure.bargain.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import price.azzure.bargain.dto.Price;
import price.azzure.bargain.dto.ResourceRemain;
import price.azzure.bargain.entity.BatchJob;
import price.azzure.bargain.entity.Resource;
import price.azzure.bargain.entity.ResourceType;
import price.azzure.bargain.repository.BatchJobRepository;
import price.azzure.bargain.repository.ResourceRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static price.azzure.bargain.entity.ResourceType.*;

@RestController
public class WebController {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private BatchJobRepository jobRepository;

    @Autowired
    private ResourceController resourceController;

    @PostMapping("/jobs")
    public BatchJob submitJob(BatchJob job){
        //TODO: validate price and deadline first

        // if both price and deadline both provided and valid, just save
        if(validatePriceOrDeadline(job)) {
            return jobRepository.save(job);
        }

        // return job with suggested price or deadline
        return job;
    }

    @GetMapping("/resources")
    public List<Resource> getResource(){
        //TODO: we should return from last 7 days to future 7 days
        return Lists.newArrayList(resourceRepository.findAll());
    }

    /**
     * Return last 3 days.
     */
    @GetMapping("/getRemainChart")
    public List<ResourceRemain> getRemainChart() {
        List<ResourceRemain> resourceRemainList = new ArrayList<>();
        int cpuTotal = resourceController.getResourceCountByType(CPU);
        int memoryTotal = resourceController.getResourceCountByType(MEMORY);
        int diskTotal = resourceController.getResourceCountByType(DISK);

        Calendar calendar = Calendar.getInstance();
        for(int i = 2; i >=0; i++){
            calendar.set(Calendar.DATE, -i);
            List<BatchJob> jobs = jobRepository.findActiveJobsByDate(calendar.getTime());
            //cpu
            int count=findCpuCount(jobs);
            ResourceRemain cpuRemain = new ResourceRemain(cpuTotal - count, calendar.getTime(),CPU);
            resourceRemainList.add(cpuRemain);
            //memory
            count = findMemoryCount(jobs);
            ResourceRemain memoryRemain = new ResourceRemain(memoryTotal - count, calendar.getTime(),MEMORY);
            resourceRemainList.add(memoryRemain);
            //disk
            count = findDiskCount(jobs);
            ResourceRemain diskRemain = new ResourceRemain(diskTotal - count, calendar.getTime(),DISK);
            resourceRemainList.add(diskRemain);
        }
        return resourceRemainList;
    }


    /**
     * Return last 3 days.
     */
    @GetMapping("/getPriceChart")
    public List<Price> getPriceChart() {
        List<Price> priceList = new ArrayList<>();
        int cpuTotal = resourceController.getResourceCountByType(CPU);
        int memoryTotal = resourceController.getResourceCountByType(MEMORY);
        int diskTotal = resourceController.getResourceCountByType(DISK);

        double cpuPrice = resourceController.getResourcePriceByType(CPU);
        double memoryPrice = resourceController.getResourcePriceByType(MEMORY);
        double diskPrice = resourceController.getResourcePriceByType(DISK);

        List<ResourceRemain> resourceRemainList = getRemainChart();
        for(ResourceRemain resourceRemain: resourceRemainList) {
            Price price;
            switch (resourceRemain.getResourceType()){
                case CPU:
                    price = new Price(calculatePrice(cpuTotal, resourceRemain.getCount(), cpuPrice),
                            resourceRemain.getDate(), CPU);
                    break;
                case MEMORY:
                    price = new Price(calculatePrice(memoryTotal, resourceRemain.getCount(), memoryPrice),
                            resourceRemain.getDate(), MEMORY);
                    break;
                default:
                    price = new Price(calculatePrice(diskTotal, resourceRemain.getCount(), diskPrice),
                            resourceRemain.getDate(), DISK);
                    break;
            }
            priceList.add(price);
        }
        return priceList;
    }

    private int findCpuCount(List<BatchJob> jobs){
        int cpuCount = 0;
        for(BatchJob job: jobs){
            cpuCount += job.getDetail().getCpuCount();
        }
        return cpuCount;
    }

    private int findMemoryCount(List<BatchJob> jobs){
        int memoryCount = 0;
        for(BatchJob job: jobs){
            memoryCount += job.getDetail().getMemoryCount();
        }
        return memoryCount;
    }

    private int findDiskCount(List<BatchJob> jobs){
        int diskCount = 0;
        for(BatchJob job: jobs){
            diskCount += job.getDetail().getDiskCount();
        }
        return diskCount;
    }

    private double calculatePrice(int total, double remain, double basicPrice) {
        return basicPrice * (2 - remain / total);
    }

    private boolean validatePriceOrDeadline(BatchJob batchJob){
        return true;
    }
}
