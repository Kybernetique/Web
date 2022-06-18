package ru.ulstu.is.sbapp.itcompany.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.itcompany.models.friend.FriendDTO;
import ru.ulstu.is.sbapp.itcompany.models.Friend;
import ru.ulstu.is.sbapp.itcompany.repositories.ProjectRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class FriendService {
    private final Logger log = LoggerFactory.getLogger(FriendService.class);
    private final ProjectRepository friendRepository;
    private final ValidatorUtil validatorUtil;

    public FriendService(ProjectRepository friendRepository, ValidatorUtil validatorUtil) {
        this.friendRepository = friendRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Friend addFriend(String firstName, String lastName, int age) {
        if(!StringUtils.hasText(firstName)) {
            throw new IllegalArgumentException("Project name is null or empty");
        }
        final Friend friend = new Friend(firstName, lastName, age);
        validatorUtil.validate(friend);
        return friendRepository.save(friend);
    }

    @Transactional
    public FriendDTO addFriend(FriendDTO friendDTO) {
        return new FriendDTO(addFriend(friendDTO.getFirstName(), friendDTO.getLastName(), friendDTO.getAge()));
    }

    @Transactional(readOnly = true)
    public Friend findFriend(Long id) {
        final Optional<Friend> friend = friendRepository.findById(id);
        return friend.orElseThrow(() -> new FriendNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Friend> findAllFriends() {
        return friendRepository.findAll();
    }

    @Transactional
    public Friend updateFriend(Long id, String name, String lastName, int age) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Project name is null or empty");
        }
        final Friend currentFriend = findFriend(id);
        currentFriend.setFirstName(name);
        currentFriend.setLastName(lastName);
        currentFriend.setAge(age);

        validatorUtil.validate(currentFriend);
        return friendRepository.save(currentFriend);
    }

    @Transactional
    public FriendDTO updateFriend(FriendDTO friendDTO) {
        return new FriendDTO(updateFriend(friendDTO.getId(), friendDTO.getFirstName(),
                friendDTO.getLastName(), friendDTO.getAge()));
    }

    @Transactional
    public Friend deleteFriend(Long id) {
        final Friend currentFriend = findFriend(id);
        friendRepository.delete(currentFriend);
        return currentFriend;
    }

/*    @Transactional
    public void deleteAllProjects() throws InProjectFoundDevelopersException {
        var projects = findAllProjects();
        for (var project : projects) {
            if (project.getDevelopers().size() > 0) {
                throw new InProjectFoundDevelopersException("В проекте " + project.getName() + " есть разработчики");
            }
        }
        projectRepository.deleteAll();
    }*/
}
