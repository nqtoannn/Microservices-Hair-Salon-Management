package com.ngocthong.userservice.command.event;

import com.ngocthong.userservice.data.User;
import com.ngocthong.userservice.data.UserRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserEventHandlers {
    @Autowired
    private UserRepository userRepository;


    @EventHandler
    public void on(CreateUserEvent createUserEvent) {
        User user = new User();
        BeanUtils.copyProperties(createUserEvent, user);
        userRepository.save(user);
    }

    @EventHandler
    public void on(UserUpdateProfileEvent userUpdateProfileEvent){
        Optional<User> user = userRepository.findById(userUpdateProfileEvent.getId());
        User updatedUser = new User();
        if (user.isPresent()) {
            updatedUser = user.get();
            updatedUser.setUserName(userUpdateProfileEvent.getUserName());
            updatedUser.setFullName(userUpdateProfileEvent.getFullName());
            updatedUser.setAddress(userUpdateProfileEvent.getAddress());
            updatedUser.setPhoneNumber(userUpdateProfileEvent.getPhoneNumber());
        }
        userRepository.save(updatedUser);
    }

}
