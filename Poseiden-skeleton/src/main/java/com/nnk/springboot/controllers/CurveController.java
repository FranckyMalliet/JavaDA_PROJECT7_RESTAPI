package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.ICurvePointService;
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
    private ICurvePointService iCurvePoint;

    public CurveController(ICurvePointService iCurvePoint){
        this.iCurvePoint=iCurvePoint;
    }

    /**
     * Method calling a html page displaying all CurvePoints entities
     * from a database
     * @param model
     * @return a String of a html page with all CurvePoints
     */

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", iCurvePoint.findAll());
        return "curvePoint/list";
    }

    /**
     * Method calling a html page with a form that can add a new CurvePoint
     * to the database
     * @return a String of a html page
     */

    @GetMapping("/curvePoint/add")
    public String addBidForm() {
        return "curvePoint/add";
    }

    /**
     * Method validating or not a new CurvePoint. If it's validated,
     * add it to the database
     * @param curvePoint
     * @param result
     * @param model
     * @return a String of a html page with all CurvePoints after adding
     * a new Bid to the database
     */

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

    /**
     * Given a CurvePoint found by his ID, this method call a html form with
     * the CurvePoint data already written, ready to be updated
     * @param id
     * @param model
     * @return a String of a html page
     */

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = iCurvePoint.findById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * Method validating or not a CurvePoint. If it's validated, update it
     * to the database
     * @param id
     * @param curvePoint
     * @param result
     * @param model
     * @return a String of a html page with all CurvePoints after updating
     * a CurvePoint in the database
     */

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

    /**
     * Method used to delete CurvePoints entities in the database
     * @param id
     * @param model
     * @return a String of a html page with all CurvePoints after deleting
     * a CurvePoint in the database
     */

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        iCurvePoint.deleteById(id);

        logger.info("Deleting CurvePoint number " + id);
        model.addAttribute("curvePoints", iCurvePoint.findAll());
        return "redirect:/curvePoint/list";
    }
}
