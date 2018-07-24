package price.azzure.bargain.repository;

import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.Resource;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
}
