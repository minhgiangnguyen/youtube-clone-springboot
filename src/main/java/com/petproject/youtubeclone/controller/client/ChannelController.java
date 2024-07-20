package com.petproject.youtubeclone.controller.client;

import com.petproject.youtubeclone.models.dto.VideoChannelDTO;
import com.petproject.youtubeclone.models.dto.VideoUserDTO;
import com.petproject.youtubeclone.models.projections.ChannelProjection;
import com.petproject.youtubeclone.models.projections.VideoChannelProjection;
import com.petproject.youtubeclone.services.UserService;
import com.petproject.youtubeclone.services.VideoService;
import com.petproject.youtubeclone.utils.YoutubeUtil;
import jakarta.servlet.http.HttpServletRequest;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
public class ChannelController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private UserService userService;

    private final int pageSize =8;

    @GetMapping(value = { "/{channelName}","/{channelName}/","/{channelName}/home" })
    public String homeChannel(@PathVariable("channelName") String channelName, Model model){
        ChannelProjection channel = userService.getChannelByName(channelName);
        int limit = 8;
        List<VideoChannelDTO> videoList = videoService.getLimitVideos(channelName, limit);
        videoList.stream().peek(video -> {
            String title = video.getTitle();
            if(title.length()>65){
                String newTitle = YoutubeUtil.subTitle(title,65);
                video.setTitle(newTitle);
            }
        }).toList();
        model.addAttribute("channel",channel);
        model.addAttribute("videoList",videoList);
        model.addAttribute("page","home");
        return "home/channelProfile/channel";
    }

    @GetMapping(value = { "/{channelName}/videos" })
    public String videosChannel(@PathVariable("channelName") String channelName, Model model
                                ,HttpServletRequest request){
        String baseUrl = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
                .replacePath(null)
                .build()
                .toUriString();
        ChannelProjection channel = userService.getChannelByName(channelName);
        Pair<Integer, List<VideoChannelDTO>> pair =
                videoService.getVideosByChannelNameLatest(channelName,1, pageSize);
        List<VideoChannelDTO> pageVideos = pair.getValue();
        int totalPage = pair.getKey();
        pageVideos.stream().peek(video -> {
            String title = video.getTitle();
            if(title.length()>65){
                String newTitle = YoutubeUtil.subTitle(title,65);
                video.setTitle(newTitle);
            }
        }).toList();
        model.addAttribute("channel",channel);
        model.addAttribute("videoList",pageVideos);
        model.addAttribute("page","videos");
        model.addAttribute("baseUrl",baseUrl);
        model.addAttribute("totalPage", totalPage);
        return "home/channelProfile/channel";
    }

    @PostMapping(value = { "/{channelName}/videos" })
    public String sortVideos(@PathVariable("channelName") String channelName
            ,@RequestParam("s") String sortType, Model model){
        List<VideoChannelDTO> videoList =sortType.equals("latest")
                ?videoService.getVideosByChannelNameLatest(channelName,1,pageSize).getValue()
                :videoService.getVideosByChannelNameOldest(channelName,1,pageSize).getValue();
        videoList.stream().peek(video -> {
            String title = video.getTitle();
            if(title.length()>65){
                String newTitle = YoutubeUtil.subTitle(title,65);
                video.setTitle(newTitle);
            }
        }).toList();
        model.addAttribute("videoList",videoList);
        return "home/channelProfile/sortChannelVideos";
    }

    @PostMapping(value = { "/{channelName}/videos/page/{pageNum}" })
    public String sortPageVideos(@PathVariable("channelName") String channelName
            ,@PathVariable("pageNum") int pageNum
            ,@RequestParam("s") String sortType, Model model){
        List<VideoChannelDTO> videoList =sortType.equals("latest")
                ?videoService.getVideosByChannelNameLatest(channelName,pageNum,pageSize).getValue()
                :videoService.getVideosByChannelNameOldest(channelName,pageNum,pageSize).getValue();
        videoList.stream().peek(video -> {
            String title = video.getTitle();
            if(title.length()>65){
                String newTitle = YoutubeUtil.subTitle(title,65);
                video.setTitle(newTitle);
            }
        }).toList();
        model.addAttribute("videoList",videoList);
        return "home/channelProfile/sortChannelVideos";
    }

}
