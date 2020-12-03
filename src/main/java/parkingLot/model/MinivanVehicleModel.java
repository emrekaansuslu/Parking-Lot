package parkingLot.model;

import lombok.Data;
import parkingLot.enums.VehicleTypes;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@DiscriminatorValue("MINIVAN")
public class MinivanVehicleModel extends  VehicleModel {
    private  String plate;
    private  boolean isParked;

    public  MinivanVehicleModel(){}
    public MinivanVehicleModel(String plate, VehicleTypes type) {
        super(plate,type);
        this.plate = plate;
    }
}
