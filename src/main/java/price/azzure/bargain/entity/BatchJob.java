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

    private String name;

    private Date startTime = new Date();

    private Double price;

    private Date deadline;

    private Date suggestDeadline;

    private double suggestedPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "jobStatus")
    private JobStatus status = JobStatus.SUBMITTED;

    @Enumerated(EnumType.STRING)
    @Column(name = "jobType")
    private JobType type = JobType.MACHINE_LEARNING;

    @ManyToOne
    @JoinColumn(name = "details")
    private JobDetail detail = new JobDetail();
}
