package ru.ulstu.is.sbapp.itcompany.controllers.project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.itcompany.services.ProjectService;

import javax.validation.Valid;

@Controller
@RequestMapping("/project")
public class ProjectMVC {
    private final ProjectService projectService;

    public ProjectMVC(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String getProjects(Model model) {
        model.addAttribute("projects",
                projectService.findAllProjects()
                        .stream()
                        .map(ProjectDTO::new).toList());
        return "project";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editProject(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("projectDTO", new ProjectDTO());
        } else {
            model.addAttribute("projectID", id);
            model.addAttribute("projectDTO", new ProjectDTO(projectService.findProject(id)));
            model.addAttribute("developers", projectService.findProject(id).getDevelopers());
        }
        return "project-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveProject(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid ProjectDTO projectDTO,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "project-edit";
        }
        if (id == null || id <= 0) {
            projectService.addProject(projectDTO.getName(), projectDTO.getDifficulty());
        } else {
            projectService.updateProject(id, projectDTO.getName(), projectDTO.getDifficulty());
        }
        return "redirect:/project";
    }

    @PostMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/project";
    }

}
