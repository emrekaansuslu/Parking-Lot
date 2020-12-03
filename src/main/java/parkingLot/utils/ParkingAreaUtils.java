package parkingLot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import parkingLot.model.AreaModel;
import parkingLot.model.HourPricePair;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
public class ParkingAreaUtils {

    public  boolean checkPriceList(AreaModel model) {
        double[] hours = getPriceArray(model.getPrice());
        if( hours != null)
        {
            for(int i=0;i<hours.length;i++)
            {
                if(hours[i] == -1)
                {
                    log.error("Price list is invalid. List have -1 value.");
                    return false;
                }
            }
        } else {
            log.error("Price list is invalid. Price list is null.");
            return false;
        }

        return true;
    }

    public double[] getPriceArray(ArrayList<HourPricePair> prices){
        double[] hours = new double[24];
        Arrays.fill(hours, -1);

        for(HourPricePair pair : prices)
        {
            String key = pair.getHour();
            int index = key.indexOf("-");
            int size = key.length();
            int beginIndex = Integer.parseInt(key.substring(0,index));
            int lastIndex = Integer.parseInt(key.substring(index+1,size));
            for(int x=beginIndex;x<lastIndex;x++)
            {
                if(hours[x]  != -1 ){

                    log.error("Price list is invalid.");
                    return null;
                }
                hours[x] = pair.getPrice();
            }
        }
        return hours;
    }
}
