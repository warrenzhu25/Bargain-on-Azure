package price.azzure.bargain.dto;

import lombok.Data;
import price.azzure.bargain.entity.ResourceType;

import java.util.Date;

@Data
public class Price {
    double price;
    String date;
    ResourceType resourceType;

    public Price(double price, String date, ResourceType resourceType) {
        this.price = price;
        this.date = date;
        this.resourceType = resourceType;
    }
}
