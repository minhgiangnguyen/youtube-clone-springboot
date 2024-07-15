package com.petproject.youtubeclone.services;

import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.models.dto.VideoChannelDTO;
import com.petproject.youtubeclone.models.dto.VideoUserDTO;
import com.petproject.youtubeclone.models.projections.VideoChannelProjection;
import com.petproject.youtubeclone.models.projections.VideoDetailUserProjection;
import com.petproject.youtubeclone.models.projections.VideoUserProjection;
import com.petproject.youtubeclone.repositories.VideoRepository;
import com.petproject.youtubeclone.utils.VideoIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class VideoService {
    @Autowired
    private VideoRepository repo;

    public List<Video> listAll() {
        return repo.findAll();
    }

    public Video save(Video video) {
        if(video.getVideoId()==null)video.setVideoId(VideoIdGenerator.generateVideoId());
        if(video.getCreateAt()==null) {
            video.setCreateAt(LocalDateTime.now());
        }
        video.setUpdateAt(LocalDateTime.now());


        return repo.save(video);
    }

    public Video get(String id) {
        return repo.findById(id).get();
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
    public List<Video> getVideoListByUserId(int userId) {
        return repo.getVideoListByUserId(userId);
    }
    public List<VideoUserDTO> getAllVideo() {
        List<VideoUserProjection> allVideoProjection = repo.getAllVideoSpecifyColumn();
        return allVideoProjection.stream().map(pro ->
                new VideoUserDTO(pro.getVideoId(), pro.getTitle()
                        ,pro.getUserId(),pro.getChannelName(),pro.getPhotoUrl(),pro.getThumbnail())
        ).toList();
    }
    public VideoDetailUserProjection getVideoById(String videoId) {
        return repo.getVideoByIdWithUserIDChannel(videoId);
    }

    public List<VideoChannelDTO> getVideosByChannelName(String channelName) {
        List<VideoChannelProjection> videosProjection = repo.getVideosByChannelName(channelName);
        return videosProjection.stream().map(pro ->
                new VideoChannelDTO(pro.getVideoId(), pro.getTitle()
                        ,pro.getThumbnail(),pro.getUserId())
        ).toList();

    }
}
