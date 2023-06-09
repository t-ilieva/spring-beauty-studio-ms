package spring.ms.com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    public Optional<UserDTO> getById(int id){
        return userRepository
                .findById(id)
                .map(UserTransformer::toUserDTO);
    }

    public int create(UserDTO userDTO){
        User user = UserTransformer.toUserEntity(userDTO);

        user.setPassword(passwordEncode.encode(userDTO.getPassword()));
        user.setRole("ROLE_USER");

        return userRepository.save(user).getId();
    }


    public boolean checkEmail(String email){
        return userRepository.existsByEmail(email);
    }

}
