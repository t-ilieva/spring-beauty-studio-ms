package spring.ms.com.rest.transformer;

import spring.ms.com.data.entity.Service;
import spring.ms.com.rest.request.ServiceRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.rest.response.LocationResponse;
import spring.ms.com.rest.response.ServiceResponse;

public class ServiceTransformer {

    public static ServiceResponse toServiceResponse(Service service){
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setId(service.getId());
        serviceResponse.setName(service.getName());
        serviceResponse.setDescription(service.getDescription());
        serviceResponse.setDuration(service.getDuration());
        serviceResponse.setPrice(service.getPrice());

        if(service.getServiceCategory() != null) {
            CategoryResponse categoryResponse = CategoryTransformer
                    .toCategoryResponse(service.getServiceCategory());

            serviceResponse.setCategoryResponse(categoryResponse);
        } else {
            serviceResponse.setCategoryResponse(new CategoryResponse());
        }

        return serviceResponse;
    }

    public static Service toServiceEntity(ServiceRequest serviceRequest){
        Service service = new Service();
        service.setName(serviceRequest.getName());
        service.setDescription(serviceRequest.getDescription());
        service.setDuration(serviceRequest.getDuration());
        service.setPrice(serviceRequest.getPrice());

        return service;
    }

    public static Service toServiceEntity(ServiceResponse serviceResponse){
        Service service = new Service();
        service.setId(serviceResponse.getId());
        service.setName(serviceResponse.getName());
        service.setDescription(serviceResponse.getDescription());
        service.setDuration(serviceResponse.getDuration());
        service.setPrice(serviceResponse.getPrice());

        return service;
    }
}
