package parkingLot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@AllArgsConstructor
@Data
@Entity
@Table(name = "area")
public class AreaModel extends  BaseModel {

    private String name;
    private int capacity;
    private String city;
    private ArrayList<HourPricePair> price;
    public  AreaModel(){}

}
