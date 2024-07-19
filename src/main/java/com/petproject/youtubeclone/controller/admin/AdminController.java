package com.petproject.youtubeclone.controller.admin;

import com.petproject.youtubeclone.models.CustomUserDetails;
import com.petproject.youtubeclone.models.enums.ERole;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping(value = { "/admin","/admin/" })
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_USER"));
        if(hasUserRole) return "home/404";

        return "admin/index";
    }
    @GetMapping("/403")
    public String authorize(Model model) {
        return "admin/403";
    }

}
