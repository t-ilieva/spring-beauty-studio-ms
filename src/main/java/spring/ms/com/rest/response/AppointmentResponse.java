package spring.ms.com.rest.response;

public class AppointmentResponse {

    private int id;
    private String clientName;
    private String dateAppointed;
    private String description;

    private ServiceResponse serviceResponse;
    private EmployeeResponse employeeResponse;
//    private LocationResponse locationResponse;

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

    public ServiceResponse getServiceResponse() {
        return serviceResponse;
    }

    public void setServiceResponse(ServiceResponse serviceResponse) {
        this.serviceResponse = serviceResponse;
    }

    public EmployeeResponse getEmployeeResponse() {
        return employeeResponse;
    }

    public void setEmployeeResponse(EmployeeResponse employeeResponse) {
        this.employeeResponse = employeeResponse;
    }

//    public LocationResponse getLocationResponse() {
//        return locationResponse;
//    }
//
//    public void setLocationResponse(LocationResponse locationResponse) {
//        this.locationResponse = locationResponse;
//    }
}
