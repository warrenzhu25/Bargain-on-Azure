package price.azzure.bargain.repository;

import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.ResourceSupply;
import price.azzure.bargain.entity.ResourceType;

import java.util.List;

public interface ResourceSupplyRepository extends CrudRepository<ResourceSupply, Long> {
    List<ResourceSupply> findByType(ResourceType type);
}
