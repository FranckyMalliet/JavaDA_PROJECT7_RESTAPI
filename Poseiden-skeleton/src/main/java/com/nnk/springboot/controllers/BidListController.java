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

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bids", iBidListService.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm() {
        return "bidList/add";
    }

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

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bid = iBidListService.findById(id);
        model.addAttribute("bid", bid);
        return "bidList/update";
    }

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

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        iBidListService.deleteById(id);

        logger.info("Deleting Bid number " + id);
        model.addAttribute("bids", iBidListService.findAll());
        return "redirect:/bidList/list";
    }
}
