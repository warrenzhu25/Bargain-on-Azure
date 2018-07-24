package price.azzure.bargain.repository;

import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.BatchJob;

import java.util.Date;
import java.util.List;

public interface BatchJobRepository extends CrudRepository<BatchJob, Long> {

    @Query("SELECT j FROM BatchJob j WHERE ?1 BETWEEN j.startTime AND j.deadline")
    List<BatchJob> findActiveJobsByDate(Date date);

}
