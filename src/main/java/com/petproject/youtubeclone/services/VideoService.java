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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javafx.util.Pair;

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
    public boolean videoExists(String id) {
        return repo.videoExists(id);
    }
    public int getUserIdByVideoId(String id) {
        return repo.getUserIdByVideoId(id);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
    public List<Video> getVideoListByUserId(int userId) {
        return repo.getVideoListByUserId(userId);
    }

    public VideoDetailUserProjection getVideoById(String videoId) {
        return repo.getVideoByIdWithUserIDChannel(videoId);
    }

    public Pair<Integer,List<VideoUserDTO>> getAllVideo(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<VideoUserProjection> allVideoProjection = repo.getAllVideo(pageable);
        int totalPage = allVideoProjection.getTotalPages();
        List<VideoUserDTO> videos =  allVideoProjection.getContent().stream().map(pro ->
                new VideoUserDTO(pro.getVideoId(), pro.getTitle()
                        ,pro.getUserId(),pro.getChannelName(),pro.getPhotoUrl(),pro.getThumbnail())
        ).toList();

        return new Pair<Integer,List<VideoUserDTO>>(totalPage,videos);

    }
    public Pair<Integer,List<VideoChannelDTO>>  getVideosByChannelNameLatest(String channelName,int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<VideoChannelProjection> videosProjection = repo.getVideosByChannelNameLatest(channelName,pageable);
        int totalPage = videosProjection.getTotalPages();
        List<VideoChannelDTO> videos = videosProjection.stream().map(pro ->
                new VideoChannelDTO(pro.getVideoId(), pro.getTitle()
                        ,pro.getThumbnail(),pro.getUserId())
        ).toList();
        return new Pair<Integer,List<VideoChannelDTO>>(totalPage,videos);

    }
    public Pair<Integer,List<VideoChannelDTO>> getVideosByChannelNameOldest(String channelName,int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<VideoChannelProjection> videosProjection = repo.getVideosByChannelNameOldest(channelName,pageable);
        int totalPage = videosProjection.getTotalPages();
        List<VideoChannelDTO> videos = videosProjection.stream().map(pro ->
                new VideoChannelDTO(pro.getVideoId(), pro.getTitle()
                        ,pro.getThumbnail(),pro.getUserId())
        ).toList();
        return new Pair<Integer,List<VideoChannelDTO>>(totalPage,videos);
    }

    public List<VideoChannelDTO>  getLimitVideos(String channelName,int limit) {
        List<VideoChannelProjection> videosProjection = repo.getLimitVideosByChannelNameLatest(channelName,limit);
        return videosProjection.stream().map(pro ->
                new VideoChannelDTO(pro.getVideoId(), pro.getTitle()
                        ,pro.getThumbnail(),pro.getUserId())
        ).toList();

    }
}
