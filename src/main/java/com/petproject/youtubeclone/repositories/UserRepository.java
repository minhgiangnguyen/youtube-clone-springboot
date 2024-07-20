package com.petproject.youtubeclone.repositories;

import com.petproject.youtubeclone.models.User;
import com.petproject.youtubeclone.models.enums.ERole;
import com.petproject.youtubeclone.models.projections.ChannelProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    @Query("SELECT u.role FROM User u WHERE u.userId = ?1")
     ERole getRoleById(int id);

    @Modifying
    @Query("update User u set u.role = ?1 Where u.userId = ?2")
    void alterRole(ERole role,int userId);

    @Query("select case when count(u)> 0 then true else false end from User u"
            +" where u.email = ?1")
    boolean emailExists(String email);
    @Query("select case when count(u)> 0 then true else false end from User u"
            +" where u.channelName = ?1")
    boolean channelNameExists(String channelName);

    @Query(value = "SELECT u.user_id, u.channel_name,u.photo_url, u.create_at,"
            + " Cast(Count(u.user_id) as Integer) as total_video  "
            + " from Users u "
            +" LEFT JOIN  Videos v ON u.user_id= v.user_id "
            +" WHERE u.channel_name = ?1 "
            +" GROUP BY u.user_id,u.channel_name,u.photo_url,u.create_at "
            , nativeQuery = true)
    public ChannelProjection getChannelByName(String channelName);

}
