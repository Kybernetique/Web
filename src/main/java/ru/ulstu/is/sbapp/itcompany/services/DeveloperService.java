package ru.ulstu.is.sbapp.itcompany.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.itcompany.models.developer.DeveloperDTO;
import ru.ulstu.is.sbapp.itcompany.models.Developer;
import ru.ulstu.is.sbapp.itcompany.repositories.DeveloperRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;
    private final ValidatorUtil validatorUtil;
    private final FriendService friendService;

    public DeveloperService(DeveloperRepository developerRepository, ValidatorUtil validatorUtil, CompanyService companyService,
                      JobService jobService, FriendService friendService) {
        this.developerRepository = developerRepository;
        this.validatorUtil = validatorUtil;
        this.friendService = friendService;
    }

    @Transactional
    public Developer addDeveloper(String firstName, String lastName, long friendId) {
        if(!StringUtils.hasText(firstName) || friendId == 0) {
            throw new IllegalArgumentException("Developer's data is null or empty");
        }
        var friend = friendService.findFriend(friendId);
        var developer = new Developer(firstName, lastName);
        validatorUtil.validate(developer);
        return developerRepository.save(developer);
    }

    @Transactional
    public DeveloperDTO addDeveloper(DeveloperDTO developerDTO) {
        return new DeveloperDTO(addDeveloper(developerDTO.getFirstName(), developerDTO.getLastName(),
                developerDTO.getFriend()));
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
    public Developer updateDeveloper(Long id, String firstName, String lastName, Long friendId) {
        if(!StringUtils.hasText(firstName)) {
            throw new IllegalArgumentException("Developer's data is null or empty");
        }
        final Developer currentDeveloper = findDeveloper(id);
        var friend = friendService.findFriend(friendId);
        currentDeveloper.setFirstName(firstName);
        currentDeveloper.setLastName(lastName);

        if (currentDeveloper.getFriend().getId().equals(friendId)) {
            currentDeveloper.getFriend().updateDeveloper(id, currentDeveloper);
        }
        else {
            currentDeveloper.getFriend().removeDeveloper(id);
            currentDeveloper.setFriend(friend);
        }
        validatorUtil.validate(currentDeveloper);
        return developerRepository.save(currentDeveloper);
    }

    @Transactional
    public DeveloperDTO updateDeveloper(DeveloperDTO developerDto) {
        return new DeveloperDTO(updateDeveloper(developerDto.getId(), developerDto.getFirstName(),
                developerDto.getLastName(), developerDto.getFriend()));
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

/*    public List<DeveloperDTO> findByNameContaining(String projectName, String jobName) {
        return developerRepository.findByNameContaining(projectName, jobName)
                .stream()
                .map(DeveloperDTO::new)
                .collect(Collectors.toList());
    }*/
}
