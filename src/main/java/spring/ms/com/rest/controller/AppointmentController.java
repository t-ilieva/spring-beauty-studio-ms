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

import java.text.ParseException;
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

        return "appointments";
    }

    @GetMapping("/add")
    public String create(Model model){
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        List<LocationResponse> locations = locationService.getAll();
        List<ServiceResponse> services = serviceService.getAll();
        List<EmployeeResponse> employees = employeeService.getAll();
        model.addAttribute("title", "Add Appointment");
        model.addAttribute("appointmentRequest", appointmentRequest);
        model.addAttribute("services", services);
        model.addAttribute("locations", locations);
        //model.addAttribute("employees", employees);
        return "add_appointment";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("appointmentRequest") AppointmentRequest appointmentRequest,
                       RedirectAttributes redirectAttributes) throws ParseException {
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
    public String editServices(@PathVariable int id, Model model){
        List<LocationResponse> locations = locationService.getAll();
        List<ServiceResponse> services = serviceService.getAll();
        List<EmployeeResponse> employees = employeeService.getAll();
        model.addAttribute("title", ("Update Appointment | ID: " + id));
        model.addAttribute("appointment", appointmentService.getById(id).get());
        model.addAttribute("services", services);
        model.addAttribute("locations", locations);
        //model.addAttribute("employees", employees);
        return "edit_appointment";
    }

    @PostMapping("/edit/{id}")
    public String updateService(@PathVariable int id,
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
    public String deleteLocation(@PathVariable int id, Model model){
        model.addAttribute("title", ("Delete Location | ID:" + id));
        model.addAttribute("text", "appointment");
        model.addAttribute("type", "appointments");
        model.addAttribute("item", appointmentService.getById(id).get());
        return "delete_page";
    }

    @PostMapping("/delete/{id}")
    public String deleteLocation(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}
