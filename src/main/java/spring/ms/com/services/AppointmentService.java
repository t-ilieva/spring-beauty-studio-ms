package spring.ms.com.services;

import org.springframework.stereotype.Service;
import spring.ms.com.data.entity.Appointment;
import spring.ms.com.data.entity.Location;
import spring.ms.com.data.repository.AppointmentRepository;
import spring.ms.com.data.repository.EmployeeRepository;
import spring.ms.com.data.repository.LocationRepository;
import spring.ms.com.data.repository.ServiceRepository;
import spring.ms.com.rest.request.*;
import spring.ms.com.rest.response.AppointmentResponse;
import spring.ms.com.rest.transformer.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final LocationRepository locationRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final LocationService locationService;
    private final EmployeeService employeeService;
    private final ServiceService serviceService;


    public AppointmentService(AppointmentRepository appointmentRepository, LocationRepository locationRepository, EmployeeRepository employeeRepository, ServiceRepository serviceRepository, LocationService locationService, EmployeeService employeeService, ServiceService serviceService) {
        this.appointmentRepository = appointmentRepository;
        this.locationRepository = locationRepository;
        this.employeeRepository = employeeRepository;
        this.serviceRepository = serviceRepository;
        this.locationService = locationService;
        this.employeeService = employeeService;
        this.serviceService = serviceService;
    }

    public List<AppointmentResponse> getAll(){
        return appointmentRepository
                .findAll()
                .stream()
                .map(AppointmentTransformer::toAppointmentResponse)
                .collect(Collectors.toList());
    }

    public Optional<AppointmentResponse> getById(int id){
        return appointmentRepository.
                findById(id).
                map(AppointmentTransformer::toAppointmentResponse);
    }

    public int createAppointment(AppointmentRequest appointmentRequest) throws ParseException {
        Appointment appointment = AppointmentTransformer.
                toAppointmentEntity(appointmentRequest);


//        EmployeeRequest employeeRequest = appointmentRequest.getEmployeeRequest();
//        Optional<Employee> employee;

        LocationRequest locationRequest = appointmentRequest.getLocationRequest();
        Optional<Location> location;

        ServiceRequest serviceRequest = appointmentRequest.getServiceRequest();
        Optional<spring.ms.com.data.entity.Service> service;

        if (serviceRepository.findByName(serviceRequest.getName()).isEmpty()) {
            int id = serviceService.createService(serviceRequest);
            service = serviceService.getById(id).map(ServiceTransformer::toServiceEntity);
        } else {
            service = serviceRepository.findByName(serviceRequest.getName());
        }

        if (locationRepository.findByName(locationRequest.getName()).isEmpty()) {
            int id = locationService.createLocation(locationRequest);
            location = locationService.getById(id).map(LocationTransformer::toLocationEntity);
        } else
        {
            location = locationRepository.findByName(locationRequest.getName());
        }

//        if (employeeRepository.findByName
//                (appointmentRequest.getEmployeeRequest().getFirstName(),
//                        appointmentRequest.getEmployeeRequest().getLastName()).isEmpty()) {
//            int id = employeeService.createEmployee(employeeRequest);
//            employee = employeeService.getById(id).map(EmployeeTransformer::toEmployeeEntity);
//        } else
//        {
//            employee = employeeRepository.findByName(employeeRequest.getFirstName(), employeeRequest.getLastName());
//        }

        appointment.setAppointmentLocation(location.get());
        appointment.setAppointmentEmployee(null);
        appointment.setService(service.get());

        return appointmentRepository.save(appointment).getId();
    }
    public void deleteAppointment(int id){
        appointmentRepository.deleteById(id);
    }
}
