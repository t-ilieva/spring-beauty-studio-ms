package spring.ms.com.services;

import spring.ms.com.data.entity.Service;
import spring.ms.com.data.repository.ServiceRepository;
import spring.ms.com.rest.request.ServiceRequest;
import spring.ms.com.rest.response.ServiceResponse;
import spring.ms.com.rest.transformer.ServiceTransformer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceResponse> getAll(){
        return serviceRepository
                .findAll()
                .stream()
                .map(ServiceTransformer::toServiceResponse)
                .collect(Collectors.toList());
    }

    public List<ServiceResponse> getByCategory(String category){
        return serviceRepository
                .findByCategory(category)
                .stream()
                .map(ServiceTransformer::toServiceResponse)
                .collect(Collectors.toList());
    }

    public Optional<ServiceResponse> getById(int id){
        return serviceRepository
                .findById(id)
                .map(ServiceTransformer::toServiceResponse);
    }

    public int createService(ServiceRequest serviceRequest){
        Service service = ServiceTransformer
                .toServiceEntity(serviceRequest);
        return serviceRepository.save(service).getId();
    }

    public void deleteService(int id){
        serviceRepository.deleteById(id);
    }
}
