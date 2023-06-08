package spring.ms.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ms.com.rest.request.AppointmentRequest;
import spring.ms.com.rest.response.*;
import spring.ms.com.services.AppointmentService;
import spring.ms.com.services.EmployeeService;
import spring.ms.com.services.LocationService;
import spring.ms.com.services.ServiceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ServiceService serviceService;

    @GetMapping("")
    public String getAll(Model model){
        model.addAttribute("title", "Appointments");
        model.addAttribute("appointments", appointmentService.getAll());
        model.addAttribute("appointment", "");

        return "appointments";
    }

    @GetMapping("/add")
    public String selectLocation(Model model){
        List<LocationResponse> locations = locationService.getAll();
        LocationResponse locationResponse = new LocationResponse();
        model.addAttribute("title", "Add Appointment");
        model.addAttribute("locationResponse", locationResponse);
        model.addAttribute("locations", locations);
        return "add_appointment_location";
    }

    @PostMapping("/add")
    public String selectLocation(@ModelAttribute("locationResponse") final LocationResponse locationResponse,
                                        RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("locationResponse", locationResponse);

        return "redirect:/appointments/add/details";
    }

    @GetMapping("/add/details")
    public String create(Model model, @ModelAttribute("locationResponse") final LocationResponse locationResponse){
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        List<ServiceResponse> services = serviceService.getAll();
        List<EmployeeResponse> allEmployees = employeeService.getAll();
        List<EmployeeResponse> employees = new ArrayList<>();
        String address = locationResponse.getAddress();

        for (EmployeeResponse e: allEmployees) {
            if (!e.getLocationResponse().getAddress().isBlank() && e.getLocationResponse().getAddress().equals(address) ){
                employees.add(e);
            }
        }
        model.addAttribute("title", "Add Appointment");
        model.addAttribute("appointmentRequest", appointmentRequest);
        model.addAttribute("services", services);
        model.addAttribute("employees", employees);
        return "add_appointment_details";
    }

    @PostMapping("/add/details")
    public String save(@ModelAttribute("appointmentRequest") AppointmentRequest appointmentRequest, RedirectAttributes redirectAttributes) {
        int id = appointmentService.createAppointment(appointmentRequest);
        Optional<AppointmentResponse> appointment = appointmentService.getById(id);
        if(appointment.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding an appointment!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Added successfully!");
        }
        return "redirect:/appointments/add";
    }

    @GetMapping("/edit/{id}")
    public String editLocation(@PathVariable int id, Model model){
        List<LocationResponse> locations = locationService.getAll();
        LocationResponse locationResponse = new LocationResponse();
        model.addAttribute("title", ("Update Appointment | ID: " + id));
        model.addAttribute("appointment", appointmentService.getById(id).get());
        model.addAttribute("locationResponse", appointmentService
                .getById(id)
                .get()
                .getEmployeeResponse()
                .getLocationResponse());
        model.addAttribute("locations", locations);
        return "edit_appointment_location";
    }

    @PostMapping("/edit/{id}")
    public String editLocation(@PathVariable int id,
                               @ModelAttribute("locationResponse") final LocationResponse locationResponse,
                               RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("locationResponse", locationResponse);

        return "redirect:/appointments/edit/details/{id}";
    }

    @GetMapping("/edit/details/{id}")
    public String edit(@PathVariable int id, Model model,
                       @ModelAttribute("locationResponse") final LocationResponse locationResponse){

        List<ServiceResponse> services = serviceService.getAll();
        List<EmployeeResponse> allEmployees = employeeService.getAll();
        List<EmployeeResponse> employees = new ArrayList<>();

        String address = locationResponse.getAddress();

        for (EmployeeResponse e: allEmployees) {
            if (!e.getLocationResponse().getAddress().isBlank() && e.getLocationResponse().getAddress().equals(address)) {
                employees.add(e);
            }
        }
        model.addAttribute("title", ("Update Appointment | ID: " + id));
        model.addAttribute("appointment", appointmentService.getById(id).get());
        model.addAttribute("services", services);
        model.addAttribute("employees", employees);
        return "edit_appointment_details";
    }

    @PostMapping("/edit/details/{id}")
    public String update(@PathVariable int id,
                                @ModelAttribute("appointment") AppointmentResponse appointmentResponse,
                                RedirectAttributes redirectAttributes, Model model){

        int newId = appointmentService.editAppointment(id, appointmentResponse);
        Optional<AppointmentResponse> appointment = appointmentService.getById(id);
        if(appointment.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating selected service!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Updated successfully!");
        }
        return "redirect:/appointments/edit/{id}";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model){
        model.addAttribute("title", ("Delete Location | ID:" + id));
        model.addAttribute("text", "appointment");
        model.addAttribute("type", "appointments");
        model.addAttribute("item", appointmentService.getById(id).get());
        return "delete_page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("appointment") String appointment, Model model){

        List<AppointmentResponse> appointments = appointmentService.getAll();
        List<AppointmentResponse> appointmentSearch = new ArrayList<>();

        for (AppointmentResponse appointmentResponse : appointments) {
            String client = appointmentResponse.getClientName().toLowerCase();
            String location = appointmentResponse.getServiceResponse().getName().toLowerCase();
            if(client.contains(appointment.toLowerCase()) || location.contains(appointment.toLowerCase())){
                appointmentSearch.add(appointmentResponse);
            }
        }

        model.addAttribute("appointment", "");
        model.addAttribute("appointmentSearch", appointmentSearch);

        return "search_appointments";
    }

    @PostMapping("/search")
    public String Search(Model model,
                         @ModelAttribute("appointment") String appointment,
                         RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("appointment", appointment);

        return "redirect:/appointments/search";
    }
}
