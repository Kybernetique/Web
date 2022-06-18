package ru.ulstu.is.sbapp.itcompany.controllers.developer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.itcompany.controllers.company.CompanyDTO;
import ru.ulstu.is.sbapp.itcompany.controllers.job.JobDTO;
import ru.ulstu.is.sbapp.itcompany.controllers.friend.FriendDTO;
import ru.ulstu.is.sbapp.itcompany.services.CompanyService;
import ru.ulstu.is.sbapp.itcompany.services.DeveloperService;
import ru.ulstu.is.sbapp.itcompany.services.JobService;
import ru.ulstu.is.sbapp.itcompany.services.FriendService;

import javax.validation.Valid;

@Controller
@RequestMapping("/developer")
public class DeveloperMVC {
    private final DeveloperService developerService;
    private final CompanyService companyService;
    private final FriendService friendService;
    private final JobService jobService;

    public DeveloperMVC(DeveloperService developerService, CompanyService companyService, FriendService friendService, JobService jobService) {
        this.developerService = developerService;
        this.companyService = companyService;
        this.friendService = friendService;
        this.jobService = jobService;
    }

    @GetMapping
    public String getDevelopers(Model model) {
        model.addAttribute("developers", developerService.findAllDevelopers().stream().map(DeveloperDTO::new).toList());
        return "developer";
    }

/*    @GetMapping("/getDevelopers")
    public String getDevelopersQuery(@RequestParam(name = "projectName", defaultValue = "Компилятор языка C") String projectName,
                                     @RequestParam(name = "jobName", defaultValue = "Backend") String jobName,
                                     Model model) {
        model.addAttribute("projects", projectService.findAllProjects().stream().map(ProjectDTO::new).toList());
        model.addAttribute("jobs", jobService.findAllJobs().stream().map(JobDTO::new).toList());
        model.addAttribute("projectName", projectName);
        model.addAttribute("jobName", jobName);
*//*
        model.addAttribute("developerDto", developerService.findByNameContaining(projectName, jobName).stream().map(DeveloperDTO::new).toList());
*//*
        return "query";
    }*/


    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editDeveloper(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("developerDto", new DeveloperDTO());
        } else {
            model.addAttribute("developerId", id);
            model.addAttribute("developerDto", new DeveloperDTO(developerService.findDeveloper(id)));
        }
        model.addAttribute("companies", companyService.findAllCompanies().stream().map(CompanyDTO::new).toList());
        model.addAttribute("jobs", jobService.findAllJobs().stream().map(JobDTO::new).toList());
        model.addAttribute("projects", friendService.findAllFriends().stream().map(FriendDTO::new).toList());
        return "developer-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveDeveloper(@PathVariable(required = false) Long id, @ModelAttribute @Valid DeveloperDTO developerDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "developer-edit";
        }
        if (id == null || id <= 0) {
            developerService.addDeveloper(developerDto.getFirstName(), developerDto.getLastName(), developerDto.getCompany(), developerDto.getJob(), developerDto.getFriend());
        } else {
            developerService.updateDeveloper(id, developerDto.getFirstName(), developerDto.getLastName(), developerDto.getCompany(), developerDto.getJob(), developerDto.getFriend());
        }
        return "redirect:/developer";
    }

    @PostMapping("/delete/{id}")
    public String deleteDeveloper(@PathVariable Long id) {
        developerService.deleteDeveloper(id);
        return "redirect:/developer";
    }

}
