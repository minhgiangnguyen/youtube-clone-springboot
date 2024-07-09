package com.petproject.youtubeclone;

import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.models.dto.VideoUserDTO;
import com.petproject.youtubeclone.models.projections.VideoUserProjection;
import com.petproject.youtubeclone.repositories.VideoRepository;
import com.petproject.youtubeclone.utils.VideoIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class VideoRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private VideoRepository repo;

    @Test
    public void testCreateVideo() {
        Video video = new Video();
        video.setVideoId(VideoIdGenerator.generateVideoId());
        video.setTitle("Video 1");
        video.setVideoUrl("video.mp4");
        video.setThumbnail("video.jpg");
        video.setDesc("No desc");
        video.setUserId(32);
        video.setCreateAt(LocalDateTime.now());
        video.setUpdateAt(LocalDateTime.now());

        Video savedVideo = repo.save(video);

        Video existVideo = entityManager.find(Video.class, savedVideo.getVideoId());

        assertThat(video.getVideoId()).isEqualTo(existVideo.getVideoId());

    }

    @Test
    public void testGetVideoListByUserId() {
        int userId = 33;
        List<Video> videoList = repo.getVideoListByUserId(userId);
        assertFalse(videoList.isEmpty());

    }

    @Test
    public void testAllVideoList() {
        List<Video> allVideo = repo.findAll();
        assertThat(allVideo).isNotEmpty();
        for(Video v: allVideo){
            System.out.println(v.getUser().getChannelName());
        }
    }

    @Test
    public void testAllVideoSpecifyColumn() {
        List<VideoUserProjection> allVideoProjection = repo.getAllVideoSpecifyColumn();

        assertThat(allVideoProjection).isNotEmpty();
        List<VideoUserDTO> allVideo = allVideoProjection.stream().map(pro ->
                new VideoUserDTO(pro.getVideoId(), pro.getTitle()
                ,pro.getUserId(),pro.getChannelName(),pro.getPhotoUrl(),pro.getThumbnail())
        ).toList();
        for(VideoUserDTO v: allVideo){
            System.out.println(v.getThumbsPath());
        }
    }

}
