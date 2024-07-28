package com.petproject.youtubeclone.controller.client;

import com.petproject.youtubeclone.models.projections.VideoDetailProjection;
import com.petproject.youtubeclone.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VideoController {
    @Autowired
    private VideoService service;

    @GetMapping("/watch")
    public String video(@RequestParam("v") String id, Model model){
        VideoDetailProjection video = service.getVideoById(id);
        if(video == null) return "home/404";
        model.addAttribute("video",video);
        return "home/video";
    }

}
