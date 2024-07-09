package com.petproject.youtubeclone.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoUserDTO {
    private String videoId;
    private String title;
    private String thumbnail;
    private int userId;
    private String channelName;
    private String photoUrl;
    private String photosImagePath;
    private String thumbsPath;

    public VideoUserDTO(String videoId,String title,int userId,String channelName, String photoUrl, String thumbnail){
        this.videoId = videoId;
        this.title = title;
        this.userId = userId;
        this.channelName = channelName;
        this.photosImagePath = (photoUrl==null)?"/user-photos/defaultavatar.jpg":"/user-photos/" + userId + "/" + photoUrl;
        this.thumbsPath = "/user-videos/" + userId + "/" + thumbnail;

    }

}
