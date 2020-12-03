package parkingLot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import parkingLot.model.VehicleModel;

import java.util.Optional;

public interface VehicleBaseRepository<T extends VehicleModel> extends JpaRepository<T, Long> {

    T findByPlate(String plate);
    T findById(long id);
}
