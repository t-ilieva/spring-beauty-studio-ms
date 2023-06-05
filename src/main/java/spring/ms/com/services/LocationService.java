package spring.ms.com.services;

import org.springframework.stereotype.Service;
import spring.ms.com.data.repository.LocationRepository;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
}
