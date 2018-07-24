package price.azzure.bargain.repository;

import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.BatchJob;

public interface BatchJobRepository extends CrudRepository<BatchJob, Long> {
}
