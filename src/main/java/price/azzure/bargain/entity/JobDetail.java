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

    private Integer cpuCount;

    private Integer memoryCount;

    private Integer diskCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "jobType")
    private JobType type;

    public Map<ResourceType, Integer> getEstimatedResourceUsage(){
        //TODO: an algorithm to estimate resource required for running the job
        return new HashMap<>();
    }
}
