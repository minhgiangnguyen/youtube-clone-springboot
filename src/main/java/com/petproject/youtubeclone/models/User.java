package com.petproject.youtubeclone.models;

import com.petproject.youtubeclone.models.enums.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "Users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "channel_name"),
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @NotBlank
    @Size(min = 3, max=60)
    @Column(name = "channel_name")
    private String channelName;
    @NotBlank
//    @Size(min = 8, max = 15)
    @Column(name = "password")
    private String password;
//    @NotBlank

    @Email(message = "Please enter a valid e-mail address")
    @Size(min = 3, max=60)
    @Column(name = "email")
    private String email;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ERole role;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Video> videos;

    @Transient
    public String getPhotosImagePath() {

//        if (photoUrl == null || userId == 0)
//            return null;
        if(photoUrl == null )
            return "/user-photos/defaultavatar.jpg";
        else
            return "/user-photos/" + userId + "/" + photoUrl;
    }
}

