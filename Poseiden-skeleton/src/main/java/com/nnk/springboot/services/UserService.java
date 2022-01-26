package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    /**
     * Method using crudRepository, saving User to the database
     * Before saving a User, encode his password using BCrypt
     * @param user
     * @return a User in the database
     */

    public User addNewUserToDatabase(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        logger.info("Adding a new user to database");
        return userRepository.save(user);
    }

    /**
     * @return all Users from database
     */

    public List<User> findAll(){
        logger.info("Retrieving all Users from database");
        return userRepository.findAll();
    }

    /**
     * @param id
     * @return a User by his id
     */

    public User findById(Integer id){
        logger.info("Retrieving User with id number " + id + " from database");
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id " + id));
    }

    /**
     * Method using crudRepository, updating a User in the database
     * Before updating a User, encode his password using BCrypt
     * @param user
     */

    public void update(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        logger.info("Updating user number " + user.getId());
        userRepository.save(user);
    }

    /**
     * Method using crudRepository, deleting a User by his id in the database
     * @param id
     */

    public void deleteById(Integer id){
        logger.info("Deleting user number " + id);
        userRepository.deleteById(id);
    }
}
