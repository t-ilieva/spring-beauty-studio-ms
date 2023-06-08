package spring.ms.com.data.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "client_name")
    private String clientName;
    @Column(name = "date_appointed")
    private Date dateAppointed;
    private String description;


    @ManyToOne
    @JoinColumn(nullable = true, name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(nullable = true, name = "employee_id")
    private Employee appointmentEmployee;

//    @ManyToOne
//    @JoinColumn(name = "location_id")
//    private Location appointmentLocation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getDateAppointed() {
        return dateAppointed;
    }

    public void setDateAppointed(Date dateCreated) {
        this.dateAppointed = dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employee getAppointmentEmployee() {
        return appointmentEmployee;
    }

    public void setAppointmentEmployee(Employee appointmentEmployee) {
        this.appointmentEmployee = appointmentEmployee;
    }

//    public Location getAppointmentLocation() {
//        return appointmentLocation;
//    }
//
//    public void setAppointmentLocation(Location appointmentLocation) {
//        this.appointmentLocation = appointmentLocation;
//    }
}
