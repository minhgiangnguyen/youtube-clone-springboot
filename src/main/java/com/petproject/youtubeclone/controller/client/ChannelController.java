package com.petproject.youtubeclone.controller.client;

import com.petproject.youtubeclone.models.dto.VideoChannelDTO;
import com.petproject.youtubeclone.models.projections.ChannelProjection;
import com.petproject.youtubeclone.models.projections.VideoChannelProjection;
import com.petproject.youtubeclone.services.UserService;
import com.petproject.youtubeclone.services.VideoService;
import jakarta.servlet.http.HttpServletRequest;
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

    static String subTitle(String title,int numSub) {
        String[] arrTitle = title.split(" ");
        StringBuilder newTitle= new StringBuilder();
        int newLength=0;
        for(int i=0;i<arrTitle.length;i++){
            newLength+=arrTitle[i].length();
            if(i!=arrTitle.length-1 && arrTitle[i+1].length()+newLength>numSub-3) {
                newTitle.append("...");
                break;
            }
            newTitle.append(" ").append(arrTitle[i]);
        }
        return newTitle.toString();
    }


    @GetMapping(value = { "/{channelName}","/{channelName}/","/{channelName}/home" })
    public String homeChannel(@PathVariable("channelName") String channelName, Model model){
        ChannelProjection channel = userService.getChannelByName(channelName);
        List<VideoChannelDTO> videoList = videoService.getVideosByChannelNameLatest(channelName);
        videoList.stream().peek(video -> {
            String title = video.getTitle();
            if(title.length()>65){
                String newTitle = ChannelController.subTitle(title,65);
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
        List<VideoChannelDTO> videoList = videoService.getVideosByChannelNameLatest(channelName);
        videoList.stream().peek(video -> {
            String title = video.getTitle();
            if(title.length()>65){
                String newTitle = ChannelController.subTitle(title,65);
                video.setTitle(newTitle);
            }
        }).toList();
        model.addAttribute("channel",channel);
        model.addAttribute("videoList",videoList);
        model.addAttribute("page","videos");
        model.addAttribute("baseUrl",baseUrl);
        return "home/channelProfile/channel";
    }

    @PostMapping(value = { "/{channelName}/videos" })
    public String sortVideos(@PathVariable("channelName") String channelName
            ,@RequestParam("s") String sortType, Model model){
//        System.out.println(sortType);
//        ChannelProjection channel = userService.getChannelByName(channelName);
        List<VideoChannelDTO> videoList =sortType.equals("latest")
                ?videoService.getVideosByChannelNameLatest(channelName)
                :videoService.getVideosByChannelNameOldest(channelName);
        videoList.stream().peek(video -> {
            String title = video.getTitle();
            if(title.length()>65){
                String newTitle = ChannelController.subTitle(title,65);
                video.setTitle(newTitle);
            }
        }).toList();
//        model.addAttribute("channel",channel);
        model.addAttribute("videoList",videoList);
//        model.addAttribute("sortType",sortType);
//        model.addAttribute("page","videos");
        return "home/channelProfile/sortChannelVideos";
    }


}
