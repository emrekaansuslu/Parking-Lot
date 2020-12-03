package parkingLot.repository;

import parkingLot.model.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
public interface VehicleRepository extends  VehicleBaseRepository<VehicleModel>{



}
