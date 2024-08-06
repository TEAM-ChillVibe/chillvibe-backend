package com.chillvibe.chillvibe;

import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.global.s3.service.S3Uploader;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;
    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;
        private final S3Uploader s3Uploader;

        public void dbInit() {

            for (int i = 1; i <= 10; i++) {
                User user = User.createUser(
                        "유저" + i,
                        "user" + i + "@example.com",
                        "password" + i,
                        "http://example.com/profile"+i+".jpg",
                        "hi" + i
                );
                user.passwordEncode(bCryptPasswordEncoder);
                em.persist(user);
            }
        }
    }

}
