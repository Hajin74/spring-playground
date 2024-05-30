package org.example.springsecurity.service;

import org.example.springsecurity.dto.JoinDto;
import org.example.springsecurity.entity.UserEntity;
import org.example.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDto joinDto) {
        // todo: db에 동일한 username을 가진 회원이 존재하는지 검증
        boolean isUser = userRepository.existsByUsername(joinDto.getUsername());
        if(isUser) {
            return;
        }

        // todo: 가입 불가 문자 정규식 처리

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(joinDto.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        userEntity.setRole("ROLE_ADMIN");

        userRepository.save(userEntity);
    }

}
