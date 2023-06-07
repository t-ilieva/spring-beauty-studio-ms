package spring.ms.com.services;

import org.springframework.stereotype.Service;
import spring.ms.com.data.entity.Category;
import spring.ms.com.data.entity.Employee;
import spring.ms.com.data.entity.Location;
import spring.ms.com.data.repository.CategoryRepository;
import spring.ms.com.data.repository.EmployeeRepository;
import spring.ms.com.data.repository.LocationRepository;
import spring.ms.com.rest.request.CategoryRequest;
import spring.ms.com.rest.request.EmployeeRequest;
import spring.ms.com.rest.request.LocationRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.rest.response.EmployeeResponse;
import spring.ms.com.rest.response.LocationResponse;
import spring.ms.com.rest.transformer.CategoryTransformer;
import spring.ms.com.rest.transformer.EmployeeTransformer;
import spring.ms.com.rest.transformer.LocationTransformer;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final LocationService locationService;
    private final CategoryService categoryService;

    public EmployeeService(EmployeeRepository employeeRepository, LocationRepository locationRepository, CategoryRepository categoryRepository, LocationService locationService, CategoryService categoryService) {
        this.employeeRepository = employeeRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.locationService = locationService;
        this.categoryService = categoryService;
    }

    public List<EmployeeResponse> getAll(){
        return employeeRepository
                .findAll()
                .stream()
                .map(EmployeeTransformer::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeResponse> getByName(String firstName, String lastName){
        return employeeRepository
                .findByName(firstName, lastName)
                .map(EmployeeTransformer::toEmployeeResponse);
    }

    public Optional<EmployeeResponse> getById(int id){
        return employeeRepository
                .findById(id)
                .map(EmployeeTransformer::toEmployeeResponse);
    }

    public int createEmployee(EmployeeRequest employeeRequest) throws ParseException {
        Employee employee = EmployeeTransformer
                .toEmployeeEntity(employeeRequest);

        CategoryRequest categoryRequest = employeeRequest.getCategoryRequest();
        Optional<Category> category;

        LocationRequest locationRequest = employeeRequest.getLocationRequest();
        Optional<Location> location;

        if (categoryRepository.findByName(categoryRequest.getName()).isEmpty()) {
            int id = categoryService.createCategory(categoryRequest);
            category = categoryService.getById(id).map(CategoryTransformer::toCategoryEntity);
        } else {
            category = categoryRepository.findByName(categoryRequest.getName());
        }

        if (locationRepository.findByName(locationRequest.getName()).isEmpty()) {
            int id = locationService.createLocation(locationRequest);
            location = locationService.getById(id).map(LocationTransformer::toLocationEntity);
        } else
        {
            location = locationRepository.findByName(locationRequest.getName());
        }


        employee.setEmployeeLocation(location.get());
        employee.setEmployeeCategory(category.get());
        return employeeRepository.save(employee).getId();
    }

//    public int editEmployee(int id, Employee employee){
//        employee.setId(id);
//
//        return employeeRepository.save(employee).getId();
//    }
//    public void deleteEmployee(int id){
//        employeeRepository.deleteById(id);
//    }

    public int editEmployee(int id, EmployeeResponse employeeResponse){
        Employee employee = EmployeeTransformer
                .toEmployeeEntity(employeeResponse);

        CategoryResponse categoryResponse = employeeResponse.getCategoryResponse();
        LocationResponse locationResponse = employeeResponse.getLocationResponse();

        Category category = categoryRepository.findByName(categoryResponse.getName()).get();
        Location location = locationRepository.findByName(locationResponse.getName()).get();

        employee.setEmployeeCategory(category);
        employee.setEmployeeLocation(location);
        employee.setId(id);

        return employeeRepository.save(employee).getId();
    }
    public void deleteEmployee(int id){
        employeeRepository.deleteById(id);
    }
}
