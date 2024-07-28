package com.petproject.youtubeclone.services;

import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.models.dto.VideoChannelDTO;
import com.petproject.youtubeclone.models.dto.VideoHomeDTO;
import com.petproject.youtubeclone.models.projections.VideoDetailProjection;
import com.petproject.youtubeclone.repositories.jpa.VideoRepository;
import com.petproject.youtubeclone.utils.VideoIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public void deleteVideoById(String id) {
        repo.deleteById(id);
    }


    public List<Video> getVideoListByUserId(int userId) {
        return repo.getVideoListByUserId(userId);
    }

    public VideoDetailProjection getVideoById(String videoId) {
        return repo.getVideoDetail(videoId);
    }

    //Home page
    public Pair<Integer,List<VideoHomeDTO>> getAllVideo(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize, Sort.by(Sort.Direction.DESC, "createAt"));
        Page<VideoHomeDTO> videos = repo.getAllVideo(pageable);
        int totalPage = videos.getTotalPages();

        return new Pair<Integer,List<VideoHomeDTO>>(totalPage,videos.getContent());

    }

    public Pair<Integer,List<VideoChannelDTO>>  getAllVideoByChannelNameLatest(String channelName,int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize, Sort.by(Sort.Direction.DESC, "createAt"));
        Page<VideoChannelDTO> videos = repo.getVideosByChannelName(channelName,pageable);
        int totalPage = videos.getTotalPages();
        return new Pair<Integer,List<VideoChannelDTO>>(totalPage,videos.getContent());

    }
    public Pair<Integer,List<VideoChannelDTO>> getAllVideoByChannelNameOldest(String channelName,int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize, Sort.by(Sort.Direction.ASC, "createAt"));
        Page<VideoChannelDTO> videos = repo.getVideosByChannelName(channelName,pageable);
        int totalPage = videos.getTotalPages();
        return new Pair<Integer,List<VideoChannelDTO>>(totalPage,videos.getContent());
    }

    public List<VideoChannelDTO> getLimitVideosByChannelName(String channelName, int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createAt"));
        return repo.getVideosByChannelName(channelName,pageable).getContent();
    }
}
