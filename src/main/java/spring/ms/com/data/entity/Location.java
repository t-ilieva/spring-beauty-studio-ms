package spring.ms.com.data.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private int id;
    private String name;
    private String address;
    private float rating;
    @Column(name = "date_opened")
    private Date dateOpened;

//    @OneToMany(mappedBy = "appointmentLocation")
//    private List<Appointment> appointments;

    @OneToMany(mappedBy = "employeeLocation")
    private List<Employee> employees;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

//    public List<Appointment> getAppointments() {
//        return appointments;
//    }
//
//    public void setAppointments(List<Appointment> appointments) {
//        this.appointments = appointments;
//    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
