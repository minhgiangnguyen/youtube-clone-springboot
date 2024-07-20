package com.petproject.youtubeclone.controller.client;


import com.petproject.youtubeclone.models.CustomUserDetails;
import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.models.dto.VideoUserDTO;
import com.petproject.youtubeclone.models.projections.VideoUserProjection;
import com.petproject.youtubeclone.repositories.VideoRepository;
import com.petproject.youtubeclone.services.VideoService;
import com.petproject.youtubeclone.utils.YoutubeUtil;
import jakarta.servlet.http.HttpServletRequest;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private VideoService service;
    @Autowired
    private VideoRepository repo;
//    @GetMapping("/{url}")
//    public String catch404(@PathVariable("url")String url){
//        String[] urlArr = new String[]{"home","register","webjars","assets_home","assets_admin",
//        "user-videos","user-photos","videos","channels"};
//
//        if(!Arrays.asList(urlArr).contains(url)) return "home/404";
//        return "redirect:/"+url;
//    }
    int pageSize=6;
    @GetMapping(value = { "/" })
    public String index(Model model, HttpServletRequest request) {
        int pageNum=1;
        String baseUrl = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
                .replacePath(null)
                .build()
                .toUriString();
        Pair<Integer, List<VideoUserDTO>> pair = service.getAllVideo(pageNum,pageSize);
        List<VideoUserDTO> pageVideos = pair.getValue();
        int totalPage = pair.getKey();
        pageVideos.stream().peek(video -> {
            String title = video.getTitle();
         if(title.length()>58){
             String newTitle = YoutubeUtil.subTitle(title,58);
            video.setTitle(newTitle);
         }
        }).toList();
        model.addAttribute("pageVideos",pageVideos);
        model.addAttribute("baseUrl",baseUrl);
        model.addAttribute("totalPage", totalPage);
        return "home/index";
    }
    @PostMapping(value = { "/page/{pageNum}" })
    public String loadingVideos(@PathVariable("pageNum") int pageNum, Model model) {
        List<VideoUserDTO> pageVideos = service.getAllVideo(pageNum,pageSize).getValue();
        pageVideos.stream().peek(video -> {
            String title = video.getTitle();
            if(title.length()>58){
                String newTitle = YoutubeUtil.subTitle(title,58);
                video.setTitle(newTitle);
            }
        }).toList();
        model.addAttribute("pageVideos",pageVideos);
        return "home/pageAllVideos";
    }



}
