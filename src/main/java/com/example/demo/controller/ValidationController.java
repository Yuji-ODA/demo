package com.example.demo.controller;

import com.example.demo.controller.form.ValidationTestForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


@Controller
@RequestMapping("val")
public class ValidationController {

    @ModelAttribute("form")
    public ValidationTestForm form() {
        return new ValidationTestForm("ほげ", 50, 100);
    }


//    @GetMapping
    public String f(@ModelAttribute("form") ValidationTestForm form) {
        return "valform";
    }

//    @PostMapping
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String validation(@Valid @ModelAttribute("form") ValidationTestForm form, BindingResult result, Model model) {
        System.out.println(form);

        if (result.hasErrors()) {
            return "valform";
        }

        return "valform";
//
//        model.addAttribute("result", result.hasErrors() ? "Error!!!" : "OK!!!");
//        return "val";
    }
}
