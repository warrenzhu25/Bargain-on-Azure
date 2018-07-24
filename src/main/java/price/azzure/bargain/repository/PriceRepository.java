package price.azzure.bargain.repository;

import org.springframework.data.repository.CrudRepository;
import price.azzure.bargain.entity.BatchJob;
import price.azzure.bargain.entity.Price;

public interface PriceRepository extends CrudRepository<Price, Long> {
}
