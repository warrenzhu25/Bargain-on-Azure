package price.azzure.bargain.entity;

import java.util.HashMap;
import java.util.Map;

public class JobDetail {

    private String input;
    private String output;
    private JobType type;

    public Map<ResourceType, Integer> getEstimatedResourceUsage(){
        //TODO: an algorithm to estimate resource required for running the job
        return new HashMap<>();
    }
}
