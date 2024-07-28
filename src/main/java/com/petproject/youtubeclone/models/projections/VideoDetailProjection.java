package com.petproject.youtubeclone.models.projections;

import java.time.LocalDateTime;

public interface VideoDetailProjection {
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
