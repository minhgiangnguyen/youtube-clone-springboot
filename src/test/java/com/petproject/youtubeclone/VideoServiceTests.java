package com.petproject.youtubeclone;

import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.repositories.VideoRepository;
import com.petproject.youtubeclone.services.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class VideoServiceTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private VideoService service;
    @Test
    public void testCreateVideo() {
        Video video = new Video();
        video.setTitle("Video 1");
        video.setVideoUrl("video.mp4");
        video.setThumbnail("video.jpg");
        video.setDesc("No desc");
        video.setUserId(32);

        Video savedVideo = service.save(video);

        Video existVideo = entityManager.find(Video.class, savedVideo.getVideoId());

        assertThat(video.getVideoId()).isEqualTo(existVideo.getVideoId());



    }
}
