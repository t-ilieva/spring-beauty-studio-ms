package spring.ms.com.rest.transformer;

import spring.ms.com.data.entity.Appointment;
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


        if(appointment.getAppointmentEmployee() != null) {
            EmployeeResponse employeeResponse = EmployeeTransformer.
                    toEmployeeResponse(appointment.getAppointmentEmployee());
            appointmentResponse.setEmployeeResponse(employeeResponse);
        } else {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setLocationResponse(new LocationResponse());
            employeeResponse.setFirstName("");
            employeeResponse.setLastName("");
            appointmentResponse.setEmployeeResponse(employeeResponse);
        }

        if(appointment.getService() != null) {
            ServiceResponse serviceResponse = ServiceTransformer.
                    toServiceResponse(appointment.getService());
            appointmentResponse.setServiceResponse(serviceResponse);
        } else {
            appointmentResponse.setServiceResponse(new ServiceResponse());
        }

        return appointmentResponse;
    }


    public static Appointment toAppointmentEntity(AppointmentRequest appointmentRequest){
        Date date = null;

        try {
            date = DATE_TIME_FORMATTER.parse(appointmentRequest.getDateAppointed());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Appointment appointment = new Appointment();

        appointment.setClientName(appointmentRequest.getClientName());
        appointment.setDateAppointed(date);
        appointment.setDescription(appointmentRequest.getDescription());

        return appointment;
    }

    public static Appointment toAppointmentEntity(AppointmentResponse appointmentResponse) {
        Date date = null;
        try {
            date = DATE_TIME_FORMATTER.parse(appointmentResponse.getDateAppointed());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Appointment appointment = new Appointment();
        appointment.setId(appointmentResponse.getId());
        appointment.setClientName(appointmentResponse.getClientName());
        appointment.setDateAppointed(date);
        appointment.setDescription(appointmentResponse.getDescription());

        return appointment;
    }
}
