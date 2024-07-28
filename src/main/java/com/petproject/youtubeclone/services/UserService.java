package com.petproject.youtubeclone.services;

import com.petproject.youtubeclone.models.User;
import com.petproject.youtubeclone.models.enums.ERole;
import com.petproject.youtubeclone.models.projections.UserChannelProjection;
import com.petproject.youtubeclone.repositories.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private  UserRepository repo;

    public List<User> listAll() {
        return repo.findAll();
    }

    public User save(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        if(user.getCreateAt()==null) {
            user.setCreateAt(LocalDateTime.now());
        }
        user.setUpdateAt(LocalDateTime.now());

        user.setRole(ERole.ROLE_USER);


        return repo.save(user);
    }

    public User get(int id) {
        return repo.findById(id).get();
    }
    public boolean emailExists(String email){
        return repo.emailExists(email);
    }
    public boolean channelNameExists(String channelName){
        return repo.channelNameExists(channelName);
    }
    public ERole getRoleById(int id) {
        return repo.getRoleById(id);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
    public void alterRole(ERole role,int id) {
        repo.alterRole(role,id);
    }


    public UserChannelProjection getChannelByName(String channelName){
        return repo.getChannelByName(channelName);
    }
}
