package price.azzure.bargain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.BatchJob;
import price.azzure.bargain.entity.JobStatus;

import java.util.Date;
import java.util.List;

public interface BatchJobRepository extends CrudRepository<BatchJob, Long> {

    @Query("SELECT j FROM BatchJob j WHERE ?1 BETWEEN j.startTime AND j.deadline")
    List<BatchJob> findActiveJobsByDate(Date date);

    List<BatchJob> findByStartTimeAfterAndDeadlineBeforeAndStatus(Date start, Date deadline, JobStatus status);

    List<BatchJob> findByStartTimeBefore(Date date);
}
