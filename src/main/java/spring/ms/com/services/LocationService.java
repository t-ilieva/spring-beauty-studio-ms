package spring.ms.com.services;

import org.springframework.stereotype.Service;
import spring.ms.com.data.entity.Location;
import spring.ms.com.data.repository.LocationRepository;
import spring.ms.com.rest.request.LocationRequest;
import spring.ms.com.rest.response.LocationResponse;
import spring.ms.com.rest.transformer.LocationTransformer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationResponse> getAll(){
        return locationRepository
                .findAll()
                .stream()
                .map(LocationTransformer::toLocationResponse)
                .collect(Collectors.toList());
    }

    public Optional<LocationResponse> getByName(String name){
        return locationRepository
                .findByName(name)
                .map(LocationTransformer::toLocationResponse);
    }

    public Optional<LocationResponse> getById(int id){
        return locationRepository
                .findById(id)
                .map(LocationTransformer::toLocationResponse);
    }

    public int createLocation(LocationRequest locationRequest) {
        if(locationRepository.findByAddress(locationRequest.getAddress()).isEmpty()){
            Location location = LocationTransformer
                    .toLocationEntity(locationRequest);
            return locationRepository.save(location).getId();
        }else{
            return -1;
        }
    }

    public int editLocation(int id, LocationRequest locationRequest){

        String address = locationRequest.getAddress();

        if (locationRepository.findByAddress(locationRequest.getAddress()).isEmpty() ||
                locationRepository.findById(id).get().getAddress().equals(address)) {
            Location location = LocationTransformer
                    .toLocationEntity(locationRequest);

            location.setId(id);
            return locationRepository.save(location).getId();
        } else {
            return -1;
        }
    }

    public void deleteLocation(int id){
        locationRepository.deleteById(id);
    }
}
