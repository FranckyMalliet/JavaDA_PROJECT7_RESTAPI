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

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", iRatingService.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

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

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = iRatingService.findById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

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

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        iRatingService.deleteById(id);

        logger.info("Deleting rating number " + id);
        model.addAttribute("ratings", iRatingService.findAll());
        return "redirect:/rating/list";
    }
}
