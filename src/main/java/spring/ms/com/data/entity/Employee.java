package spring.ms.com.data.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_employed")
    private Date dateEmployed;
    @Column(name = "employee_number")
    private String employeeNumber;
    private float rating;

//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category employeeCategory;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location employeeLocation;

    @OneToMany(mappedBy = "appointmentEmployee")
    private List<Appointment> appointments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateEmployed() {
        return dateEmployed;
    }

    public void setDateEmployed(Date dateEmployed) {
        this.dateEmployed = dateEmployed;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

//    public Category getEmployeeCategory() {
//        return employeeCategory;
//    }
//
//    public void setEmployeeCategory(Category employeeCategory) {
//        this.employeeCategory = employeeCategory;
//    }

    public Location getEmployeeLocation() {
        return employeeLocation;
    }

    public void setEmployeeLocation(Location employeeLocation) {
        this.employeeLocation = employeeLocation;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
