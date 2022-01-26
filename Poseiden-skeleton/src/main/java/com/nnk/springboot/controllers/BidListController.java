package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class BidListController {

    private final static Logger logger = LoggerFactory.getLogger(BidListController.class);
    private final IBidListService iBidListService;

    public BidListController(IBidListService iBidListService) {
        this.iBidListService = iBidListService;
    }

    /**
     * Method calling a html page displaying all Bids entities
     * from a database
     * @param model
     * @return a String of a html page with all Bids
     */

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bids", iBidListService.findAll());
        return "bidList/list";
    }

    /**
     * Method calling a html page with a form that can add a new Bid
     * to the database
     * @return a String of a html page
     */

    @GetMapping("/bidList/add")
    public String addBidForm() {
        return "bidList/add";
    }

    /**
     * Method validating or not a new Bid. If it's validated,
     * add it to the database
     * @param bid
     * @param result
     * @param model
     * @return a String of a html page with all Bids after adding
     * a new Bid to the database
     */

    @PostMapping(value="/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.info("Bid incorrect");
            return "bidList/add";
        }

        iBidListService.addNewBidToDatabase(bid);

        logger.info("Adding a new Bid to database ");
        model.addAttribute("bids", iBidListService.findAll());
        return "redirect:/bidList/list";
    }

    /**
     * Given a Bid found by his ID, this method call a html form with
     * the Bid data already written, ready to be updated
     * @param id
     * @param model
     * @return a String of a html page
     */

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bid = iBidListService.findById(id);
        model.addAttribute("bid", bid);
        return "bidList/update";
    }

    /**
     * Method validating or not a Bid. If it's validated, update it
     * to the database
     * @param id
     * @param bid
     * @param result
     * @param model
     * @return a String of a html page with all Bids after updating a Bid
     * in the database
     */

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bid, BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.info("Bid not found or incorrect");
            return "bidList/update";
        }

        bid.setBidListId(id);
        iBidListService.update(bid);

        logger.info("Updating Bid number " + id);
        model.addAttribute("bids", iBidListService.findAll());
        return "redirect:/bidList/list";
    }

    /**
     * Method used to delete Bids entities in the database
     * @param id
     * @param model
     * @return a String of a html page with all Bids after deleting a Bid
     * in the database
     */

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        iBidListService.deleteById(id);

        logger.info("Deleting Bid number " + id);
        model.addAttribute("bids", iBidListService.findAll());
        return "redirect:/bidList/list";
    }
}
