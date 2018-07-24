package price.azzure.bargain;

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

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
public class WebController {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private BatchJobRepository jobRepository;

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
    public List<Resource> getResourceSupply(){
        //TODO: we should return from last 7 days to future 7 days
        return Lists.newArrayList(resourceRepository.findAll());
    }

    @GetMapping("/getRemainChart")
    public List<ResourceRemain> getRemainChart() {
        List<ResourceRemain> resourceRemainList = new ArrayList<>();
        int cpuTotal = resourceRepository.findCountByType("CPU");
        int memoryTotal = resourceRepository.findCountByType("MEMORY");
        int diskTotal = resourceRepository.findCountByType("DISK");

//        Calendar calendar = Calendar.getInstance();
//        for(int i = 0; i < 3; i++){
//            calendar.set(Calendar.DATE, -i);
//            int count=jobRepository.findCpuCountByDate(calendar.getTime());
//            ResourceRemain resourceRemain = new ResourceRemain(count, calendar.getTime(),"CPU");
//            resourceRemainList.add(resourceRemain);
//        }
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
