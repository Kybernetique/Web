package ru.ulstu.is.sbapp.itcompany.models.friend;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.itcompany.services.FriendService;

import javax.validation.Valid;

@Controller
@RequestMapping("/friend")
public class FriendMVC {
    private final FriendService friendService;

    public FriendMVC(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping
    public String getFriends(Model model) {
        model.addAttribute("friends",
                friendService.findAllFriends().stream()
                        .map(FriendDTO::new)
                        .toList());
        return "friend";
    }

    @GetMapping("/getMyFriends")
    public String getMyFriends(Model model) {
        model.addAttribute("myFriends",
                friendService.findAllFriends().stream()
                        .map(FriendDTO::new)
                        .toList());
        return "myfriends";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editFriend(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("friendDto",new FriendDTO());
        }
        else {
            model.addAttribute("friendId", id);
            model.addAttribute("friendDto", new FriendDTO(friendService.findFriend(id)));
        }
        return "friend-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveFriend(@PathVariable(required = false) Long id,
                             @ModelAttribute @Valid FriendDTO friendDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "friend-edit";
        }
        if (id == null || id <= 0) {
            friendService.addFriend(friendDTO.getFirstName(), friendDTO.getLastName(),
                    friendDTO.getAge());
        } else {
            friendService.updateFriend(id, friendDTO.getFirstName(), friendDTO.getLastName(),
                    friendDTO.getAge());
        }
        return "redirect:/friend";
    }


    @PostMapping("/delete/{id}")
    public String deleteFriend(@PathVariable Long id) {
        friendService.deleteFriend(id);
        return "redirect:/friend";
    }
}
