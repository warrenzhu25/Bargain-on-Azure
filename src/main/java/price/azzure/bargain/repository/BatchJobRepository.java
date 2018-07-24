package price.azzure.bargain.repository;

import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.BatchJob;

import java.util.Date;
import java.util.List;

public interface BatchJobRepository extends CrudRepository<BatchJob, Long> {

    @Query("SELECT j.cpuCount FROM BatchJob j WHERE ?1 BETWEEN j.startTime AND j.deadline")
    List<Integer> findCpuCountsByDate(Date date);

    @Query("SELECT j.memoryCount FROM BatchJob j WHERE ?1 BETWEEN j.startTime AND j.deadline")
    List<Integer> findMemoryCountsByDate(Date date);

    @Query("SELECT j.diskCount FROM BatchJob j WHERE ?1 BETWEEN j.startTime AND j.deadline")
    List<Integer> findDiskCountsByDate(Date date);

}
