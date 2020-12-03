package parkingLot.model;

import lombok.Data;
import parkingLot.enums.VehicleTypes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@DiscriminatorValue("SUV")
public class SuvVehicleModel extends  VehicleModel {
    private  String plate;
    private boolean isParked;

    public  SuvVehicleModel(){}
    public SuvVehicleModel(String plate, VehicleTypes type) {
        super(plate,type);
        this.plate = plate;
    }
}
