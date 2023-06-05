package spring.ms.com.rest.transformer;

import spring.ms.com.data.entity.Appointment;
import spring.ms.com.data.entity.Employee;
import spring.ms.com.rest.request.AppointmentRequest;
import spring.ms.com.rest.response.AppointmentResponse;
import spring.ms.com.rest.response.EmployeeResponse;
import spring.ms.com.rest.response.LocationResponse;
import spring.ms.com.rest.response.ServiceResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentTransformer {

    private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public static AppointmentResponse toAppointmentResponse(Appointment appointment){
        AppointmentResponse appointmentResponse = new AppointmentResponse();
        appointmentResponse.setId(appointment.getId());
        appointmentResponse.setClientName(appointment.getClientName());
        appointmentResponse.setDateAppointed(DATE_TIME_FORMATTER.format(appointment.getDateAppointed()));
        appointmentResponse.setDescription(appointment.getDescription());
        appointmentResponse.setPrice(appointment.getPrice());

        EmployeeResponse employeeResponse = EmployeeTransformer.
                toEmployeeResponse(appointment.getAppointmentEmployee());
        LocationResponse locationResponse = LocationTransformer.
                toLocationResponse(appointment.getAppointmentLocation());
        ServiceResponse serviceResponse = ServiceTransformer.
                toServiceResponse(appointment.getService());

        appointmentResponse.setEmployeeResponse(employeeResponse);
        appointmentResponse.setLocationResponse(locationResponse);
        appointmentResponse.setServiceResponse(serviceResponse);

        return appointmentResponse;
    }


    public static Appointment toAppointmentEntity(AppointmentRequest appointmentRequest) throws ParseException {
        Date date = DATE_TIME_FORMATTER.parse(appointmentRequest.getDateAppointed());
        Appointment appointment = new Appointment();
        appointment.setClientName(appointmentRequest.getClientName());
        appointment.setDateAppointed(date);
        appointment.setDescription(appointmentRequest.getDescription());
        appointment.setPrice(appointmentRequest.getPrice());

        return appointment;
    }

    public static Appointment toAppointmentEntity(AppointmentResponse appointmentResponse) throws ParseException {
        Date date = DATE_TIME_FORMATTER.parse(appointmentResponse.getDateAppointed());
        Appointment appointment = new Appointment();
        appointment.setId(appointmentResponse.getId());
        appointment.setClientName(appointmentResponse.getClientName());
        appointment.setDateAppointed(date);
        appointment.setDescription(appointmentResponse.getDescription());
        appointment.setPrice(appointmentResponse.getPrice());

        return appointment;
    }
}
