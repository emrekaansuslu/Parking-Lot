package parkingLot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import parkingLot.enums.VehicleTypes;
import parkingLot.model.SuvVehicleModel;
import parkingLot.repository.SuvVehicleModelRepository;

@Slf4j
@Controller
@RequestMapping("/suv")
public class SuvVehicleController extends  VehicleController<SuvVehicleModel> {

    @Autowired
    SuvVehicleModelRepository repository;


    @Override
    protected SuvVehicleModel addVehicle(String plate)
    {
        try {
            SuvVehicleModel model = new SuvVehicleModel(plate, VehicleTypes.minivan);
            SuvVehicleModel result = repository.save(model);
            if (result != null) {
                log.info("SUV Vehicle is added. It's plate is : {}",plate);
                return result;
            } else {
                log.error("SUV Vehicle can not be added.");
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
