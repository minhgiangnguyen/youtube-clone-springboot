package com.petproject.youtubeclone.controller.client;

import com.petproject.youtubeclone.models.VideoElastic;
import com.petproject.youtubeclone.models.dto.VideoChannelDTO;
import com.petproject.youtubeclone.services.VideoSearchService;
import com.petproject.youtubeclone.services.VideoService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private VideoSearchService service;

    @GetMapping(value = { "/results" })
    public String searchPage(@RequestParam("search_query") String searchText, Model model){
        int pageSize = 8;
        String searchTrim = searchText.trim();
        Pair<Integer, List<VideoElastic>> pair =
                service.searchVideo(searchTrim,1, pageSize);
        List<VideoElastic> searchVideos = pair.getValue();
        int totalPage = pair.getKey();

        model.addAttribute("searchVideos",searchVideos);
        return "home/search";
    }

}
