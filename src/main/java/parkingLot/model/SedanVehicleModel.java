package parkingLot.model;

import lombok.Data;
import parkingLot.enums.VehicleTypes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@DiscriminatorValue("SEDAN")
public class SedanVehicleModel extends VehicleModel {
    private String plate;
    private  boolean isParked;

    public  SedanVehicleModel(){}
    public SedanVehicleModel(String plate, VehicleTypes type) {
        super(plate,type);
        this.plate = plate;
    }
}
