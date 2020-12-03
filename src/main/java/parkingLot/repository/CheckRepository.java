package parkingLot.repository;

import parkingLot.model.CheckModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parkingLot.model.VehicleModel;

import java.util.List;

@Repository
public interface CheckRepository extends  JpaRepository<CheckModel, Long>{
    CheckModel findById(long id);
    List<CheckModel> findAllByAreaId(long areaId);
    List<CheckModel> findAllByVehiclePlate(String vehicleName);
    List<CheckModel> findAllByCheckOutDateContains(String dateTime);
}
