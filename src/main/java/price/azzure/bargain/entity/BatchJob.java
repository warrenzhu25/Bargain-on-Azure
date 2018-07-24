package price.azzure.bargain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "BatchJob")
public class BatchJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startTime;

    private double price;

    private Date deadline;

    private Date suggestDeadline;

    private double suggestedPrice;

    private JobStatus status;

    private JobDetail detail;
}
