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

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", iTradeService.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

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

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = iTradeService.findById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

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

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        iTradeService.deleteById(id);

        logger.info("Deleting trade number " + id);
        model.addAttribute("trades", iTradeService.findAll());
        return "redirect:/trade/list";
    }
}
