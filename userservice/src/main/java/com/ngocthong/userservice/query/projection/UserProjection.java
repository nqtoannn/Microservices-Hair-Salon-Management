package com.ngocthong.userservice.query.projection;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ngocthong.commonservice.model.UserResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailUser;
import com.ngocthong.userservice.data.User;
import com.ngocthong.userservice.data.UserRepository;
import com.ngocthong.userservice.query.queries.GetAllCustomerQuery;
import com.ngocthong.userservice.query.queries.GetAllEmployeesQuery;
import com.ngocthong.userservice.query.queries.GetUserByIdQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {
    @Autowired
    private UserRepository userRepository;

    @QueryHandler
    public UserResponseCommonModel handle(GetDetailUser getDetailUser) {
        String id = getDetailUser.getUserId();
        Optional<User> user = userRepository.findById(id);
        User getUser = new User();
        if (user.isPresent()) {
            getUser = user.get();
        }
        UserResponseCommonModel u = new UserResponseCommonModel();
        u.setId(getUser.getId());
        u.setEmail(getUser.getEmail());
        u.setAddress(getUser.getAddress());
        u.setStatus(getUser.getStatus());
        u.setUserName(getUser.getUsername());
        u.setPhoneNumber(getUser.getPhoneNumber());
        u.setFullName(getUser.getFullName());
        return u;
    }

    @QueryHandler
    public UserResponseCommonModel handle(GetUserByIdQuery getUserByIdQuery) {
        String id = getUserByIdQuery.getUserId();
        Optional<User> user = userRepository.findById(id);
        User getUser = new User();
        if (user.isPresent()) {
            getUser = user.get();
        }
        UserResponseCommonModel u = new UserResponseCommonModel();
        u.setId(getUser.getId());
        u.setEmail(getUser.getEmail());
        u.setAddress(getUser.getAddress());
        u.setStatus(getUser.getStatus());
        u.setUserName(getUser.getUsername());
        u.setPhoneNumber(getUser.getPhoneNumber());
        u.setFullName(getUser.getFullName());
        return u;
    }

    @QueryHandler
    public List<UserResponseCommonModel> handle (GetAllEmployeesQuery getAllEmployees){
        List<User> list = userRepository.findAllEmployees();
        List<UserResponseCommonModel> listEmployees = new ArrayList<>();
        list.forEach(getUser -> {
            UserResponseCommonModel u = new UserResponseCommonModel();
            u.setId(getUser.getId());
            u.setEmail(getUser.getEmail());
            u.setAddress(getUser.getAddress());
            u.setStatus(getUser.getStatus());
            u.setUserName(getUser.getUsername());
            u.setPhoneNumber(getUser.getPhoneNumber());
            u.setFullName(getUser.getFullName());
            listEmployees.add(u);
        });
        return listEmployees;
    }

    @QueryHandler
    public List<UserResponseCommonModel> handle (GetAllCustomerQuery getAllCustomerQuery){
        List<User> list = userRepository.findAllCustomer();
        List<UserResponseCommonModel> listCustomer = new ArrayList<>();
        list.forEach(getUser -> {
            UserResponseCommonModel u = new UserResponseCommonModel();
            u.setId(getUser.getId());
            u.setEmail(getUser.getEmail());
            u.setAddress(getUser.getAddress());
            u.setStatus(getUser.getStatus());
            u.setUserName(getUser.getUsername());
            u.setPhoneNumber(getUser.getPhoneNumber());
            u.setFullName(getUser.getFullName());
            listCustomer.add(u);
        });
        return listCustomer;
    }

}
