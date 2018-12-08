package com.example.demo.app;

import com.example.demo.app.service.OreoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/hoge")
public class HogeController {

    private final OreoreService oreoreService;

    @GetMapping(path = "")
    public String hoge(Principal principal, Model model) {
        model.addAttribute("message", oreoreService.getMessage());
        return "hoge";
    }
}
