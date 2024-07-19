package com.petproject.youtubeclone;

import com.petproject.youtubeclone.models.User;
import com.petproject.youtubeclone.models.enums.ERole;
import com.petproject.youtubeclone.models.projections.ChannelProjection;
import com.petproject.youtubeclone.repositories.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setChannelName("giang23");
        user.setEmail("ging@gmail.com");
        user.setPassword("123456789");
        LocalDateTime lt = LocalDateTime.now();
        user.setRole(ERole.ROLE_USER);
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getUserId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }

    @Test
    public void  testFindUserByEmail() {
        String email="minhgiang1295@gmail.com";
        User user = repo.findByEmail(email);
        assertThat(user).isNotNull();
    }
    @Test
    public void  testGetChannelByName() {
        String channelname="minhgiangnguye";
        ChannelProjection channel = repo.getChannelByName(channelname);
        assertThat(channel).isNotNull();
        System.out.println(channel.getTotalVideo());
    }

    @Test
    public void  testGetRoleByUserId() {
        int userId=32;
        ERole role = repo.getRoleById(32);
        assertThat(role).isNotNull();
        System.out.println(role);
    }

    @Test
    public void  testAlterRoleByUserId() {
        int userId=32;
        ERole role = repo.getRoleById(32);
        assertThat(role).isNotNull();
        System.out.println(repo.getRoleById(32));
        if(role==ERole.ROLE_USER){
            repo.alterRole(ERole.ROLE_ADMIN,32);
        }else{
            repo.alterRole(ERole.ROLE_USER,32);
        }

        System.out.println(repo.getRoleById(32));
    }

    @Test
    public void  testExistEmail() {
        String email="minhgiang125@gmail.com";
        boolean boo = repo.checkExistEmail(email);
        assertThat(boo).isEqualTo(true);

    }
}
