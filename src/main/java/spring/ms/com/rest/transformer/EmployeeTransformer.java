package spring.ms.com.rest.transformer;

//import spring.ms.com.data.entity.Category;
//import spring.ms.com.data.entity.Location;
import spring.ms.com.data.entity.Employee;
import spring.ms.com.rest.request.EmployeeRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.rest.response.EmployeeResponse;
import spring.ms.com.rest.response.LocationResponse;
import spring.ms.com.rest.response.ServiceResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeTransformer {

    private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public static EmployeeResponse toEmployeeResponse(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employee.getId());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setDateEmployed(DATE_TIME_FORMATTER.format(employee.getDateEmployed()));
        employeeResponse.setEmployeeNumber(employee.getEmployeeNumber());
        employeeResponse.setRating(employee.getRating());

//        CategoryResponse categoryResponse = CategoryTransformer.
//                toCategoryResponse(employee.getEmployeeCategory());

        if(employee.getEmployeeLocation() != null) {
            LocationResponse locationResponse = LocationTransformer.
                    toLocationResponse(employee.getEmployeeLocation());

            employeeResponse.setLocationResponse(locationResponse);
        } else {
            LocationResponse locationResponse = new LocationResponse();
            locationResponse.setAddress("");
            employeeResponse.setLocationResponse(locationResponse);
        }

//        employeeResponse.setCategoryResponse(categoryResponse);

        return employeeResponse;
    }


    public static Employee toEmployeeEntity(EmployeeRequest employeeRequest) {
        Date date = null;
        try {
            date = DATE_TIME_FORMATTER.parse(employeeRequest.getDateEmployed());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setDateEmployed(date);
        employee.setEmployeeNumber(employeeRequest.getEmployeeNumber());
        employee.setRating(employeeRequest.getRating());

        return employee;
    }

    public static Employee toEmployeeEntity(EmployeeResponse employeeResponse) {
        Date date = null;
        try {
            date = DATE_TIME_FORMATTER.parse(employeeResponse.getDateEmployed());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Employee employee = new Employee();
//        Category category = CategoryTransformer
//                .toCategoryEntity(employeeResponse.getCategoryResponse());
//        Location location = LocationTransformer
//                .toLocationEntity(employeeResponse.getLocationResponse());

        employee.setId(employeeResponse.getId());
        employee.setFirstName(employeeResponse.getFirstName());
        employee.setLastName(employeeResponse.getLastName());
        employee.setDateEmployed(date);
        employee.setEmployeeNumber(employeeResponse.getEmployeeNumber());
        employee.setRating(employeeResponse.getRating());
//        employee.setEmployeeCategory(category);
//        employee.setEmployeeLocation(location);

        return employee;
    }
}
