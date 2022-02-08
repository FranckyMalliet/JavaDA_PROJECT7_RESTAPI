package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    private final IUserService iUserService;

    public UserController(IUserService iUserService){
        this.iUserService = iUserService;
    }

    @Autowired
    private UserRepository userRepository;

    /**
     * Method calling a html page displaying all Users entities
     * from a database
     * @param model
     * @return a String of a html page with all Users
     */

    @RequestMapping("/user/list")
    @RolesAllowed("ADMIN")
    public String home(Model model)
    {
        model.addAttribute("users", iUserService.findAll());
        return "user/list";
    }

    /**
     * Method calling a html page with a form that can add a new User
     * to the database
     * @return a String of a html page
     */

    @GetMapping("/user/add")
    @RolesAllowed("ADMIN")
    public String addUser(User user, Model model) {
        model.addAttribute("user", user);
        return "user/add";
    }

    /**
     * Method validating or not a new User. If it's validated,
     * add it to the database
     * @param user
     * @param result
     * @param model
     * @return a String of a html page with all Bids after adding
     * a new User to the database
     */

    @PostMapping("/user/validate")
    @RolesAllowed("ADMIN")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.info("User incorrect");
            return "user/add";
        }

        iUserService.addNewUserToDatabase(user);

        logger.info("Adding a new User to database");
        model.addAttribute("users", iUserService.findAll());
        return "redirect:/user/list";
    }

    /**
     * Given a User found by his ID, this method call a html form with
     * the User data already written, ready to be updated
     * @param id
     * @param model
     * @return a String of a html page
     */

    @GetMapping("/user/update/{id}")
    @RolesAllowed("ADMIN")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = iUserService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Method validating or not a User. If it's validated, update it
     * to the database
     * @param id
     * @param user
     * @param result
     * @param model
     * @return a String of a html page with all Users after updating a User
     * in the database
     */

    @PostMapping("/user/update/{id}")
    @RolesAllowed("ADMIN")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("User not found or incorrect");
            return "user/update";
        }

        user.setId(id);
        iUserService.update(user);
        model.addAttribute("users", iUserService.findAll());
        return "redirect:/user/list";
    }

    /**
     * Method used to delete Users entities in the database
     * @param id
     * @param model
     * @return a String of a html page with all Users after deleting a User
     * in the database
     */

    @GetMapping("/user/delete/{id}")
    @RolesAllowed("ADMIN")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        iUserService.deleteById(id);

        logger.info("Deleting User number " + id);
        model.addAttribute("users", iUserService.findAll());
        return "redirect:/user/list";
    }
}
