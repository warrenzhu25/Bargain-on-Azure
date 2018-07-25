package price.azzure.bargain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "jobStatus")
    private JobStatus status;

    @ManyToOne
    @JoinColumn(name = "details")
    @JsonBackReference
    private JobDetail detail;
}
