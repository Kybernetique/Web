package ru.ulstu.is.sbapp.itcompany.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.itcompany.controllers.project.ProjectDTO;
import ru.ulstu.is.sbapp.itcompany.models.Project;
import ru.ulstu.is.sbapp.itcompany.repositories.ProjectRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @PersistenceContext
    private EntityManager em;
    private final Logger log = LoggerFactory.getLogger(CompanyService.class);
    private final ProjectRepository projectRepository;
    private final ValidatorUtil validatorUtil;

    public ProjectService(ProjectRepository projectRepository,
                          ValidatorUtil validatorUtil) {
        this.projectRepository = projectRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Project addProject(String projectName, String projectDifficulty) {
        if (!StringUtils.hasText(projectName) || !StringUtils.hasText(projectDifficulty)) {
            throw new IllegalArgumentException("Project data is empty");
        }
        final Project project = new Project(projectName, projectDifficulty);
        validatorUtil.validate(project);
        return projectRepository.save(project);
    }

    @Transactional
    public ProjectDTO addProject(ProjectDTO projectDTO) {
        return new ProjectDTO(addProject(projectDTO.getName(), projectDTO.getDifficulty()));
    }

    @Transactional(readOnly = true)
    public Project findProject(Long id) {
        final Optional<Project> project = projectRepository.findById(id);
        return project.orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Project findProjectByName(String name) {
        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            if (project.getName().equals(name))
                return project;
        }
        throw new EntityNotFoundException(String.format("Project with name [%s] is not found", name));
    }

    @Transactional(readOnly = true)
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    public Project updateProject(Long id, String projectName, String projectDifficulty) {
        if (!StringUtils.hasText(projectName) || !StringUtils.hasText(projectDifficulty)) {
            throw new IllegalArgumentException("Project name is null or empty");
        }
        final Project currentProject = findProject(id);
        currentProject.setName(projectName);
        validatorUtil.validate(currentProject);
        return projectRepository.save(currentProject);
    }

    @Transactional
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        return new ProjectDTO(updateProject(projectDTO.getProjectID(), projectDTO.getName(),
                projectDTO.getDifficulty()));
    }

    @Transactional
    public Project deleteProject(Long id) {
        final Project currentProject = findProject(id);
        projectRepository.delete(currentProject);
        return currentProject;
    }

    @Transactional
    public void deleteAllProjectsUnsafe() {
        log.warn("Unsafe usage!");
        List<Project> projects = findAllProjects();
        for (Project project : projects) {
            if (project.getDevelopers().size() > 0)
                project.removeAllDevelopers();
        }
        projectRepository.deleteAll();
    }

    @Transactional
    public void deleteAllProjects() throws InProjectFoundDevelopersException {
        List<Project> projects = findAllProjects();
        for (Project project : projects) {
            if (project.getDevelopers().size() > 0)
                throw new InProjectFoundDevelopersException("в проекте" + project.getName() + "есть разработчики");
        }
        projectRepository.deleteAll();
    }


}
