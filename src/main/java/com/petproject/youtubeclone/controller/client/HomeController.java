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

import java.lang.reflect.Array;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private VideoService service;

    @GetMapping(value = { "/","/home" })
    public String index(Model model) {
        List<VideoUserDTO> allVideo = service.getAllVideo();
        allVideo.stream().peek(video -> {
            String title = video.getTitle();
         if(title.length()>58){
             String[] arrTitle = title.split(" ");
             StringBuilder newTitle= new StringBuilder();
             int newLength=0;
//             for(String word:title.split(" ")){
//                 newTitleLength+=word.length();
//                 if(word.)
//                 newTitle.append(word);
//             }
            for(int i=0;i<arrTitle.length;i++){
                newLength+=arrTitle[i].length();
                if(i!=arrTitle.length-1 && arrTitle[i+1].length()+newLength>55) {
                    newTitle.append("...");
                    break;
                }
                newTitle.append(" ").append(arrTitle[i]);
            }
            video.setTitle(newTitle.toString());
         }
        }).toList();
        model.addAttribute("allVideo",allVideo);
        return "home/index";
    }


}
