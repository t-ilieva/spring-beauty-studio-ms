package spring.ms.com.rest.request;

public class EmployeeRequest {

    private String firstName;
    private String lastName;
    private String dateEmployed;
    private String employeeNumber;
    private float rating;

//    private CategoryRequest categoryRequest;
    private LocationRequest locationRequest;

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

    public String getDateEmployed() {
        return dateEmployed;
    }

    public void setDateEmployed(String dateEmployed) {
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

//    public CategoryRequest getCategoryRequest() {
//        return categoryRequest;
//    }
//
//    public void setCategoryRequest(CategoryRequest categoryRequest) {
//        this.categoryRequest = categoryRequest;
//    }

    public LocationRequest getLocationRequest() {
        return locationRequest;
    }

    public void setLocationRequest(LocationRequest locationRequest) {
        this.locationRequest = locationRequest;
    }
}
