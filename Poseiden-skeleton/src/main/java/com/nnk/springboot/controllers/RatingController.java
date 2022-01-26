package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RatingController {

    private final static Logger logger = LoggerFactory.getLogger(RatingController.class);
    private final IRatingService iRatingService;

    public RatingController(IRatingService iRatingService){
        this.iRatingService = iRatingService;
    }

    /**
     * Method calling a html page displaying all Ratings entities
     * from a database
     * @param model
     * @return a String of a html page with all Ratings
     */

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", iRatingService.findAll());
        return "rating/list";
    }

    /**
     * Method calling a html page with a form that can add a new Rating
     * to the database
     * @return a String of a html page
     */

    @GetMapping("/rating/add")
    public String addRatingForm() {
        return "rating/add";
    }

    /**
     * Method validating or not a new Bid. If it's validated,
     * add it to the database
     * @param rating
     * @param result
     * @param model
     * @return a String of a html page with all Ratings after adding
     * a new Rating to the database
     */

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.info("Rating incorrect");
            return "rating/add";
        }

        iRatingService.addNewRatingToDatabase(rating);

        logger.info("Adding a new Rating to database");
        model.addAttribute("ratings", iRatingService.findAll());
        return "redirect:/rating/list";
    }

    /**
     * Given a Rating found by his ID, this method call a html form with
     * the Rating data already written, ready to be updated
     * @param id
     * @param model
     * @return a String of a html page
     */

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = iRatingService.findById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * Method validating or not a Rating. If it's validated, update it
     * to the database
     * @param id
     * @param rating
     * @param result
     * @param model
     * @return a String of a html page with all Ratings after updating a Bid
     *  in the database
     */

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.info("Rating not found or incorrect");
            return "rating/update";
        }

        rating.setId(id);
        iRatingService.update(rating);

        logger.info("Updating rating number " + id);
        model.addAttribute("ratings", iRatingService.findAll());
        return "redirect:/rating/list";
    }

    /**
     * Method used to delete Rating entities in the database
     * @param id
     * @param model
     * @return a String of a html page with all Ratings after deleting a Bid
     * in the database
     */

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        iRatingService.deleteById(id);

        logger.info("Deleting rating number " + id);
        model.addAttribute("ratings", iRatingService.findAll());
        return "redirect:/rating/list";
    }
}
