package price.azzure.bargain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import price.azzure.bargain.repository.ActiveJobRepository;
import price.azzure.bargain.repository.ResourceSupplyRepository;

@RestController
public class WebController {

    @Autowired
    private ResourceSupplyRepository resourceSupplyRepo;

    @Autowired
    private ActiveJobRepository activeJobRepo;


}
