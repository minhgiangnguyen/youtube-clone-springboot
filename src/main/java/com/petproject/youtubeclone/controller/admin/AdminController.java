package com.petproject.youtubeclone.controller.admin;

import com.petproject.youtubeclone.models.CustomUserDetails;
import com.petproject.youtubeclone.models.enums.ERole;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping(value = { "/admin","/admin/home" })
    public String index(Model model) {


        return "admin/index";
    }
    @GetMapping("/403")
    public String authorize(Model model) {
        return "admin/403";
    }

}
