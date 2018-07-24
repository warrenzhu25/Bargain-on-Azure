package price.azzure.bargain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.Resource;

public interface ResourceRepository extends CrudRepository<Resource, Long> {

    @Query("SELECT r.count FROM Resource r WHERE r.resourceType = ?1")
    int findCountByType(String resourceType);
}
