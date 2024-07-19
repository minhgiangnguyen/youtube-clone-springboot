package com.petproject.youtubeclone.controller.admin;

import com.petproject.youtubeclone.models.User;
import com.petproject.youtubeclone.models.enums.ERole;
import com.petproject.youtubeclone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping(value = { "/admin/channel" })
    public String list(Model model) {
        List<User> channelList = service.listAll();
        model.addAttribute("channelList",channelList);
        return "admin/channelList";
    }
    @GetMapping(value = "/admin/channel/{id}/edit/role")
    public String editRole(@PathVariable("id") int id, Model model) {
        ERole role = service.getRoleById(id);
        if(role==ERole.ROLE_USER){
            service.alterRole(ERole.ROLE_ADMIN,id);
        }else{
            service.alterRole(ERole.ROLE_USER,id);
        }
        return "redirect:/admin/channel";

    }
    @GetMapping(value = { "/admin/channel/{id}/delete" })
    public String delete(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "redirect:/admin/channel";
    }

}
