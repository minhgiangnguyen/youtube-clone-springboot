package com.petproject.youtubeclone.services;

import com.petproject.youtubeclone.models.VideoElastic;
import com.petproject.youtubeclone.models.dto.VideoChannelDTO;
import com.petproject.youtubeclone.repositories.elasticsearch.VideoSearchRepository;
import com.petproject.youtubeclone.utils.YoutubeUtil;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoSearchService {
    @Autowired
    private VideoSearchRepository repo;

    public Pair<Integer, List<VideoElastic>> searchVideo(String searchTxt, int pageNum, int pageSize){
        Pageable pageable = PageRequest.of(pageNum-1, pageSize,
                Sort.by(Sort.Direction.ASC, "createAt"));
        Page<VideoElastic> videos = repo.findByTitleContaining(searchTxt,pageable);
        int totalPage = videos.getTotalPages();
        videos.getContent().stream().peek(video -> {
            String desc = video.getDescription();
            if(desc.length()>125){
                String newTitle = YoutubeUtil.subTitle(desc,125);
                video.setDescription(newTitle);
            }
        }).toList();
        return new Pair<Integer,List<VideoElastic>>(totalPage,videos.getContent());
    }
}
