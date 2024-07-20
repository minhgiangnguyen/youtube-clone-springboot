package com.petproject.youtubeclone.repositories;

import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.models.projections.VideoChannelProjection;
import com.petproject.youtubeclone.models.projections.VideoDetailUserProjection;
import com.petproject.youtubeclone.models.projections.VideoUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository  extends JpaRepository<Video,String> {

    @Query("SELECT v FROM Video v WHERE v.userId = ?1")
    public List<Video> getVideoListByUserId(int userId);
    @Query("SELECT v.userId FROM Video v WHERE v.videoId = ?1")
    public int getUserIdByVideoId(String videoId);
    @Query("select case when count(v)> 0 then true else false end from Video v"
            +" where v.videoId = ?1")
    boolean videoExists(String videoId);

    @Query(value = "SELECT v.video_id, v.title,v.thumbnail,u.user_id, u.channel_name,u.photo_url "
            + " from Videos v"
            +" INNER JOIN Users u"
            + " ON v.user_id = u.user_id "
            , nativeQuery = true)
    Page<VideoUserProjection> getAllVideo(Pageable pageable);

    @Query(value = "SELECT v.title,v.description ,v.video_url,v.create_at,"
            +" u.user_id, u.channel_name,u.photo_url "
            + " from Videos v"
            +" INNER JOIN Users u"
            + " ON v.user_id = u.user_id "
            +" WHERE v.video_id= ?1"
            , nativeQuery = true)
    VideoDetailUserProjection getVideoByIdWithUserIDChannel(String videoId);


    @Query(value = "SELECT v.video_id as videoId, v.title as title"
            + ",v.thumbnail as thumbnail,u.user_id as userId "
            + " from Videos v"
            +" INNER JOIN Users u"
            + " ON v.user_id = u.user_id "
            + " Where u.channel_name = ?1"
            +" ORDER BY v.create_at  DESC"
            , nativeQuery = true)
    Page<VideoChannelProjection> getVideosByChannelNameLatest(String channelName,Pageable pageable);

    @Query(value = "SELECT v.video_id as videoId, v.title as title"
            + ",v.thumbnail as thumbnail,u.user_id as userId "
            + " from Videos v"
            +" INNER JOIN Users u"
            + " ON v.user_id = u.user_id "
            + " Where u.channel_name = ?1"
            +" ORDER BY v.create_at  ASC"
            , nativeQuery = true)
    Page<VideoChannelProjection> getVideosByChannelNameOldest(String channelName,Pageable pageable);

    @Query(value = "SELECT v.video_id as videoId, v.title as title"
            + ",v.thumbnail as thumbnail,u.user_id as userId "
            + " from Videos v"
            +" INNER JOIN Users u"
            + " ON v.user_id = u.user_id "
            + " Where u.channel_name = ?1"
            +" ORDER BY v.create_at  DESC"
            +" Limit ?2"
            , nativeQuery = true)
    List<VideoChannelProjection> getLimitVideosByChannelNameLatest(String channelName,int limit);

}
