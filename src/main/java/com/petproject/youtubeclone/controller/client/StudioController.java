package com.petproject.youtubeclone.controller.client;

import com.petproject.youtubeclone.models.CustomUserDetails;
import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.services.VideoService;
import com.petproject.youtubeclone.utils.FileUploadUtil;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
public class StudioController {
    @Autowired
    private VideoService service;

    @GetMapping(value = {"studio","studio/","studio/channel","studio/channel/"})
    public String studio(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "home/account/login";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int id = userDetails.getUserId();
        return "redirect:/studio/channel/"+id;
    }
    @GetMapping(value = {"studio/channel/{id}"})
    public String channelStudio(@PathVariable("id") int id,Model model) {
        Video video = new Video();
        List<Video> videoList = service.getVideoListByUserId(id);
        model.addAttribute("id",id);
        model.addAttribute("newVideo",video);
        model.addAttribute("videoList",videoList);
        model.addAttribute("modalForm","hide");
        return "home/studio";
    }
    @GetMapping(value = {"studio/video/{id}/edit"})
    public String editVideo(@PathVariable("id") String id,Model model, HttpServletRequest request) {
        String baseUrl = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
                .replacePath(null)
                .build()
                .toUriString();
        Video video = service.get(id);
        model.addAttribute("video",video);
        model.addAttribute("baseUrl",baseUrl);
        return "home/editVideo";
    }


    @PostMapping("/studio/channel/{id}/videos/upload")
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
        return "redirect:/studio/channel"+id;


    }@PostMapping("/studio/video/{videoId}/edit")
    public String editForm(@PathVariable("videoId") String videoId
            , @Valid @ModelAttribute("video") Video video ,BindingResult bindingResult
            ,@RequestParam("thumbFile") MultipartFile thumbFile, String createDate
            ,Model model,HttpServletRequest request) throws IOException {
        System.out.println(createDate);
        String[] imageType=new String[]{"image/jpeg","image/png","image/jpg"};
        if(!thumbFile.isEmpty()){
            if(!Arrays.asList(imageType).contains(thumbFile.getContentType())) {
                model.addAttribute("errorThumb","Invalid image type. Allowed types are .jpg, .jpeg, .png");
                model.addAttribute("modalForm","show");
                return "home/studio";
            }
            if(thumbFile.getSize()> 1024 * 1024 * 10){
                model.addAttribute("err","Video not upload more than 10 mb");
                model.addAttribute("modalForm","show");
                return "home/studio";
            }
            String thumbFileName = StringUtils.cleanPath(thumbFile.getOriginalFilename());
            String uploadDir = "user-videos/" + video.getUserId();
            video.setThumbnail(thumbFileName);
        }
        video.setCreateAt(LocalDateTime.parse(createDate));

        service.save(video);


//        return "redirect:/studio/channel/"+video.getUserId();
        String baseUrl = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
                .replacePath(null)
                .build()
                .toUriString();
        model.addAttribute("video",video);
        model.addAttribute("baseUrl",baseUrl);
        model.addAttribute("alert"," ");
        return "home/editVideo";
    }
}
