package spring.ms.com.services;

import spring.ms.com.data.entity.Category;
import spring.ms.com.data.entity.Service;
import spring.ms.com.data.repository.CategoryRepository;
import spring.ms.com.data.repository.ServiceRepository;
import spring.ms.com.rest.request.CategoryRequest;
import spring.ms.com.rest.request.ServiceRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.rest.response.ServiceResponse;
import spring.ms.com.rest.transformer.CategoryTransformer;
import spring.ms.com.rest.transformer.ServiceTransformer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public ServiceService(ServiceRepository serviceRepository, CategoryRepository categoryRepository, CategoryService categoryService) {
        this.serviceRepository = serviceRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
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

        if (serviceRepository.findByName(serviceRequest.getName()).isEmpty()) {
            Service service = ServiceTransformer
                    .toServiceEntity(serviceRequest);

            CategoryRequest categoryRequest = serviceRequest.getCategoryRequest();
            Optional<Category> category;

            if(categoryRepository.findByName(categoryRequest.getName()) == null){
                int id = categoryService.createCategory(categoryRequest);
                category = categoryService.getById(id).map(CategoryTransformer::toCategoryEntity);
            }
            else {
                category = categoryRepository.findByName(categoryRequest.getName());
            }

            service.setServiceCategory(category.get());

            return serviceRepository.save(service).getId();
        } else {
            return -1;
        }

    }

    public int editService(int id, ServiceResponse serviceResponse){
        String name = serviceResponse.getName();

        if (serviceRepository.findByName(serviceResponse.getName()).isEmpty() ||
                serviceRepository.findById(id).get().getName().equals(name)) {
            Service service = ServiceTransformer
                    .toServiceEntity(serviceResponse);

            CategoryResponse categoryResponse = serviceResponse.getCategoryResponse();
            Category category = categoryRepository.findByName(categoryResponse.getName()).get();

            service.setServiceCategory(category);
            service.setId(id);
            return serviceRepository.save(service).getId();
        } else {
            return -1;
        }
    }

    public void deleteService(int id){
        serviceRepository.deleteById(id);
    }
}
