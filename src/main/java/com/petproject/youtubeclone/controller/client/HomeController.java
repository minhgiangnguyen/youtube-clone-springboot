package com.petproject.youtubeclone.controller.client;


import com.petproject.youtubeclone.models.CustomUserDetails;
import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.models.dto.VideoUserDTO;
import com.petproject.youtubeclone.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private VideoService service;

    @GetMapping(value = { "/","/home" })
    public String index(Model model) {
        List<VideoUserDTO> allVideo = service.getAllVideo();
        model.addAttribute("allVideo",allVideo);
        return "home/index";
    }


}
