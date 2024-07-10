package com.petproject.youtubeclone.models.dto;

import com.petproject.youtubeclone.models.projections.UserAvatarPath;
import com.petproject.youtubeclone.models.projections.VideoDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class VideoDetailUserDTO {
    private List<VideoDetail> videos;
    private UserAvatarPath user;
    private String photosImagePath;
    private String videosPath;

    public VideoDetailUserDTO(List<VideoDetail> videos,UserAvatarPath user){
        this.videos = videos;
        this.user = user;
        this.photosImagePath = user.getPhotosImagePath();
        this.videosPath = videos.get(0).getVideosPath();

    }
//    private String title;
//    private String thumbnail;
//    private String desc;
//    private String videoUrl;
//    private LocalDateTime createAt;
//    private int userId;
//    private String channelName;
//    private String photoUrl;
//    private String photosImagePath;
//    private String videosPath;

//    public VideoDetailUserDTO(String title, String thumbnail, String desc, String videoUrl
//            , LocalDateTime createAt, int userId, String channelName, String photoUrl){
//        this.title = title;
//        this.thumbnail = thumbnail;
//        this.desc = desc;
//        this.videoUrl = videoUrl;
//        this.createAt = createAt;
//        this.userId = userId;
//        this.channelName = channelName;
//        this.photoUrl = photoUrl;
//        this.photosImagePath = (photoUrl==null)?"/user-photos/defaultavatar.jpg":"/user-photos/" + userId + "/" + photoUrl;
//        this.videosPath = (videoUrl == null || userId == 0)?null:"/user-videos/" + userId + "/" + videoUrl;
//
//    }
}
