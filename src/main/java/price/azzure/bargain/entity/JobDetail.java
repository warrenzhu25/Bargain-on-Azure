package price.azzure.bargain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "JobDetail")
public class JobDetail {

    @Id
    private Long id;

    private Integer cpuCount;

    private Integer memoryCount;

    private Integer diskCount;

    private JobType type;

    public Map<ResourceType, Integer> getEstimatedResourceUsage(){
        //TODO: an algorithm to estimate resource required for running the job
        return new HashMap<>();
    }
}
