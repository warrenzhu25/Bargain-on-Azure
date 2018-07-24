package price.azzure.bargain.repository;

import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.BatchJob;

public interface ActiveJobRepository extends CrudRepository<BatchJob, Long> {
}
