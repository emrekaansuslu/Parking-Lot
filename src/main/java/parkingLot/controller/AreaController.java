package parkingLot.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import parkingLot.model.AreaModel;
import parkingLot.model.CheckModel;
import parkingLot.repository.AreaRepository;
import parkingLot.repository.CheckRepository;
import parkingLot.utils.ParkingAreaUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@Controller
@RequestMapping(value="/area", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class AreaController {

    @Autowired
    AreaRepository repository;

    @Autowired
    CheckRepository checkRepository;


    ParkingAreaUtils utils = new ParkingAreaUtils();

    @RequestMapping(value = "/addParkingArea")
    @ResponseBody
    @PostMapping
    public ResponseEntity addParkingArea(@RequestBody AreaModel area){

        if(utils.checkPriceList(area))
        {
            AreaModel result = repository.save(area);
            if(result != null){
                log.info("Area was added! {}",result);
                return new ResponseEntity(result, HttpStatus.OK);
            }
            else {
                log.error("Invalid hour-price list!");
                return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
            }
        }
        else {
            log.error("Bad request error!");
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/updateParkingArea/{id}")
    @ResponseBody
    @PostMapping
    public ResponseEntity updateParkingArea(@RequestBody AreaModel area, @PathVariable long id)
    {
        AreaModel areaModel = repository.findById(id);
        if(areaModel != null)
        {
            areaModel.setName(area.getName());
            areaModel.setCapacity(area.getCapacity());
            areaModel.setCity(area.getCity());
            areaModel.setPrice(area.getPrice());

            AreaModel result = null;
            result = repository.save(areaModel);
            if(result != null){
                log.info("Id {} Area  was updated! {}",id,result);
                return new ResponseEntity(result, HttpStatus.OK);
            }
            else {
                log.error("Id {} Area  can  not updated! ",id);
                return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
            }
        } else {
            log.error("Bad request error!");
            return new ResponseEntity(areaModel, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/getDailyIncome/{dateTime}")
    @ResponseBody
    @GetMapping
    public ResponseEntity getDailyIncome( @PathVariable String dateTime)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime formatDateTime;
        try {
            formatDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            log.error("Invalid date time format! Format should be dd-MM-yyyy");
            return new ResponseEntity(-1,HttpStatus.BAD_REQUEST);
        }

        List<CheckModel> bilets = checkRepository.findAllByCheckOutDateContains(dateTime);
        double income = 0;
        for(CheckModel ch : bilets) {
            income = income + ch.getFee();
        }
        return new ResponseEntity(income,HttpStatus.OK);
    }


    @RequestMapping(value = "/deleteParkingArea/{id}")
    @ResponseBody
    @DeleteMapping
    public ResponseEntity deleteParkingArea(@PathVariable long id)
    {
        AreaModel areaModel = repository.findById(id);
        if(areaModel != null)
        {
            repository.delete(areaModel);
            log.info("Delete operation is done !");
            return new ResponseEntity("Delete operation is done.", HttpStatus.OK);
        } else {
            log.error("Delete operation can not completed!");
            return new ResponseEntity("Delete operation is done.", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/getArea/{name}")
    @ResponseBody
    @GetMapping
    public ResponseEntity getAreaByName(@PathVariable String name)
    {
        AreaModel areaModel = repository.findByName(name);
        if(areaModel != null)
        {
            log.info("Get by name operation is done");
            return new ResponseEntity(areaModel, HttpStatus.OK);
        } else {
            log.error("Get by name operation cant be done !");
            return new ResponseEntity( HttpStatus.BAD_REQUEST);
        }
    }
}




