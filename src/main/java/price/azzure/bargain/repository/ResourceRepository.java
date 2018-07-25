package price.azzure.bargain.repository;

import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.Resource;
import price.azzure.bargain.entity.ResourceType;

import java.util.List;

public interface ResourceRepository extends CrudRepository<Resource, Long> {

    List<Resource> findByType(ResourceType type);
}
