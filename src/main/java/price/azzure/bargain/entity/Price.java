package price.azzure.bargain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "BatchJob")
public class Price {

    private Date time;
    private ResourceType type;
    private double price;
}
