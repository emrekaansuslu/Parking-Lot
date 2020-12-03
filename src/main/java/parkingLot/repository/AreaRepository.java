package parkingLot.repository;

import parkingLot.model.AreaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository  extends  JpaRepository<AreaModel,Long>{

    AreaModel findById(long id);
    AreaModel findByName(String name);

}
