package ru.froleod.FinanceTrackerApp.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.froleod.FinanceTrackerApp.config.MyUserDetails;
import ru.froleod.FinanceTrackerApp.model.User;
import ru.froleod.FinanceTrackerApp.repo.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return new MyUserDetails(user);
    }
}
