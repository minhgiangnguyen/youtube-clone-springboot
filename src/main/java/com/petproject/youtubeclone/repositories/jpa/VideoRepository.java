package com.petproject.youtubeclone.repositories.jpa;

import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.models.dto.VideoChannelDTO;
import com.petproject.youtubeclone.models.dto.VideoHomeDTO;
import com.petproject.youtubeclone.models.projections.VideoDetailProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository  extends CrudRepository<Video,String> {

    @Query("SELECT v FROM Video v WHERE v.userId = ?1")
    public List<Video> getVideoListByUserId(int userId);

    @Query("SELECT v.userId FROM Video v WHERE v.videoId = ?1")
    public int getUserIdByVideoId(String videoId);

    @Query("select case when count(v)> 0 then true else false end from Video v"
            +" where v.videoId = ?1")
    boolean videoExists(String videoId);

    @Query(value = "SELECT new com.petproject.youtubeclone.models.dto.VideoHomeDTO("
            + " v.videoId, v.title,v.thumbnail,v.userId,v.createAt, u.channelName,u.photoUrl)"
            + " from Video v INNER JOIN v.user u"
    )
    Page<VideoHomeDTO> getAllVideo(Pageable pageable);

    @Query(value = "SELECT new com.petproject.youtubeclone.models.dto.VideoChannelDTO("
            + " v.videoId, v.title,v.thumbnail,v.userId,v.createAt)"
            + " from Video v INNER JOIN v.user u where u.channelName = ?1"
    )
    Page<VideoChannelDTO> getVideosByChannelName(String channelName, Pageable pageable);


    @Query(value = "SELECT v.title,v.description ,v.video_url,v.create_at,"
            +" u.user_id, u.channel_name,u.photo_url "
            + " from Videos v"
            +" INNER JOIN Users u"
            + " ON v.user_id = u.user_id "
            +" WHERE v.video_id= ?1"
            , nativeQuery = true)
    VideoDetailProjection getVideoDetail(String videoId);

}
