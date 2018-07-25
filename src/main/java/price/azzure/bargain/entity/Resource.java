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
@Table(name = "Resource")
public class Resource {

    @Id
    private Long id;

    private Integer count;

    @Enumerated(EnumType.STRING)
    @Column(name = "resourceType")
    private ResourceType type;

    private double cost;

    private double price;
}
