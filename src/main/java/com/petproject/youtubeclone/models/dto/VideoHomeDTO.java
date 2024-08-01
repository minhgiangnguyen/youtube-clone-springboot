package com.petproject.youtubeclone.models.dto;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoHomeDTO {
    private String videoId;
    private String title;
    private String thumbnail;
    private int userId;
    private LocalDateTime createAt;
    private String channelName;
    private String photoUrl;
    private String photosImagePath;
    private String thumbsPath;
    public VideoHomeDTO(String videoId, String title, String thumbnail, int userId,LocalDateTime createAt,
                        String channelName, String photoUrl) {
        this.videoId = videoId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.userId = userId;
        this.createAt = createAt;
        this.channelName = channelName;
        this.photoUrl = photoUrl;
        this.photosImagePath = (photoUrl == null) ? "/user-photos/defaultavatar.jpg" : "/user-photos/" + userId + "/" + photoUrl;
        this.thumbsPath = "/user-videos/" + userId + "/" + thumbnail;
    }
    @Transient
    public String getTimeAgo() {
        LocalDateTime now= LocalDateTime.now();
        int minute = createAt.getMinute();
        int hour = createAt.getHour();
        int day = createAt.getDayOfMonth();
        int month = createAt.getMonthValue();
        int year = createAt.getYear();
        if(year!=now.getYear()){
            int yearAgo = year-now.getYear();
            return yearAgo==1 ?Integer.toString(yearAgo).replace("-","")+" year ago":
                    Integer.toString(yearAgo).replace("-","")+" years ago";
        }else if(month!=now.getMonthValue()){
            int monthAgo = month-now.getMonthValue();
            return monthAgo==1 ?Integer.toString(monthAgo).replace("-","")+" month ago":
                    Integer.toString(monthAgo).replace("-","")+" months ago";
        }else if(day!=now.getDayOfMonth()){
            int dayAgo = day-now.getDayOfMonth();
            return dayAgo==1 ?Integer.toString(dayAgo).replace("-","")+" day ago":
                    Integer.toString(dayAgo).replace("-","")+" days ago";
        }else if(hour!=now.getHour()){
            int hourAgo = hour-now.getHour();
            return hourAgo==1 ?Integer.toString(hourAgo).replace("-","")+" hour ago":
                    Integer.toString(hourAgo).replace("-","")+" hours ago";
        }else if(minute!=now.getMinute()){
            int minuteAgo = minute-now.getMinute();
            return minuteAgo==1 ?Integer.toString(minuteAgo).replace("-","")+" minute ago":
                    Integer.toString(minuteAgo).replace("-","")+" minutes ago";
        }else
            return "a moment ago";


    }

}
