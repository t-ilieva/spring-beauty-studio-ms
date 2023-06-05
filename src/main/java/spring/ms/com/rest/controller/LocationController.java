package spring.ms.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ms.com.rest.request.CategoryRequest;
import spring.ms.com.rest.request.LocationRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.rest.response.LocationResponse;
import spring.ms.com.services.LocationService;

import java.text.ParseException;
import java.util.Optional;

@Controller
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/locations")
    public String getAll(Model model){
        model.addAttribute("title", "Locations");
        model.addAttribute("locations", locationService.getAll());

        return "locations";
    }

    @GetMapping("/addLocation")
    public String create(Model model){
        LocationRequest locationRequest = new LocationRequest();
        model.addAttribute("title", "Add Location");
        model.addAttribute("locationRequest", locationRequest);

        return "add_location";
    }

    @PostMapping("/addLocation")
    public String save(@ModelAttribute("locationRequest") LocationRequest locationRequest,
                       RedirectAttributes redirectAttributes) throws ParseException {
        int id = locationService.createLocation(locationRequest);
        Optional<LocationResponse> location = locationService.getById(id);
        if(location.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding a location!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Added successfully!");
        }
        return "redirect:/addLocation";
    }
}
