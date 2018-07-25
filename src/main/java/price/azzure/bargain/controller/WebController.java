package price.azzure.bargain.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import price.azzure.bargain.dto.Price;
import price.azzure.bargain.dto.Profit;
import price.azzure.bargain.dto.ResourceRemain;
import price.azzure.bargain.dto.Usage;
import price.azzure.bargain.entity.BatchJob;
import price.azzure.bargain.entity.Resource;
import price.azzure.bargain.entity.ResourceType;
import price.azzure.bargain.repository.BatchJobRepository;
import price.azzure.bargain.repository.JobDetailsRepository;
import price.azzure.bargain.repository.ResourceRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static price.azzure.bargain.entity.ResourceType.*;


@RestController
public class WebController {

    private static final Map<ResourceType, Double> costByType = getResourceCost();
    private static final Map<ResourceType, Double> priceByType = getResourcePrice();

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private BatchJobRepository jobRepository;

    @Autowired
    private ResourceController resourceController;

    @Autowired
    private BatchJobController batchJobController;

    @Autowired
    private JobDetailsRepository jobDetailsRepository;

    @PostMapping("/jobs")
    public BatchJob submitJob(@RequestBody BatchJob job) {
        //TODO: validate price and deadline first

        // if both price and deadline both provided and valid, just save
        if (validatePriceOrDeadline(job)) {
            jobDetailsRepository.save(job.getDetail());
            return jobRepository.save(job);
        }

        // return job with suggested price or deadline
        return job;
    }

    @GetMapping("/jobs")
    public List<BatchJob> listJobs() {
        return Lists.newArrayList(jobRepository.findAll());
    }

    @DeleteMapping("/jobs/{id}")
    public void deleteJob(@PathVariable("id") long id) {
        jobRepository.deleteById(id);
    }

    @GetMapping("/resources")
    public List<Resource> getResource() {
        //TODO: we should return from last 7 days to future 7 days
        return Lists.newArrayList(resourceRepository.findAll());
    }

    /**
     * Return last 7 days.
     */
    @GetMapping("/getRemainChart")
    public List<ResourceRemain> getRemainChart() {
        List<ResourceRemain> resourceRemainList = new ArrayList<>();
        int cpuTotal = resourceController.getResourceCountByType(CPU);
        int memoryTotal = resourceController.getResourceCountByType(MEMORY);
        int diskTotal = resourceController.getResourceCountByType(DISK);


        for (int i = 6; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -i);
            List<BatchJob> jobs = jobRepository.findActiveJobsByDate(calendar.getTime());
            //cpu
            int count = findCpuCount(jobs);
            String date=new SimpleDateFormat("MM.dd").format(calendar.getTime());
            ResourceRemain cpuRemain = new ResourceRemain(cpuTotal - count, date, CPU);
            resourceRemainList.add(cpuRemain);
            //memory
            count = findMemoryCount(jobs);
            ResourceRemain memoryRemain = new ResourceRemain(memoryTotal - count, date, MEMORY);
            resourceRemainList.add(memoryRemain);
            //disk
            count = findDiskCount(jobs);
            ResourceRemain diskRemain = new ResourceRemain(diskTotal - count, date, DISK);
            resourceRemainList.add(diskRemain);
        }
        return resourceRemainList;
    }


    /**
     * Return last 7 days.
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
        for (ResourceRemain resourceRemain : resourceRemainList) {
            Price price;
            switch (resourceRemain.getResourceType()) {
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

    /**
     * Return last 7 days.
     */
    @GetMapping("/getProfitChart")
    public List<Profit> getProfitChart() {
        List<Profit> profitList = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -i);
            List<BatchJob> jobs = jobRepository.findByStartTimeBefore(calendar.getTime());
            String date = new SimpleDateFormat("MM.dd").format(calendar.getTime());
            int profitSum = 0;
            for(BatchJob job: jobs){
                profitSum += job.getPrice() - getTotalCost(job);
            }
            Profit profit = new Profit(profitSum, date);
            profitList.add(profit);
        }
        return profitList;
    }


    /**
     * Return last 7 days.
     */
    @GetMapping("/getUsageChart")
    public List<Usage> getUsageChart() {
        List<Usage> usageList = new ArrayList<>();
        for(int i=6; i>=0; i--){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -i);
            String date = new SimpleDateFormat("MM.dd").format(calendar.getTime());
            List<BatchJob> jobs = jobRepository.findByStartTimeBefore(calendar.getTime());
            int cpuUsage = 0, memoryUsage = 0, diskUsage = 0;
            for(BatchJob job: jobs){
                cpuUsage += job.getDetail().getCpuCount();
                memoryUsage += job.getDetail().getMemoryCount();
                diskUsage += job.getDetail().getDiskCount();
            }
            Usage cU = new Usage(cpuUsage, date, CPU);
            Usage mU = new Usage(memoryUsage, date, MEMORY);
            Usage dU = new Usage(diskUsage, date, DISK);
            usageList.add(cU);
            usageList.add(mU);
            usageList.add(dU);
        }
        return usageList;
    }

    private double getTotalCost(BatchJob job) {
        double cpuProfit = job.getDetail().getCpuCount() * resourceController.getResourceCostByType(CPU);
        double memoryProfit =job.getDetail().getMemoryCount() * resourceController.getResourceCostByType(MEMORY);
        double diskProfit = job.getDetail().getDiskCount() * resourceController.getResourceCostByType(DISK);
        return getMax(cpuProfit, memoryProfit, diskProfit);
    }

    private double getMax(double a, double b, double c) {
        double max = (a > b) ? a : b;
        max = (max > c) ? max : c;
        return max;
    }


    private int findCpuCount(List<BatchJob> jobs) {
        int cpuCount = 0;
        for (BatchJob job : jobs) {
            cpuCount += job.getDetail().getCpuCount();
        }
        return cpuCount;
    }

    private int findMemoryCount(List<BatchJob> jobs) {
        int memoryCount = 0;
        for (BatchJob job : jobs) {
            memoryCount += job.getDetail().getMemoryCount();
        }
        return memoryCount;
    }

    private int findDiskCount(List<BatchJob> jobs) {
        int diskCount = 0;
        for (BatchJob job : jobs) {
            diskCount += job.getDetail().getDiskCount();
        }
        return diskCount;
    }

    private double calculatePrice(int total, double remain, double basicPrice) {
        return basicPrice * (2 - remain / total);
    }

    private boolean validatePriceOrDeadline(BatchJob batchJob) {
        if(batchJob.getPrice() != null && batchJob.getDeadline() != null){
            double suggestPrice = computePrice(batchJob);
            Date suggestDeadline = computeDeadline(batchJob);

            if(suggestPrice < batchJob.getPrice()) {
                return true;
            }

            batchJob.setSuggestedPrice(suggestPrice);
            batchJob.setSuggestDeadline(suggestDeadline);

            return true;
        } else if(batchJob.getPrice() != null){
            batchJob.setSuggestDeadline(computeDeadline(batchJob));
        } else if(batchJob.getDeadline() != null){
            batchJob.setSuggestedPrice(computePrice(batchJob));
        }

        return false;
    }

    private double computePrice(BatchJob job) {
        Map<ResourceType, Integer> resourceUsage = job.getDetail().getEstimatedResourceUsage();
        ResourceType dominantResourceType = getDomainateResource(resourceUsage);

        double price = priceByType.get(dominantResourceType);
        double cost = costByType.get(dominantResourceType);

        Period period = between(new Date(), job.getDeadline());

        return cost + (price - cost) * daysToDiscount(period.getDays());
    }

    private static double daysToDiscount(int days){
        return 1 - days / 365 * 0.2;
    }

    private static int discountToDays(double discount){
        return (int)((1 - discount) * 4 * 365);
    }

    private Date computeDeadline(BatchJob job) {
        Map<ResourceType, Integer> resourceUsage = job.getDetail().getEstimatedResourceUsage();
        ResourceType dominantResourceType = getDomainateResource(resourceUsage);

        double price = priceByType.get(dominantResourceType);
        double cost = costByType.get(dominantResourceType);
        double profit = price - cost;

        double discount = (job.getPrice() - cost) / profit ;

        int days = discountToDays(discount);
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Date.from(localDate.plusDays(days).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private ResourceType getDomainateResource(Map<ResourceType, Integer> resourceUsage) {
        ResourceType dominantResourceType = null;
        double price = 0;

        for (Map.Entry<ResourceType, Integer> entry : resourceUsage.entrySet()) {
            double curPrice = entry.getValue() * priceByType.get(entry.getKey());

            if (curPrice > price) {
                price = curPrice;
                dominantResourceType = entry.getKey();
            }
        }

        return dominantResourceType;
    }

    private static Map<ResourceType, Double> getResourceCost() {
        return Collections.unmodifiableMap(Stream.of(
                entry(CPU, 100.0),
                entry(MEMORY, 80.0),
                entry(DISK, 20.0),
                entry(ResourceType.GPU, 120.0)
        ).collect(entriesToMap()));
    }

    private static Map<ResourceType, Double> getResourcePrice() {
        return Collections.unmodifiableMap(Stream.of(
                entry(CPU, 100.0 * 2),
                entry(MEMORY, 80.0 * 2),
                entry(DISK, 20.0 * 2),
                entry(ResourceType.GPU, 120.0 * 2)
        ).collect(entriesToMap()));
    }

    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    public static <K, U> Collector<Map.Entry<K, U>, ?, Map<K, U>> entriesToMap() {
        return Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue());
    }

    private static Period between(Date from, Date to) {
        LocalDate localFrom = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localTo = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Period.between(localFrom, localTo);
    }
}
