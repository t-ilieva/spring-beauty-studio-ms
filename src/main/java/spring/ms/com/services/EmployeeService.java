package spring.ms.com.services;

import org.springframework.stereotype.Service;
import spring.ms.com.data.entity.Employee;
import spring.ms.com.data.entity.Location;
import spring.ms.com.data.repository.CategoryRepository;
import spring.ms.com.data.repository.EmployeeRepository;
import spring.ms.com.data.repository.LocationRepository;
import spring.ms.com.rest.request.EmployeeRequest;
import spring.ms.com.rest.request.LocationRequest;
import spring.ms.com.rest.response.EmployeeResponse;
import spring.ms.com.rest.response.LocationResponse;
import spring.ms.com.rest.transformer.EmployeeTransformer;
import spring.ms.com.rest.transformer.LocationTransformer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final LocationRepository locationRepository;
    private final LocationService locationService;

    public EmployeeService(EmployeeRepository employeeRepository, LocationRepository locationRepository, CategoryRepository categoryRepository, LocationService locationService, CategoryService categoryService) {
        this.employeeRepository = employeeRepository;
        this.locationRepository = locationRepository;
        this.locationService = locationService;
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

    public Optional<EmployeeResponse> getByEmployeeNumber(String employeeNumber){
        return employeeRepository
                .findByEmployeeNumber(employeeNumber)
                .map(EmployeeTransformer::toEmployeeResponse);
    }

    public Optional<EmployeeResponse> getById(int id){
        return employeeRepository
                .findById(id)
                .map(EmployeeTransformer::toEmployeeResponse);
    }

    public int createEmployee(EmployeeRequest employeeRequest) {

//        CategoryRequest categoryRequest = employeeRequest.getCategoryRequest();
//        Optional<Category> category;

//        if (categoryRepository.findByName(categoryRequest.getName()).isEmpty()) {
//            int id = categoryService.createCategory(categoryRequest);
//            category = categoryService.getById(id).map(CategoryTransformer::toCategoryEntity);
//        } else {
//            category = categoryRepository.findByName(categoryRequest.getName());
//        }
//        employee.setEmployeeCategory(category.get());

        LocationRequest locationRequest = employeeRequest.getLocationRequest();
        Optional<Location> location;

        if (locationRepository.findByAddress(locationRequest.getAddress()).isEmpty()) {
            int id = locationService.createLocation(locationRequest);
            location = locationService.getById(id).map(LocationTransformer::toLocationEntity);
        } else
        {
            location = locationRepository.findByAddress(locationRequest.getAddress());
        }

        if(employeeRepository.findByEmployeeNumber(employeeRequest.getEmployeeNumber()).isEmpty()){
            Employee employee = EmployeeTransformer
                    .toEmployeeEntity(employeeRequest);
            employee.setEmployeeLocation(location.get());
            return employeeRepository.save(employee).getId();
        }else{
            return -1;
        }
    }

    public int editEmployee(int id, EmployeeResponse employeeResponse) {
        Employee employee = EmployeeTransformer
                .toEmployeeEntity(employeeResponse);

        LocationResponse locationResponse = employeeResponse.getLocationResponse();

        Location location = locationRepository.findByAddress(locationResponse.getAddress()).get();

        String emplNum = employeeRepository.findById(id).get().getEmployeeNumber();

        if (employeeRepository.findByEmployeeNumber(employeeResponse.getEmployeeNumber()).isEmpty() ||
                employeeRepository.findByEmployeeNumber(employeeResponse.getEmployeeNumber()).get().getEmployeeNumber().equals(emplNum)) {
            employee.setEmployeeLocation(location);
            employee.setId(id);

            return employeeRepository.save(employee).getId();
        } else {
            return -1;
        }
    }

    public void deleteEmployee(int id){
        employeeRepository.deleteById(id);
    }
}
