package spring.ms.com.data.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String clientName;

    @Column(name = "date_created")
    private Date dateAppointed;
    private String description;
    private float price;


    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee appointmentEmployee;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location appointmentLocation;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public Location getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(Location appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }
}
