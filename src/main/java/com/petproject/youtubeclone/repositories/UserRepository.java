package com.petproject.youtubeclone.repositories;

import com.petproject.youtubeclone.models.User;
import com.petproject.youtubeclone.models.projections.ChannelProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);


//    @Query("SELECT u.userId as userId, u.channelName as channelName,"
//            +"u.photoUrl as photoUrl, u.createAt as createAt"
//            + " FROM User u WHERE u.channelName = ?1")
    @Query(value = "SELECT u.user_id, u.channel_name,u.photo_url, u.create_at,"
            + " Cast(Count(u.user_id) as Integer) as total_video  "
            + " from Users u "
            +" LEFT JOIN  Videos v ON u.user_id= v.user_id "
            +" WHERE u.channel_name = ?1 "
            +" GROUP BY u.user_id,u.channel_name,u.photo_url,u.create_at "
            , nativeQuery = true)
    public ChannelProjection getChannelByName(String channelName);

}
