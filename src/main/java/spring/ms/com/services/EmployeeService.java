package spring.ms.com.services;

import org.springframework.stereotype.Service;
import spring.ms.com.data.entity.Employee;
import spring.ms.com.data.repository.EmployeeRepository;
import spring.ms.com.rest.request.EmployeeRequest;
import spring.ms.com.rest.response.EmployeeResponse;
import spring.ms.com.rest.transformer.EmployeeTransformer;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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
        return employeeRepository.save(employee).getId();
    }
    public void deleteEmployee(int id){
        employeeRepository.deleteById(id);
    }
}
