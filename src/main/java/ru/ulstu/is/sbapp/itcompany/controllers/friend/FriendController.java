package ru.ulstu.is.sbapp.itcompany.controllers.friend;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.WebConfiguration;
import ru.ulstu.is.sbapp.itcompany.services.FriendService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/friend")
public class FriendController {
    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("/{id}")
    public FriendDTO getFriend(@PathVariable Long id){
        return new FriendDTO(friendService.findFriend(id));
    }

    @GetMapping("/")
    public List<FriendDTO> getFriends(){
        return friendService.findAllFriends().stream()
                .map(FriendDTO::new)
                .toList();
    }

    @PostMapping("/")
    public FriendDTO createFriend(@RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastName,
                                  @RequestParam("age") int age) {
        return new FriendDTO(friendService.addFriend(firstName, lastName, age));
    }

    @PatchMapping("/{id}")
    public FriendDTO updateFriend(@PathVariable Long id, @RequestBody @Valid FriendDTO friendDTO){
        return new FriendDTO(friendService.updateFriend(id, friendDTO.getFirstName(),
                friendDTO.getLastName(), friendDTO.getAge()));
    }

    @DeleteMapping("/{id}")
    public FriendDTO deleteFriend(@PathVariable Long id) {
        return new FriendDTO(friendService.deleteFriend(id));
    }
}
