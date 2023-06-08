package spring.ms.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ms.com.rest.request.LocationRequest;
import spring.ms.com.rest.response.LocationResponse;
import spring.ms.com.services.LocationService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("")
    public String getAll(Model model){
        model.addAttribute("title", "Locations");
        model.addAttribute("locations", locationService.getAll());
        model.addAttribute("location", "");

        return "locations";
    }

    @GetMapping("/add")
    public String create(Model model){
        LocationRequest locationRequest = new LocationRequest();
        model.addAttribute("title", "Add Location");
        model.addAttribute("locationRequest", locationRequest);

        return "add_location";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("locationRequest") LocationRequest locationRequest,
                       RedirectAttributes redirectAttributes) throws ParseException {
        int id = locationService.createLocation(locationRequest);
        Optional<LocationResponse> location = locationService.getById(id);
        if(location.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding! Check if location with the same address already exists!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Added successfully!");
        }
        return "redirect:/locations/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("title", ("Update Location | ID:" + id));
        model.addAttribute("location", locationService.getById(id).get());
        return "edit_location";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable int id,
                                 @ModelAttribute("location") LocationRequest locationRequest,
                                 RedirectAttributes redirectAttributes, Model model){

        int newId = locationService.editLocation(id, locationRequest);
        Optional<LocationResponse> location = locationService.getById(id);
        if(location.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating selected location!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Updated successfully!");
        }
        return "redirect:/locations/edit/{id}";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model){
        model.addAttribute("title", ("Delete Location | ID:" + id));
        model.addAttribute("text", "location");
        model.addAttribute("type", "locations");
        model.addAttribute("item", locationService.getById(id).get());
        return "delete_page";
    }

    @PostMapping("/delete/{id}")
    public String deleteLocation(@PathVariable int id) {
        locationService.deleteLocation(id);
        return "redirect:/locations";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("location") String location, Model model){

        List<LocationResponse> locations = locationService.getAll();
        List<LocationResponse> locationSearch = new ArrayList<>();

        for (LocationResponse locationResponse : locations) {
            String address = locationResponse.getAddress().toLowerCase();
            String name = locationResponse.getName().toLowerCase();
            if(address.contains(location.toLowerCase()) || name.contains(location.toLowerCase())){
                locationSearch.add(locationResponse);
            }
        }

        model.addAttribute("location", "");
        model.addAttribute("locationSearch", locationSearch);

        return "search_locations";
    }

    @PostMapping("/search")
    public String Search(Model model,
                         @ModelAttribute("location") String location,
                         RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("location", location);

        return "redirect:/locations/search";
    }
}
