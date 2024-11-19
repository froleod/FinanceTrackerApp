package ru.froleod.FinanceTrackerApp.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.froleod.FinanceTrackerApp.model.User;
import ru.froleod.FinanceTrackerApp.repo.UserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class AppService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User " + user + " is saved!");
    }
}
