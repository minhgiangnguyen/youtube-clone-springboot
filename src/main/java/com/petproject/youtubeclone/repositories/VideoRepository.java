package com.petproject.youtubeclone.repositories;

import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.models.projections.VideoDetailUserProjection;
import com.petproject.youtubeclone.models.projections.VideoUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository  extends JpaRepository<Video,Integer> {

    @Query("SELECT v FROM Video v WHERE v.userId = ?1")
    public List<Video> getVideoListByUserId(int userId);

    @Query(value = "SELECT v.video_id, v.title,v.thumbnail,u.user_id, u.channel_name,u.photo_url "
            + " from Videos v"
            +" INNER JOIN Users u"
            + " ON v.user_id = u.user_id "
            , nativeQuery = true)
    List<VideoUserProjection> getAllVideoSpecifyColumn();
    @Query(value = "SELECT v.title,v.description ,v.video_url,v.create_at,"
            +" u.user_id, u.channel_name,u.photo_url "
            + " from Videos v"
            +" INNER JOIN Users u"
            + " ON v.user_id = u.user_id "
            +" WHERE v.video_id= ?1"
            , nativeQuery = true)
    VideoDetailUserProjection getVideoByIdWithUserIDChannel(String videoId);


}
