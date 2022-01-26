package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.ITradeService;
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
public class TradeController {

    private final static Logger logger = LoggerFactory.getLogger(TradeController.class);
    private final ITradeService iTradeService;

    public TradeController (ITradeService iTradeService){
        this.iTradeService = iTradeService;
    }

    /**
     * Method calling a html page displaying all Trades entities
     * from a database
     * @param model
     * @return a String of a html page with all Trades
     */

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", iTradeService.findAll());
        return "trade/list";
    }

    /**
     * Method calling a html page with a form that can add a new Trade
     * to the database
     * @return a String of a html page
     */

    @GetMapping("/trade/add")
    public String addUser() {
        return "trade/add";
    }

    /**
     * Method validating or not a new Trade. If it's validated,
     * add it to the database
     * @param trade
     * @param result
     * @param model
     * @return a String of a html page with all Bids after adding
     * a new Trade to the database
     */

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.info("Trade incorrect");
            return "trade/add";
        }

        iTradeService.addNewTradeToDatabase(trade);

        logger.info("Adding a new Trade to database");
        model.addAttribute("trades", iTradeService.findAll());
        return "redirect:/trade/list";
    }

    /**
     * Given a Trade found by his ID, this method call a html form with
     * the Trade data already written, ready to be updated
     * @param id
     * @param model
     * @return a String of a html page
     */

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = iTradeService.findById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * Method validating or not a Trade. If it's validated, update it
     * to the database
     * @param id
     * @param trade
     * @param result
     * @param model
     * @return a String of a html page with all Trades after updating a Trade
     * in the database
     */

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.info("Trade not found or incorrect");
            return "trade/update";
        }

        trade.setTradeId(id);
        iTradeService.update(trade);

        logger.info("Updating trade number " + id);
        model.addAttribute("trades", iTradeService.findAll());
        return "redirect:/trade/list";
    }

    /**
     * Method used to delete Trades entities in the database
     * @param id
     * @param model
     * @return a String of a html page with all Trades after deleting a Bid
     * in the database
     */

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        iTradeService.deleteById(id);

        logger.info("Deleting trade number " + id);
        model.addAttribute("trades", iTradeService.findAll());
        return "redirect:/trade/list";
    }
}
