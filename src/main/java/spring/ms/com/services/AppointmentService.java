package spring.ms.com.services;

import org.springframework.stereotype.Service;
import spring.ms.com.data.entity.Appointment;
import spring.ms.com.data.repository.AppointmentRepository;
import spring.ms.com.rest.request.AppointmentRequest;
import spring.ms.com.rest.response.AppointmentResponse;
import spring.ms.com.rest.transformer.AppointmentTransformer;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
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
        return appointmentRepository.save(appointment).getId();
    }
    public void deleteAppointment(int id){
        appointmentRepository.deleteById(id);
    }
}
