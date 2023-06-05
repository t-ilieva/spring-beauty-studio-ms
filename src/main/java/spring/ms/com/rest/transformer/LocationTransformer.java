package spring.ms.com.rest.transformer;

import spring.ms.com.data.entity.Location;
import spring.ms.com.rest.request.LocationRequest;
import spring.ms.com.rest.response.LocationResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationTransformer {

    private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public static LocationResponse toLocationResponse(Location location){
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setId(location.getId());
        locationResponse.setName(location.getName());
        locationResponse.setAddress(location.getAddress());
        locationResponse.setRating(location.getRating());
        locationResponse.setDateOpened(DATE_TIME_FORMATTER.format(location.getDateOpened()));

        return locationResponse;
    }


    public static Location toLocationEntity(LocationRequest locationRequest) throws ParseException {
        Date date = DATE_TIME_FORMATTER.parse(locationRequest.getDateOpened());
        Location location = new Location();
        location.setName(locationRequest.getName());
        location.setAddress(locationRequest.getAddress());
        location.setRating(locationRequest.getRating());
        location.setDateOpened(date);

        return location;
    }

    public static Location toLocationEntity(LocationResponse locationResponse) throws ParseException {
        Date date = DATE_TIME_FORMATTER.parse(locationResponse.getDateOpened());
        Location location = new Location();
        location.setId(locationResponse.getId());
        location.setName(locationResponse.getName());
        location.setAddress(locationResponse.getAddress());
        location.setRating(locationResponse.getRating());
        location.setDateOpened(date);

        return location;
    }


}
