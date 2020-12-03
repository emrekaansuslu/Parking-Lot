package parkingLot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import parkingLot.enums.VehicleTypes;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


@Entity
@Table(name="check")
@Data
@NoArgsConstructor
public class CheckModel extends  BaseModel{
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

*/
    private String checkInDate;
    private String checkOutDate;
    private String vehiclePlate;
    private Long areaId;
    private double fee;


    public CheckModel(String checkInDate,String checkOutDate,
                      String vehiclePlate, Long areaId, double fee)
    {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.vehiclePlate = vehiclePlate;
        this.areaId = areaId;
        this.fee = fee;
    }


}
