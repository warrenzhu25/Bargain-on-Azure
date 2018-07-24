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

import java.time.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class WebController {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private BatchJobRepository jobRepository;

    private static final Map<ResourceType, Double> costByType = getResourceCost();
    private static final Map<ResourceType, Double> priceByType = getResourcePrice();
    @Autowired
    private ResourceController ResourceController;

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

    @GetMapping("/getRemainChart")
    public List<ResourceRemain> getRemainChart() {
        return Lists.newArrayList();
    }

    @GetMapping("/getPriceChart")
    public List<Price> getPriceChart() {
        return Lists.newArrayList();
    }

    private boolean validatePriceOrDeadline(BatchJob batchJob){
        return true;
    }

    private double computePrice(BatchJob job){
        Map<ResourceType, Integer> resourceUsage = job.getDetail().getEstimatedResourceUsage();
        ResourceType dominantResourceType = getDomainateResource(resourceUsage);

        double price = priceByType.get(dominantResourceType);
        double cost = costByType.get(dominantResourceType);

        Period period = between(new Date(), job.getDeadline());

        int weight = -(int)Math.log(resourceUsage.get(dominantResourceType) / period.getDays() /
                getResource
                        (dominantResourceType));

        return cost + (price - cost) * weight / (weight + 1);
    }

    private Date computeDeadline(BatchJob job){
        Map<ResourceType, Integer> resourceUsage = job.getDetail().getEstimatedResourceUsage();
        ResourceType dominantResourceType = getDomainateResource(resourceUsage);

        double price = priceByType.get(dominantResourceType);
        double cost = costByType.get(dominantResourceType);
        double profit = price -cost;

        int weight = (int)(1 / ( 1 - (job.getPrice() - cost) / profit));

        int days = (int)Math.pow(2, -weight) * getResource(dominantResourceType) / resourceUsage.get
                (dominantResourceType);
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Date.from(localDate.plusDays(days).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private ResourceType getDomainateResource(Map<ResourceType, Integer> resourceUsage){
        ResourceType dominantResourceType = null;
        double price = 0;

        for(Map.Entry<ResourceType, Integer> entry: resourceUsage.entrySet()){
            double curPrice = entry.getValue() * priceByType.get(entry.getKey());

            if(curPrice > price){
                price = curPrice;
                dominantResourceType = entry.getKey();
            }
        }

        return dominantResourceType;
    }

    private static Map<ResourceType, Double> getResourceCost(){
        return Collections.unmodifiableMap(Stream.of(
                entry(ResourceType.CPU, 100.0),
                entry(ResourceType.MEMORY, 80.0),
                entry(ResourceType.DISK, 20.0),
                entry(ResourceType.GPU, 120.0)
                ).collect(entriesToMap()));
    }

    private static Map<ResourceType, Double> getResourcePrice(){
        return Collections.unmodifiableMap(Stream.of(
                entry(ResourceType.CPU, 100.0 * 2),
                entry(ResourceType.MEMORY, 80.0 * 2),
                entry(ResourceType.DISK, 20.0 * 2),
                entry(ResourceType.GPU, 120.0 * 2)
        ).collect(entriesToMap()));
    }

    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    public static <K, U> Collector<Map.Entry<K, U>, ?, Map<K, U>> entriesToMap() {
        return Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue());
    }

    private static Period between(Date from, Date to){
        LocalDate localFrom = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localTo = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Period.between(localFrom, localTo);
    }

    private int getResource(ResourceType resourceType){
        return 0;
    }
}
