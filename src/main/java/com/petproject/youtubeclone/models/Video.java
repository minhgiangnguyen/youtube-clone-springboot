package com.petproject.youtubeclone.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "videos")
public class Video {
    @Id
    @Column(name = "video_id")
    private String videoId;

    @NotBlank
    @Size(min = 3, max=250)
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail")
//    @NotBlank(message = "Thumbnail have not bean upload yet")
    private String thumbnail;
    @Column(name = "video_url")
//    @NotBlank(message = "Video have not bean upload yet")
    private String videoUrl;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name="user_id",insertable = false,updatable = false)
    private User user;

    @Transient
    public String getVideosPath() {

        if (videoUrl == null || userId == 0)
            return null;
        return "/user-videos/" + userId + "/" + videoUrl;
    }
    @Transient
    public String getThumbsPath() {

        if (thumbnail == null || userId == 0)
            return null;
        return "/user-videos/" + userId + "/" + thumbnail;
    }
}
