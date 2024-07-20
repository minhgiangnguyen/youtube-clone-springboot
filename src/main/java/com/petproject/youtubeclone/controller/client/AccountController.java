package com.petproject.youtubeclone.controller.client;

import com.petproject.youtubeclone.models.CustomUserDetails;
import com.petproject.youtubeclone.models.User;
import com.petproject.youtubeclone.models.enums.ERole;
import com.petproject.youtubeclone.services.UserService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Controller
public class AccountController {
    @Autowired
    private UserService service;


    @GetMapping(value = { "/login" })
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "home/account/login";
        }

        return "redirect:/";


    }
//    @GetMapping("login")
//    public String login() {
//        return "home/account/login";
//    }
    @GetMapping(value = { "/register" })
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "home/account/register";
    }
    @PostMapping("/register")
    public String submitForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResult
            , @RequestParam MultipartFile file, Model model) throws IOException {
        String[] imageType=new String[]{"image/jpeg","image/png","image/jpg"};
        if(service.emailExists(user.getEmail())){
            model.addAttribute("msg","Account is already exists");
            return "home/account/register";
        }
        if(service.channelNameExists(user.getChannelName())){
            model.addAttribute("msg","Channel Name is already exists");
            return "home/account/register";
        }
        if (bindingResult.hasErrors()) {
            return "home/account/register";
        } else {
            String fileName = "";
            if (!file.isEmpty()){
                if(!Arrays.asList(imageType).contains(file.getContentType())) {
                    model.addAttribute("err","Invalid image type. Allowed types are .jpg, .jpeg, .png");

                    return "home/account/register";
                }
                if(file.getSize()>1024*1024*2){
                    model.addAttribute("err","Image not upload more than 2mb");

                    return "home/account/register";
                }
                fileName = StringUtils.cleanPath(file.getOriginalFilename());
                user.setPhotoUrl(fileName);

            }

            User savedUser = service.save(user);
//            if(savedUser == null){
//                model.addAttribute("msg","Register fail");
//                return "home/account/register";
//            }
            if(!fileName.isEmpty()){
                String uploadDir = "user-photos/" + savedUser.getUserId();
                FileUploadUtil.saveFile(uploadDir, fileName, file);
            }

            model.addAttribute("success", "Register success");
            return "home/account/login";

        }
    }

    @GetMapping(value = "/success")
    public String success(){
        try {
            CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            if (user.getAuthorities().toString().contains(ERole.ROLE_ADMIN.toString()))
                return "redirect:/admin";
        } catch (Exception e) {

        }
        return "redirect:/";
    }
}

