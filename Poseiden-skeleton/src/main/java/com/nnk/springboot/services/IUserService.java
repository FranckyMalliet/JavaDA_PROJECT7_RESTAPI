package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface IUserService {

    public User addNewUserToDatabase(User user);
    public List<User> findAll();
    public User findById(Integer id);
    public void update(User user);
    public void deleteById(Integer id);

}
