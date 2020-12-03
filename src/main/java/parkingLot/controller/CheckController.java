package parkingLot.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import parkingLot.model.AreaModel;
import parkingLot.model.CheckModel;
import parkingLot.model.SedanVehicleModel;
import parkingLot.model.VehicleModel;
import parkingLot.repository.AreaRepository;
import parkingLot.repository.CheckRepository;
import parkingLot.repository.SedanVehicleModelRepository;
import parkingLot.repository.VehicleRepository;
import parkingLot.utils.ParkingAreaUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static parkingLot.enums.VehicleTypes.sedan;


@Slf4j
@RestController
@RequestMapping(value="/check")
public class CheckController {

    @Autowired
    CheckRepository checkRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    SedanVehicleModelRepository sedanVehicleRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    ParkingAreaUtils util = new ParkingAreaUtils();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    @RequestMapping(value = "/check-in/{areaId}/{vehicleName}/{dateTime}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity checkIn(@PathVariable long areaId, @PathVariable String vehicleName,@PathVariable String dateTime){
        LocalDateTime formatDateTime;
        try {
            formatDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            log.error("Invalid date time format! Format should be dd-MM-yyyy HH:mm");
            return new ResponseEntity(" Format should be dd-MM-yyyy HH:mm",HttpStatus.BAD_REQUEST);
        }


        AreaModel area = areaRepository.findById(areaId);
        VehicleModel vehicle = vehicleRepository.findByPlate(vehicleName);
        if(checkBilet(area,vehicle,vehicleName))
        {
            CheckModel result = checkRepository.save(new CheckModel(formatDateTime.format(formatter),"",vehicleName,areaId,0));

            if(result != null){
                log.info("Check-in successfully created!");
                return new ResponseEntity(result,HttpStatus.OK);
            } else {
                log.error("Check-in can not be created!");
                return new ResponseEntity("Check-in error!",HttpStatus.BAD_REQUEST);
            }
        } else {
            log.error("Check-in can not be created!");
            return new ResponseEntity("Check-in input error!",HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/check-out/{id}/{dateTime}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity checkOut( @PathVariable long id, @PathVariable String dateTime){

        LocalDateTime formatDateTime;
        try {
            formatDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            log.error("Invalid date time format! Format should be dd-MM-yyyy HH:mm");
            return new ResponseEntity(" Format should be dd-MM-yyyy HH:mm",HttpStatus.BAD_REQUEST);
        }

        CheckModel check = checkRepository.findById(id);
        if(check != null)
        {
            VehicleModel vehicleModel = vehicleRepository.findByPlate(check.getVehiclePlate());
            AreaModel area = areaRepository.findById((long)check.getAreaId());
            if(check.getFee() != 0)
            {
                log.error("Check-out input is not valid. Fee have to be zero !");
                return new ResponseEntity("Check-out input error",HttpStatus.BAD_REQUEST);
            } else {

                    double fee = calculatePrice(formatDateTime.format(formatter),check,vehicleModel,area);
                    if(fee != -1) {
                        check.setFee(fee);
                        check.setCheckOutDate(formatDateTime.format(formatter));
                        CheckModel checkResult = checkRepository.save(check);

                        if( checkResult != null )
                        {
                            log.info("Check-out operation is successfully done");
                            return new ResponseEntity(checkResult,HttpStatus.OK);
                        } else {
                            log.info("Check-out can not be done. Save is failed.");
                            return new ResponseEntity("Check-out input error",HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        return new ResponseEntity("Check-out input error",HttpStatus.BAD_REQUEST);
                    }

            }
        } else {
            log.error("Check-out input is not valid. Can not be found bilet with given id!");
            return  new ResponseEntity("Check-out input error",HttpStatus.BAD_REQUEST);
        }

    }



    public double calculatePrice(String endDate,CheckModel model,VehicleModel vehicle,AreaModel area)
    {

        LocalDateTime beginDateTime =  LocalDateTime.parse(model.getCheckInDate(), formatter);;
        LocalDateTime endDateTime =  LocalDateTime.parse(endDate, formatter);;

        Duration duration = Duration.between(beginDateTime, endDateTime);
        double difference = duration.toMinutes();

        double days = (int)difference/(24*60);
        double time = (difference - (days*24*60)) / 60;
        if( time > Math.floor(time))
        {
            time = Math.floor(time) + 1;
        }
        if(area != null) {

            double[] hours = util.getPriceArray(area.getPrice());
            if( hours != null )
            {
                double totalPrice = ((int)days * hours[23] ) + hours[(int)time];
                return calculateVehiclePrice(vehicle,totalPrice);
            } else {

                log.error("Parking Area Model Price List is invalid !");
                return -1;
            }

        } else {
            log.error("Parking area can not be found !");
            return -1;
        }


    }

    public double calculateVehiclePrice(VehicleModel vehicle, double price) {

        switch(vehicle.getType()){
            case minivan:
                return price + (price*15/100);
            case suv:
                return price + (price/10);
            case sedan:
                return price;
            default:
                return price;

        }
    }

    public boolean checkBilet(AreaModel area, VehicleModel vehicle,String vehicleName)
    {

        if(!checkEmptyLot(area)) {
            log.error("Check-in park area capacity is full!");
            return false;
        }
        if( area == null){
            log.error("Check-in park area can not find !");
            return false;
        }

        if(vehicle == null)
        {
            if( createDefaultVehicle(vehicleName) ) {
                log.info("Default vehicle is created !");
                return true;
            } else {
                log.error("Default vehicle can not be created!");
                return false;
            }
        } else {
            if(!checkCarParkingStatus(vehicleName)) {
                log.error("This vehicle is already parked another area!");
                return false;
            }

        }
        return true;
    }

    public  boolean checkEmptyLot(AreaModel model) {

        long modelID = model.getId();
        List<CheckModel> bilets = checkRepository.findAllByAreaId(modelID);
        int parkCount = 0;
        for (CheckModel check : bilets ) {
            if(check.getCheckOutDate().length() == 0)
            {
                parkCount++;
            }
        }
        if(parkCount < model.getCapacity())
        {
            return true;
        } else {
            return  false;
        }

    }

    public boolean checkCarParkingStatus(String vehicleName)
    {
        List<CheckModel> bilets = checkRepository.findAllByVehiclePlate(vehicleName);
        for(CheckModel check : bilets) {
            if(check.getCheckOutDate().length() == 0) {
                return false;
            }
        }
        return true;

    }

    public boolean createDefaultVehicle(String name){

        SedanVehicleModel model = new SedanVehicleModel(name, sedan);
        SedanVehicleModel result = sedanVehicleRepository.save(model);

        if(result != null) {
            return true;
        } else {
            return false;
        }

    }



}
