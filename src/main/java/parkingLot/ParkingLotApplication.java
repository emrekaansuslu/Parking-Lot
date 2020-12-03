package parkingLot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import parkingLot.enums.VehicleTypes;
import parkingLot.model.VehicleModel;
import parkingLot.repository.VehicleRepository;


@SpringBootApplication
public class ParkingLotApplication {

	public static void main(String[] args) {

		SpringApplication.run(ParkingLotApplication.class, args);
	}



}
