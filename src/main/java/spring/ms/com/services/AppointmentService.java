package spring.ms.com.services;

import spring.ms.com.data.entity.Appointment;
import spring.ms.com.data.entity.Employee;
import spring.ms.com.data.entity.Service;
import spring.ms.com.data.repository.AppointmentRepository;
import spring.ms.com.data.repository.EmployeeRepository;
import spring.ms.com.data.repository.LocationRepository;
import spring.ms.com.data.repository.ServiceRepository;
import spring.ms.com.rest.request.*;
import spring.ms.com.rest.response.*;
import spring.ms.com.rest.transformer.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final EmployeeService employeeService;
    private final ServiceService serviceService;


    public AppointmentService(AppointmentRepository appointmentRepository, LocationRepository locationRepository, EmployeeRepository employeeRepository, ServiceRepository serviceRepository, LocationService locationService, EmployeeService employeeService, ServiceService serviceService) {
        this.appointmentRepository = appointmentRepository;
        this.employeeRepository = employeeRepository;
        this.serviceRepository = serviceRepository;
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

    public int createAppointment(AppointmentRequest appointmentRequest) {
        Appointment appointment = AppointmentTransformer.
                toAppointmentEntity(appointmentRequest);

        ServiceRequest serviceRequest = appointmentRequest.getServiceRequest();
        Optional<Service> service;

        if (serviceRepository.findByName(serviceRequest.getName()).isEmpty()) {
            int id = serviceService.createService(serviceRequest);
            service = serviceService.getById(id).map(ServiceTransformer::toServiceEntity);
        } else
        {
            service = serviceRepository.findByName(serviceRequest.getName());
        }

        EmployeeRequest employeeRequest = appointmentRequest.getEmployeeRequest();
        Optional<Employee> employee;

        if (employeeRepository.findByEmployeeNumber(employeeRequest.getEmployeeNumber()).isEmpty()) {
            int id = employeeService.createEmployee(employeeRequest);
            employee = employeeService.getById(id).map(EmployeeTransformer::toEmployeeEntity);
        } else
        {
            employee = employeeRepository.findByEmployeeNumber(employeeRequest.getEmployeeNumber());
        }

        appointment.setAppointmentEmployee(employee.get());
        appointment.setService(service.get());

        return appointmentRepository.save(appointment).getId();
    }

    public int editAppointment(int id, AppointmentResponse appointmentResponse){
        Appointment appointment = AppointmentTransformer
                .toAppointmentEntity(appointmentResponse);

        ServiceResponse serviceResponse = appointmentResponse.getServiceResponse();
        Service service = serviceRepository.findByName(serviceResponse.getName()).get();

        EmployeeResponse employeeResponse = appointmentResponse.getEmployeeResponse();
        Employee employee = employeeRepository.findByEmployeeNumber(employeeResponse.getEmployeeNumber()).get();

        appointment.setService(service);
        appointment.setAppointmentEmployee(employee);
        appointment.setId(id);

        return appointmentRepository.save(appointment).getId();
    }
    public void deleteAppointment(int id){
        appointmentRepository.deleteById(id);
    }
}
