package com.petproject.youtubeclone.controller.client;

import com.petproject.youtubeclone.models.CustomUserDetails;
import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.services.VideoService;
import com.petproject.youtubeclone.utils.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class StudioController {
    @Autowired
    private VideoService service;

    @GetMapping(value = {"studio"})
    public String studio(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "home/account/login";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int id = userDetails.getUserId();
        return "redirect:/studio/"+id;
    }
    @GetMapping(value = {"studio/{id}"})
    public String channelStudio(@PathVariable("id") int id,Model model) {
        Video video = new Video();
        List<Video> videoList = service.getVideoListByUserId(id);
        model.addAttribute("id",id);
        model.addAttribute("video",video);
        model.addAttribute("videoList",videoList);
        model.addAttribute("modalForm","hide");
        return "home/studio";
    }

    @PostMapping("/studio/{id}/upload-video")
    public String submitForm(@PathVariable("id") int id
            , @Valid @ModelAttribute("video") Video video ,BindingResult bindingResult
            , @RequestParam("videoFile") MultipartFile videoFile
            ,@RequestParam("thumbFile") MultipartFile thumbFile, Model model) throws IOException {
        System.out.println(videoFile.getContentType());
        System.out.println(thumbFile.getContentType());
        String[] imageType=new String[]{"image/jpeg","image/png","image/jpg"};
        String[] videoType=new String[]{"video/mp4","video/avi"};


        if(videoFile.isEmpty() && thumbFile.isEmpty()){
            model.addAttribute("errorVideo","You have not uploaded the Video yet");
            model.addAttribute("errorThumb","You have not uploaded the Thumbnail yet");
            model.addAttribute("modalForm","show");
            return "home/studio";
        }else if(videoFile.isEmpty()){
            model.addAttribute("errorVideo","You have not uploaded the Video yet");
            model.addAttribute("modalForm","show");
            return "home/studio";
        }else if(thumbFile.isEmpty()){
            model.addAttribute("errorThumb","You have not uploaded the Thumbnail yet");
            model.addAttribute("modalForm","show");
            return "home/studio";
        }else {
            System.out.println("ERROR TYPE");
            if(!Arrays.asList(imageType).contains(thumbFile.getContentType())
                    && !Arrays.asList(videoType).contains(videoFile.getContentType())  ) {
                model.addAttribute("errorVideo","Invalid video type. Allowed types are .mp4, .avi");
                model.addAttribute("errorThumb","Invalid image type. Allowed types are .jpg, .jpeg, .png");
                model.addAttribute("modalForm","show");
                return "home/studio";
            }
            if(!Arrays.asList(videoType).contains(videoFile.getContentType()) ) {
                model.addAttribute("errorVideo","Invalid video type. Allowed types are .mp4, .avi");
                model.addAttribute("modalForm","show");
                return "home/studio";
            }
            if(!Arrays.asList(imageType).contains(thumbFile.getContentType())) {
                model.addAttribute("errorThumb","Invalid image type. Allowed types are .jpg, .jpeg, .png");
                model.addAttribute("modalForm","show");
                return "home/studio";
            }

            if(videoFile.getSize()> 1024 * 1024 * 1024){
                model.addAttribute("err","Video not upload more than 1 GB");
                model.addAttribute("modalForm","show");
                return "home/studio";
            }
        }
        System.out.println("NO ERROR TYPE");
        String videoFileName = StringUtils.cleanPath(videoFile.getOriginalFilename());
        String thumbFileName = StringUtils.cleanPath(thumbFile.getOriginalFilename());

        video.setUserId(id);
        video.setVideoUrl(videoFileName);
        video.setThumbnail(thumbFileName);

        Video savedVideo = service.save(video);
        String uploadDir = "user-videos/" + savedVideo.getUserId();

        FileUploadUtil.saveFile(uploadDir,videoFileName,videoFile);
        FileUploadUtil.saveFile(uploadDir,thumbFileName,thumbFile);



        model.addAttribute("modalForm","hide");
        return "redirect:/studio/"+id;


    }
}
