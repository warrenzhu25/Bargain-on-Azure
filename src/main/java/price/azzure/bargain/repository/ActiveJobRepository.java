package price.azzure.bargain.repository;

import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.ActiveJob;

public interface ActiveJobRepository extends CrudRepository<ActiveJob, Long> {
}
