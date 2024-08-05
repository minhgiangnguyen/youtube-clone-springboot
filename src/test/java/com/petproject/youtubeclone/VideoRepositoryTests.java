package com.petproject.youtubeclone;

import com.petproject.youtubeclone.models.Video;
import com.petproject.youtubeclone.models.dto.VideoChannelDTO;
import com.petproject.youtubeclone.models.dto.VideoHomeDTO;
import com.petproject.youtubeclone.models.projections.VideoDetailProjection;
import com.petproject.youtubeclone.repositories.jpa.VideoRepository;
import com.petproject.youtubeclone.utils.VideoIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

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
        video.setDescription("No desc");
        video.setUserId(32);
        video.setCreateAt(LocalDateTime.now());
        video.setUpdateAt(LocalDateTime.now());

        Video savedVideo = repo.save(video);

        Video existVideo = entityManager.find(Video.class, savedVideo.getVideoId());

        assertThat(video.getVideoId()).isEqualTo(existVideo.getVideoId());

    }

    @Test
    public void testGetVideoListByUserId() {
        int userId = 32;
        List<Video> videoList = repo.getVideoListByUserId(userId);
        assertFalse(videoList.isEmpty());

    }



    @Test
    public void testAllVideo() {
        Pageable pageable = PageRequest.of(0, 12,
                Sort.by(Sort.Direction.DESC, "createAt"));

//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<VideoHomeDTO> videos = repo.getAllVideo(pageable);

        assertThat(videos).isNotEmpty();
//        List<VideoUserDTO> allVideo = allVideoProjection.stream().map(pro ->
//                new VideoUserDTO(pro.getVideoId(), pro.getTitle()
//                ,pro.getUserId(),pro.getChannelName(),pro.getPhotoUrl(),pro.getThumbnail())
//        ).toList();
        for(VideoHomeDTO v: videos){
            System.out.println(v.getTimeAgo());
        }
    }

    @Test
    public void testGetVideoSpecifyColumnById() {
        String videoId = "08givmryRb6T13DoToJl8g";
        VideoDetailProjection videoProjection = repo.getVideoDetail(videoId);

        assertThat(videoProjection).isNotNull();
        System.out.println(videoProjection.getDescription());

    }
    @Test
    public void testGetVideoListByChannelName() {
        String channelName = "minhgiangnguyen";
        Pageable pageable = PageRequest.of(0, 5,
                Sort.by(Sort.Direction.DESC, "createAt"));
        Page<VideoChannelDTO> videos = repo.getVideosByChannelName(channelName,pageable);

        assertThat(videos).isNotEmpty();
        for (VideoChannelDTO v : videos){
            System.out.println(v.getTitle());
        }

    }



}
