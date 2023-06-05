package spring.ms.com.rest.request;

public class AppointmentRequest {

    private String clientName;
    private String dateAppointed;
    private String description;
    private float price;

    private ServiceRequest serviceRequest;
    private EmployeeRequest employeeRequest;
    private LocationRequest locationRequest;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDateAppointed() {
        return dateAppointed;
    }

    public void setDateAppointed(String dateAppointed) {
        this.dateAppointed = dateAppointed;
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

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public EmployeeRequest getEmployeeRequest() {
        return employeeRequest;
    }

    public void setEmployeeRequest(EmployeeRequest employeeRequest) {
        this.employeeRequest = employeeRequest;
    }

    public LocationRequest getLocationRequest() {
        return locationRequest;
    }

    public void setLocationRequest(LocationRequest locationRequest) {
        this.locationRequest = locationRequest;
    }
}
