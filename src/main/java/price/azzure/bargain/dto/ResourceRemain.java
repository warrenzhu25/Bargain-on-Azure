package price.azzure.bargain.dto;

import lombok.Data;
import price.azzure.bargain.entity.ResourceType;

import java.util.Date;

@Data
public class ResourceRemain {
    int count;
    String date;
    ResourceType resourceType;

    public ResourceRemain(int count, String date, ResourceType resourceType){
        this.count=count;
        this.date=date;
        this.resourceType=resourceType;
    }
}
