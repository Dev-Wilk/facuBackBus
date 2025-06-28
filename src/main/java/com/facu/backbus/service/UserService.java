package com.facu.backbus.service;

import com.facu.backbus.model.User;
import com.facu.backbus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        validatePassword(user.getPassword());
        String hashedPassword = com.facu.backbus.util.HashUtil.gerarHashSHA256(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    /**
     * Valida se a senha atende aos requisitos de segurança
     * (pelo menos um número e um caractere especial)
     * 
     * @param password Senha a ser validada
     * @throws IllegalArgumentException Se a senha não atender aos requisitos
     */
    private void validatePassword(String password) {
        if (password == null || !password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*(),.?:{}|<>]).*$")) {
            throw new IllegalArgumentException("A senha deve conter pelo menos um número e um caractere especial");
        }
    }

    

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
