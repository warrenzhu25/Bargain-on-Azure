package price.azzure.bargain.repository;

import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.JobDetail;

public interface JobDetailsRepository extends CrudRepository<JobDetail, Long> {
}
