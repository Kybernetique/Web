package ru.ulstu.is.sbapp.itcompany.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.itcompany.controllers.developer.DeveloperDTO;
import ru.ulstu.is.sbapp.itcompany.models.Company;
import ru.ulstu.is.sbapp.itcompany.models.Developer;
import ru.ulstu.is.sbapp.itcompany.models.Job;
import ru.ulstu.is.sbapp.itcompany.models.Project;
import ru.ulstu.is.sbapp.itcompany.repositories.DeveloperRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class DeveloperService {
    @PersistenceContext
    private EntityManager em;

    private final ValidatorUtil validatorUtil;
    private final DeveloperRepository developerRepository;
    private final CompanyService companyService;
    private final JobService jobService;
    private final ProjectService projectService;

    public DeveloperService(DeveloperRepository developerRepository,
                            ValidatorUtil validatorUtil,
                            CompanyService companyService,
                            JobService jobService,
                            ProjectService projectService) {
        this.developerRepository = developerRepository;
        this.companyService = companyService;
        this.jobService = jobService;
        this.projectService = projectService;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Developer addDeveloper(String firstName, String lastName,
                                  Long companyID, Long jobID, Long projectID) {
        if (!StringUtils.hasText(firstName) || !StringUtils.hasText(lastName)
                || companyID == 0
                || jobID == 0
                || projectID == 0) {
            throw new IllegalArgumentException("Developer data is null or empty");
        }
        var company = companyService.findCompany(companyID);
        var job = jobService.findJob(jobID);
        var project = projectService.findProject(projectID);

        Developer developer = new Developer(firstName, lastName, job, company, project);
        company.setDeveloper(developer);
        project.setDeveloper(developer);
        job.setDeveloper(developer);
/*        developer.setProject(project);
        developer.setJob(job);
        developer.setCompany(company);*/

        validatorUtil.validate(developer);
        return developerRepository.save(developer);
    }

    @Transactional
    public DeveloperDTO addDeveloper(DeveloperDTO developerDTO) {
        return new DeveloperDTO(addDeveloper(developerDTO.getFirstName(), developerDTO.getLastName(),
                developerDTO.getCompanyID(), developerDTO.getJobID(),
                developerDTO.getProjectID()));
    }


    @Transactional(readOnly = true)
    public Developer findDeveloper(Long id) {
        final Optional<Developer> developer = developerRepository.findById(id);
        return developer.orElseThrow(() -> new DeveloperNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Developer> findAllDevelopers() {
        return developerRepository.findAll();
    }

    @Transactional
    public Developer updateDeveloper(Long id, String firstName, String lastName,
                                     Long companyID, Long jobID, Long projectID) {
        if (!StringUtils.hasText(firstName) || !StringUtils.hasText(lastName)) {
            throw new IllegalArgumentException("Developer name is null or empty");
        }
        final Developer currentDeveloper = findDeveloper(id);
        Company company = companyService.findCompany(companyID);
        Job job = jobService.findJob(jobID);
        Project project = projectService.findProject(projectID);

        currentDeveloper.setFirstName(firstName);
        currentDeveloper.setLastName(lastName);
        currentDeveloper.setCompany(company);
        currentDeveloper.setJob(job);
        currentDeveloper.setProject(project);


        if (currentDeveloper.getCompany().getId().equals(companyID)) {
            currentDeveloper.getCompany().updateDeveloper(id, currentDeveloper);
        }
        else {
            currentDeveloper.getCompany().removeDeveloper(id);
            currentDeveloper.setCompany(company);
        }

        if (currentDeveloper.getProject().getId().equals(projectID)) {
            currentDeveloper.getProject().updateDeveloper(id, currentDeveloper);
        }
        else {
            currentDeveloper.getProject().removeDeveloper(id);
            currentDeveloper.setProject(project);
        }

        validatorUtil.validate(currentDeveloper);
        return developerRepository.save(currentDeveloper);
    }

    @Transactional
    public DeveloperDTO updateDeveloper(DeveloperDTO developerDTO) {
        return new DeveloperDTO(updateDeveloper(developerDTO.getID(), developerDTO.getFirstName(),
                developerDTO.getLastName(), developerDTO.getCompanyID(), developerDTO.getJobID(),
                developerDTO.getProjectID()));
    }

    @Transactional
    public Developer deleteDeveloper(Long id) {
        final Developer currentDeveloper = findDeveloper(id);
        developerRepository.delete(currentDeveloper);
        return currentDeveloper;
    }

    @Transactional
    public void deleteAllDevelopers() {
        developerRepository.deleteAll();
    }
}
