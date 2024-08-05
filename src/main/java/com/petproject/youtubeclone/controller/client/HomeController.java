package com.petproject.youtubeclone.controller.client;


import com.petproject.youtubeclone.models.dto.VideoHomeDTO;
import com.petproject.youtubeclone.services.VideoService;
import com.petproject.youtubeclone.utils.YoutubeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private VideoService service;

    int pageSize=6;
    @GetMapping(value = { "/" })
    public String index(Model model, HttpServletRequest request) {
        int pageNum=1;
        String baseUrl = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
                .replacePath(null)
                .build()
                .toUriString();
        Page<VideoHomeDTO> firstPage = service.getAllVideo(pageNum,pageSize);
        List<VideoHomeDTO> pageVideos = firstPage.getContent();
        int totalPage = firstPage.getTotalPages();
        pageVideos.stream().peek(video -> {
            String title = video.getTitle();
            if(title.length()>58){
                String newTitle = YoutubeUtil.subTitle(title,58);
                video.setTitle(newTitle);
            }
        }).toList();
        System.out.println(pageVideos.isEmpty());
        model.addAttribute("pageVideos",pageVideos);
        model.addAttribute("baseUrl",baseUrl);
        model.addAttribute("totalPage", totalPage);
        return "home/index";
    }
    @PostMapping(value = { "/page/{pageNum}" })
    public String loadingVideos(@PathVariable("pageNum") int pageNum, Model model) {
        List<VideoHomeDTO> pageVideos = service.getAllVideo(pageNum,pageSize).getContent();
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
