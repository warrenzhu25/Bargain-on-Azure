package price.azzure.bargain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "JobDetail")
public class JobDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cpuCount = new Random().nextInt(50) + 20;

    private Integer memoryCount = new Random().nextInt(50) + 10;

    private Integer diskCount = new Random().nextInt(50) + 30;

    @Enumerated(EnumType.STRING)
    @Column(name = "jobType")
    private JobType type;

    public Map<ResourceType, Integer> getEstimatedResourceUsage(){
        //TODO: an algorithm to estimate resource required for running the job
        HashMap<ResourceType, Integer> resourceUsage =  new HashMap<>();

        resourceUsage.put(ResourceType.CPU, cpuCount);
        resourceUsage.put(ResourceType.MEMORY, memoryCount);
        resourceUsage.put(ResourceType.DISK, diskCount);

        return resourceUsage;
    }
}
