package parkingLot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import parkingLot.enums.VehicleTypes;
import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="VEH_TYPE")
public abstract class VehicleModel extends  BaseModel{

/*
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
*/
    @Column(unique = true)
    private String plate;
    private VehicleTypes type;

    protected  VehicleModel(){}

}
