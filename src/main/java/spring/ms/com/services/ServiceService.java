package spring.ms.com.services;

import org.springframework.stereotype.Service;
import spring.ms.com.data.repository.ServiceRepository;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }
}
