package com.petproject.youtubeclone.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoChannelDTO {
    private String videoId;
    private String title;
    private String thumbnail;
    private int userId;
    private String thumbsPath;

    public VideoChannelDTO(String videoId,String title, String thumbnail,int userId){
        this.videoId = videoId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.userId = userId;
        this.thumbsPath = "/user-videos/" + userId + "/" + thumbnail;

    }
}
