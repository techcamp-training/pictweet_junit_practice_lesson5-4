package in.tech_camp.pictweet.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.tech_camp.pictweet.custom_user.CustomUserDetail;
import in.tech_camp.pictweet.entity.UserEntity;
import in.tech_camp.pictweet.repository.UserRepository;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserAuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new CustomUserDetail(userEntity);
    }
}
