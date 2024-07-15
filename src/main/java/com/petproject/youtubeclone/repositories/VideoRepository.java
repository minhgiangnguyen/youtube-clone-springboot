package com.petproject.youtubeclone.repositories;

import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.models.dto.VideoChannelDTO;
import com.petproject.youtubeclone.models.projections.VideoChannelProjection;
import com.petproject.youtubeclone.models.projections.VideoDetailUserProjection;
import com.petproject.youtubeclone.models.projections.VideoUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository  extends JpaRepository<Video,String> {

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

//    @Query(value = "SELECT v.video_id as videoId, v.title as title"
//            + ",v.thumbnail as thumbnail,u.user_id as userId "
//            + " from Videos v"
//            +" INNER JOIN Users u"
//            + " ON v.user_id = u.user_id "
//            + " Where u.channel_name = ?1"
//            , nativeQuery = true)
//    List<VideoChannelDTO> getVideosByChannelName(String channelName);

    @Query(value = "SELECT v.video_id as videoId, v.title as title"
            + ",v.thumbnail as thumbnail,u.user_id as userId "
            + " from Videos v"
            +" INNER JOIN Users u"
            + " ON v.user_id = u.user_id "
            + " Where u.channel_name = ?1"
            , nativeQuery = true)
    List<VideoChannelProjection> getVideosByChannelName(String channelName);



}
