package price.azzure.bargain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "JobDetail")
public class JobDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cpuCount = 30;

    private Integer memoryCount = 40;

    private Integer diskCount = 50;

    public Map<ResourceType, Integer> getEstimatedResourceUsage(){
        //TODO: an algorithm to estimate resource required for running the job
        HashMap<ResourceType, Integer> resourceUsage =  new HashMap<>();

        resourceUsage.put(ResourceType.CPU, cpuCount);
        resourceUsage.put(ResourceType.MEMORY, memoryCount);
        resourceUsage.put(ResourceType.DISK, diskCount);

        return resourceUsage;
    }
}
