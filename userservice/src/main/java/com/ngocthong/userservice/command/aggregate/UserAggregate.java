package com.ngocthong.userservice.command.aggregate;

import com.ngocthong.commonservice.command.UpdateStatusUserCommand;
import com.ngocthong.userservice.command.command.CreateUserCommand;
import com.ngocthong.userservice.command.command.UpdateUserProfileCommand;
import com.ngocthong.userservice.command.event.CreateUserEvent;
import com.ngocthong.userservice.command.event.UserUpdateProfileEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class UserAggregate {
    @AggregateIdentifier
    private String userId;
    private String status;
    private String userName;
    private String password;
    private String role;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;


    public UserAggregate() {

    }

    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand) {
        CreateUserEvent createUserEvent = new CreateUserEvent();
        BeanUtils.copyProperties(createUserCommand, createUserEvent);
        AggregateLifecycle.apply(createUserEvent);
    }

    @EventSourcingHandler
    public void on(CreateUserEvent event) {
        this.userId = event.getUserId();
        this.status = event.getStatus();
        this.userName = event.getUsername();
        this.password = event.getPassword();
        this.role = event.getRole();
    }


    @CommandHandler
    public void handle(UpdateUserProfileCommand updateUserProfileCommand){
        UserUpdateProfileEvent userUpdateProfileEvent = new UserUpdateProfileEvent();
        BeanUtils.copyProperties(updateUserProfileCommand,userUpdateProfileEvent);
        AggregateLifecycle.apply(userUpdateProfileEvent);
    }

    @EventSourcingHandler
    public void on(UserUpdateProfileEvent event){
        this.userId = event.getId();
        this.fullName = event.getFullName();
        this.userName = event.getUserName();
        this.phoneNumber = event.getPhoneNumber();
        this.address = event.getAddress();
    }
}
