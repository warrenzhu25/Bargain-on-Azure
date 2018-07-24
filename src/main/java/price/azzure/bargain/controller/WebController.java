package price.azzure.bargain.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import price.azzure.bargain.entity.BatchJob;
import price.azzure.bargain.entity.Price;
import price.azzure.bargain.entity.ResourceSupply;
import price.azzure.bargain.repository.BatchJobRepository;
import price.azzure.bargain.repository.PriceRepository;
import price.azzure.bargain.repository.ResourceSupplyRepository;

import java.util.List;

@RestController
public class WebController {

    @Autowired
    private ResourceSupplyRepository resourceSupplyRepository;

    @Autowired
    private BatchJobRepository jobRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ResourceSupplyController resourceSupplyController;

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
    public List<ResourceSupply> getResourceSupply(){
        //TODO: we should return from last 7 days to future 7 days
        return Lists.newArrayList(resourceSupplyRepository.findAll());
    }

    @GetMapping("/prices")
    public List<Price> getPrices(){
        //TODO: we should return from last 7 days to future 7 days
        return Lists.newArrayList(priceRepository.findAll());
    }

    private boolean validatePriceOrDeadline(BatchJob batchJob){
        return true;
    }
}
