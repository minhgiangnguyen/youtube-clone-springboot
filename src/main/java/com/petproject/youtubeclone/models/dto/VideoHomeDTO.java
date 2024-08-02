package com.petproject.youtubeclone.models.dto;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

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
//        int dayMonth = createAt.getDayOfMonth();
//        int dayYear = createAt.getDayOfYear();
//        int month = createAt.getMonthValue();
//        int year = createAt.getYear();
        Period period = Period.between(createAt.toLocalDate(),now.toLocalDate());
        int yearAgo = period.getYears();
        int monthAgo = period.getMonths();
        int dayAgo = period.getDays();
//        int hourAgo = duration.toHours();



        if(yearAgo>0){
            return yearAgo==1 ?Integer.toString(yearAgo).replace("-","")+" year ago":
                    Integer.toString(yearAgo).replace("-","")+" years ago";
        }else if(monthAgo>0){
            return monthAgo==1 ?Integer.toString(monthAgo).replace("-","")+" month ago":
                    Integer.toString(monthAgo).replace("-","")+" months ago";
        }else if(dayAgo>0){
            int weekAgo = (int) Math.floor((double) dayAgo /7) ;
            if(weekAgo<=1) {
                return dayAgo==1 ?Integer.toString(dayAgo).replace("-","")+" day ago":
                        Integer.toString(dayAgo).replace("-","")+" days ago";
            }else {
                return Integer.toString(weekAgo).replace("-","")+" weeks ago";
            }
        }else {
            Duration duration = Duration.between(createAt, now);
            int hourAgo=(int) duration.toHours();
            int minuteAgo=(int)duration.toMinutes();
//                int hourAgo = now.getHour()-hour;
//                int minuteAgo = 60-minute-now.getMinute();
//                if(hourAgo==0){
//                    int minuteAgo = now.getMinute()-minute;
//                    return minuteAgo==1 ?Integer.toString(minuteAgo).replace("-","")+" minute ago":
//                            Integer.toString(minuteAgo).replace("-","")+" minutes ago";
//                }
//                if(hourAgo==1 && minute>now.getMinute()){
//                    int minuteAgo = 60-minute-now.getMinute();
//                    return minuteAgo==1 ?Integer.toString(minuteAgo).replace("-","")+" minute ago":
//                            Integer.toString(minuteAgo).replace("-","")+" minutes ago";
//                }
//                if(hourAgo>=1 && minute<=now.getMinute()){
//                    return hourAgo==1 ?Integer.toString(hourAgo).replace("-","")+" hour ago":
//                            Integer.toString(hourAgo).replace("-","")+" hours ago";
//                }
            if(hourAgo>0){
                return hourAgo==1 ?Integer.toString(hourAgo).replace("-","")+" hour ago":
                            Integer.toString(hourAgo).replace("-","")+" hours ago";
            }
            if(minuteAgo>0){
                return minuteAgo==1 ?Integer.toString(minuteAgo).replace("-","")+" minute ago":
                            Integer.toString(minuteAgo).replace("-","")+" minutes ago";
            }

            return "a moment ago";
        }

    }

}
