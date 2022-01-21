package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.ICurvePoint;
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
public class CurveController {

    private final static Logger logger = LoggerFactory.getLogger(CurveController.class);
    private ICurvePoint iCurvePoint;

    public CurveController(ICurvePoint iCurvePoint){
        this.iCurvePoint=iCurvePoint;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", iCurvePoint.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm() {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.info("CurvePoint incorrect");
            return "curvePoint/add";
        }

        logger.info("Adding a new CurvePoint to database ");
        iCurvePoint.addNewCurvePointToDatabase(curvePoint);
        model.addAttribute("curvePoints", iCurvePoint.findAll());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = iCurvePoint.findById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.info("curvePoint not found or incorrect");
            return "curvePoint/update";
        }

        curvePoint.setCurveId(id);
        iCurvePoint.addNewCurvePointToDatabase(curvePoint);

        logger.info("Updating CurvePoint number " + id);
        model.addAttribute("curvePoints", iCurvePoint.findAll());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        iCurvePoint.deleteById(id);

        logger.info("Deleting CurvePoint number " + id);
        model.addAttribute("curvePoints", iCurvePoint.findAll());
        return "redirect:/curvePoint/list";
    }
}
