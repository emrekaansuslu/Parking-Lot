package parkingLot.controller;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parkingLot.enums.VehicleTypes;
import parkingLot.model.AreaModel;
import parkingLot.model.CheckModel;
import parkingLot.model.VehicleModel;
import parkingLot.repository.CheckRepository;
import parkingLot.repository.VehicleRepository;

import javax.persistence.Inheritance;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@Inheritance
//@RequestMapping(/*, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }*/)
public abstract class VehicleController<T extends VehicleModel> {

    @Autowired
    CheckRepository checkRepository;

    @RequestMapping("/add/{plate}")
    public @ResponseBody T add(@PathVariable String plate)
    {
        return addVehicle(plate);
    }

    protected abstract T addVehicle(String plate);

    @RequestMapping("{plate}")
    public @ResponseBody
    List<CheckModel> info(@PathVariable String plate)
    {
        List<CheckModel> history = null;
        history = checkRepository.findAllByVehiclePlate(plate);
        return  history;
    }




    /*
    @Autowired
    VehicleRepository repository;

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addVehicle(@RequestBody VehicleModel vehicle){
        VehicleModel result = repository.save(vehicle);
        if(result != null){
            log.info("Vehicle was added! {}",result);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        else {
            log.error("Vehicle can not be added !");
            return new ResponseEntity("Command cannot be created", HttpStatus.BAD_REQUEST);
        }
    }
*/
}
