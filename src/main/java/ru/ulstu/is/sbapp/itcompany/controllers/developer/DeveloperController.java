package ru.ulstu.is.sbapp.itcompany.controllers.developer;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.WebConfiguration;
import ru.ulstu.is.sbapp.itcompany.services.DeveloperService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/developer")
public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("/{id}")
    public DeveloperDTO getDeveloper(@PathVariable Long id) {
        return new DeveloperDTO(developerService.findDeveloper(id));
    }

    @GetMapping("/")
    public List<DeveloperDTO> getDevelopers() {
        return developerService.findAllDevelopers().stream().map(DeveloperDTO::new).toList();
    }

    @PostMapping
    public DeveloperDTO createDeveloper(@RequestBody @Valid DeveloperDTO developerDTO) {
        return new DeveloperDTO(developerService.addDeveloper(developerDTO.getFirstName(), developerDTO.getLastName(), developerDTO.getCompanyID(), developerDTO.getJobID(), developerDTO.getProjectID()));
    }


    @PutMapping("/{id}")
    public DeveloperDTO updateDeveloper(@PathVariable Long id, @RequestBody @Valid DeveloperDTO developerDTO) {
        return new DeveloperDTO(developerService.updateDeveloper(id, developerDTO.getFirstName(), developerDTO.getLastName(), developerDTO.getCompanyID(), developerDTO.getJobID(), developerDTO.getProjectID()));
    }

    @DeleteMapping("/{id}")
    public DeveloperDTO deleteDeveloper(@PathVariable Long id) {
        return new DeveloperDTO(developerService.deleteDeveloper(id));
    }
}