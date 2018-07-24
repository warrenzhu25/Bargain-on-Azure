package price.azzure.bargain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResourceRemain {
    double count;
    Date date;
    String resourceType;

    public ResourceRemain(int count, Date date, String resourceType){
        this.count=count;
        this.date=date;
        this.resourceType=resourceType;
    }
}
