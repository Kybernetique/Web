package ru.ulstu.is.sbapp.itcompany.controllers.developer;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.itcompany.controllers.company.CompanyDTO;
import ru.ulstu.is.sbapp.itcompany.controllers.project.ProjectDTO;
import ru.ulstu.is.sbapp.itcompany.services.CompanyService;
import ru.ulstu.is.sbapp.itcompany.services.DeveloperService;
import ru.ulstu.is.sbapp.itcompany.services.ProjectService;

import javax.validation.Valid;

@Controller
@RequestMapping("/developer")
public class DeveloperMVC {
    private final DeveloperService developerService;
    private final CompanyService companyService;
    private final ProjectService projectService;

    public DeveloperMVC(DeveloperService developerService, CompanyService companyService, ProjectService projectService) {
        this.developerService = developerService;
        this.companyService = companyService;
        this.projectService = projectService;
    }

    @GetMapping
    public String getDevelopers(Model model) {
        model.addAttribute("developers", developerService.findAllDevelopers().stream().map(DeveloperDTO::new).toList());
        return "developer";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editDeveloper(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("developerDTO", new DeveloperDTO());
        } else {
            model.addAttribute("developerID", id);
            model.addAttribute("developerDTO", new DeveloperDTO(developerService.findDeveloper(id)));
        }
        model.addAttribute("companies", companyService.findAllCompanies().stream().map(CompanyDTO::new).toList());
        model.addAttribute("projects", projectService.findAllProjects().stream().map(ProjectDTO::new).toList());
        return "developer-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveDeveloper(@PathVariable(required = false) Long id,
                                @ModelAttribute @Valid DeveloperDTO developerDTO,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "developer-edit";
        }
        if (id == null || id <= 0) {
            developerService.addDeveloper(developerDTO.getFirstName(), developerDTO.getLastName(), developerDTO.getCompanyID(), developerDTO.getJobID(), developerDTO.getProjectID());
        } else {
            developerService.updateDeveloper(id, developerDTO.getFirstName(), developerDTO.getLastName(), developerDTO.getCompanyID(), developerDTO.getJobID(), developerDTO.getProjectID());
        }
        return "redirect:/developer";
    }

    @PostMapping("/delete/{id}")
    public String deleteDeveloper(@PathVariable Long id) {
        developerService.deleteDeveloper(id);
        return "redirect:/developer";
    }

}
