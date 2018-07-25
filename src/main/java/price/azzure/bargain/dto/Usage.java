package price.azzure.bargain.dto;

import lombok.Data;
import price.azzure.bargain.entity.ResourceType;

@Data
public class Usage {
    int usage;
    String date;
    ResourceType resourceType;

    public Usage(int usage, String date, ResourceType resourceType) {
        this.usage = usage;
        this.date = date;
        this.resourceType = resourceType;
    }
}
