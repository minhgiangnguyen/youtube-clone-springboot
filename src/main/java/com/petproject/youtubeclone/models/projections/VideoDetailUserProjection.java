package com.petproject.youtubeclone.models.projections;

import java.time.LocalDateTime;
import java.util.List;

public interface VideoDetailUserProjection {
    String getTitle();
    String getDescription();
    String getVideoUrl();
    LocalDateTime getCreateAt();
    int getUserId();
    String getChannelName();
    String getPhotoUrl();
    default String getVideosPath(){
        if (getVideoUrl() == null)
            return null;
        return "/user-videos/" + getUserId() + "/" + getVideoUrl();
    };
    default String getPhotosImagePath(){
        if(getPhotoUrl() == null )
            return "/user-photos/defaultavatar.jpg";
        else
            return "/user-photos/" + getUserId() + "/" + getPhotoUrl();
    };

}
