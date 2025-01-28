package com.lec.spring.base.service;

import com.lec.spring.base.domain.Gym;
import com.lec.spring.base.domain.User;
import com.lec.spring.base.DTO.UserRegistrationDTO;
import com.lec.spring.base.repository.GymRepository;
import com.lec.spring.base.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GymRepository gymRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, GymRepository gymRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.gymRepository = gymRepository;
    }

    public User registerUser(UserRegistrationDTO registrationDTO, String role) {
        String email = registrationDTO.getEmail();
        String username = registrationDTO.getUsername();
        String password = passwordEncoder.encode(registrationDTO.getPassword());
        String nickname = registrationDTO.getNickname();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = registrationDTO.getBirth();
        String formatBirth = formatter.format(birth);
        String address = registrationDTO.getAddress();
        String gymName = registrationDTO.getGymName();
        Double latitude = registrationDTO.getLatitude();
        Double longitude = registrationDTO.getLongitude();

        // 회원가입 시 이미 존재하는 id 라면 회원가입 실패
        if (userRepository.existsByUsername(username)) {
            return null;
        }

        User user = User.builder()
                .email(email)
                .username(username.toUpperCase())
                .password(password)  // 이미 인코딩된 비밀번호 사용
                .nickname(nickname)
                .birth(birth)
                .address(address)
                .authority(role)  // 유저의 권한 설정
                .build();

        // 유저 정보를 우선적으로 저장
        user = userRepository.save(user);

        // trainer 회원가입 시 GYM 정보도 같이 저장
        if("ROLE_TRAINER".equals(role)) {
            Gym gym = Gym.builder()
                    .name(gymName)
                    .address(address)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
            gymRepository.save(gym);
            System.out.println("트레이너로 회원가입 되었습니다." + gym);
        }

       return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username.toUpperCase());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
