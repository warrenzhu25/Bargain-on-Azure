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
import price.azzure.bargain.repository.BatchJobRepository;
import price.azzure.bargain.repository.ResourceRepository;

import java.util.List;

@RestController
public class WebController {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private BatchJobRepository jobRepository;

    @Autowired
    private ResourceController resourceController;

    @Autowired
    private BatchJobController batchJobController;

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
}
