package com.petproject.youtubeclone.services;

import com.petproject.youtubeclone.models.VideoElastic;
import com.petproject.youtubeclone.repositories.elasticsearch.VideoSearchRepository;
import com.petproject.youtubeclone.utils.YoutubeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VideoSearchService {
    @Autowired
    private VideoSearchRepository repo;

    public Page<VideoElastic> searchVideo(String searchTxt, int pageNum, int pageSize){
        Pageable pageable = PageRequest.of(pageNum-1, pageSize,
                Sort.by(Sort.Direction.ASC, "createAt"));
       String[] arrKeyword = searchTxt.trim().split(" ");
//        Page<VideoElastic> newVideos =Page.empty(pageable);
        Map<String,Integer> videoCount  = new HashMap<>();
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        List<VideoElastic> concatVideos = new ArrayList<>();

        for(String keyword : arrKeyword){
            List<VideoElastic> videos = repo.findByTitleContaining(keyword.trim());
            concatVideos.addAll(videos);
//            Stream.concat(concatVideos.stream(),videos.stream()).toList();
        }
        concatVideos.forEach(video ->
                videoCount.merge(video.getVideoId(), 1, Integer::sum)
        );
        List<VideoElastic> searchVideos = concatVideos.stream()
                .filter(video -> videoCount.get(video.getVideoId()) == arrKeyword.length)
                .distinct()
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), searchVideos.size());

        List<VideoElastic> pageVideo = searchVideos.subList(start, end);
        Page<VideoElastic> newVideos = new PageImpl<>(pageVideo, pageable, searchVideos.size());

//        videos.getContent().stream().filter(video -> {
//            String desc = video.getDescription();
//            String title = video.getTitle();
//            String contentDesc = YoutubeUtil.contentDesc(desc);
//            System.out.println(title.contains(searchTxt));
//            System.out.println(contentDesc.contains(searchTxt));
//            return title.contains(searchTxt) || contentDesc.contains(searchTxt);
//        }).toList();
        newVideos.getContent().stream().peek(video -> {
            String desc = video.getDescription();
            if(desc.length()>110){
                String newDesc= YoutubeUtil.subDesc(desc,110);
                video.setDescription(newDesc);
            }
        }).toList();

        return newVideos;
    }
}
