package parkingLot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import parkingLot.enums.VehicleTypes;
import parkingLot.model.SedanVehicleModel;
import parkingLot.repository.SedanVehicleModelRepository;

@Slf4j
@Controller
@RequestMapping("/sedan")
public class SedanVehicleController extends VehicleController <SedanVehicleModel> {

    @Autowired
    SedanVehicleModelRepository repository;
    @Override
    protected SedanVehicleModel addVehicle(String plate)
    {
        try {
            SedanVehicleModel model = new SedanVehicleModel(plate, VehicleTypes.minivan);
            SedanVehicleModel result = repository.save(model);
            if (result != null) {
                log.info("SEDAN Vehicle is added. It's plate is : {}",plate);
                return result;
            } else {
                log.error("SEDAN Vehicle can not be added.");
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



}
