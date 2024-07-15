package com.petproject.youtubeclone.controller.client;

import com.petproject.youtubeclone.models.dto.VideoChannelDTO;
import com.petproject.youtubeclone.models.projections.ChannelProjection;
import com.petproject.youtubeclone.models.projections.VideoChannelProjection;
import com.petproject.youtubeclone.services.UserService;
import com.petproject.youtubeclone.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ChannelController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private UserService userService;

    @GetMapping(value = { "/channels/{channelName}" })
    public String channel(@PathVariable("channelName") String channelName, Model model){
        ChannelProjection channel = userService.getChannelByName(channelName);
        List<VideoChannelDTO> videoList = videoService.getVideosByChannelName(channelName);
        videoList.stream().peek(video -> {
            String title = video.getTitle();
            if(title.length()>58){
                String[] arrTitle = title.split(" ");
                StringBuilder newTitle= new StringBuilder();
                int newLength=0;
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
        model.addAttribute("channel",channel);
        model.addAttribute("videoList",videoList);
        return "home/channel";
    }
//    @GetMapping(value = { "/channels/{channelName}/videos" })
//    public String channelVideos(@PathVariable("channelName") String channelName, Model model){
//        ChannelProjection channel = userService.getChannelByName(channelName);
//        List<VideoChannelProjection> videoList = videoService.getVideosByChannelName(channelName);
//        model.addAttribute("channel",channel);
//        model.addAttribute("videoList",videoList);
//        return "home/channel";
//    }
}
