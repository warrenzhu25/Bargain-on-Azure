package price.azzure.bargain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ResourceSupply")
public class ResourceSupply {

    @Id
    private Long id;

    private Integer count;

    private Integer cost;

    private Integer basicPrice;

    private ResourceType type;
}
