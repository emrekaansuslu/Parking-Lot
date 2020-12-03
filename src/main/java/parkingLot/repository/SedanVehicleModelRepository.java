package parkingLot.repository;

import parkingLot.model.SedanVehicleModel;

import javax.transaction.Transactional;

@Transactional
public interface SedanVehicleModelRepository extends VehicleBaseRepository<SedanVehicleModel> {

}
