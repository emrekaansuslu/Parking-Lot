package parkingLot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import parkingLot.enums.VehicleTypes;
import parkingLot.model.MinivanVehicleModel;
import parkingLot.repository.MinivanVehicleModelRepository;

@Slf4j
@Controller
@RequestMapping("/minivan")
public class MinivanVehicleController extends VehicleController<MinivanVehicleModel> {

    @Autowired
    MinivanVehicleModelRepository repository;


    @Override
    protected MinivanVehicleModel addVehicle(String plate)
    {
        try {
            MinivanVehicleModel model = new MinivanVehicleModel(plate, VehicleTypes.minivan);
            MinivanVehicleModel result = repository.save(model);
            if (result != null) {
                log.info("MINIVAN Vehicle is added. It's plate is : {}",plate);
                return result;
            } else {
                log.error("MINIVAN Vehicle can not be added.");
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
