package com.petproject.youtubeclone.controller.client;

import com.petproject.youtubeclone.models.VideoElastic;
import com.petproject.youtubeclone.models.dto.VideoChannelDTO;
import com.petproject.youtubeclone.models.dto.VideoHomeDTO;
import com.petproject.youtubeclone.services.VideoSearchService;
import com.petproject.youtubeclone.services.VideoService;
import com.petproject.youtubeclone.utils.YoutubeUtil;
import jakarta.servlet.http.HttpServletRequest;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private VideoSearchService service;

    private final int pageSize = 8;

    @GetMapping(value = { "/results" })
    public String searchPage(@RequestParam("search_query") String searchText, Model model, HttpServletRequest request){

        String baseUrl = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
                .replacePath(null)
                .build()
                .toUriString();
        String searchTrim = searchText.trim();
        Pair<Integer, List<VideoElastic>> pair =
                service.searchVideo(searchTrim,1, pageSize);
        int totalPage = pair.getKey();
        List<VideoElastic> searchVideos = pair.getValue();


        model.addAttribute("searchVideos",searchVideos);
        return "home/search";
    }
    public String loadingSearchPage(@RequestParam("search_query") String searchText,
                                    @PathVariable("pageNum") int pageNum, Model model) {
        List<VideoElastic> searchVideos = service.searchVideo(searchText,pageNum,pageSize).getValue();

        model.addAttribute("searchVideos",searchVideos);
        return "home/pageAllVideos";
    }

}
