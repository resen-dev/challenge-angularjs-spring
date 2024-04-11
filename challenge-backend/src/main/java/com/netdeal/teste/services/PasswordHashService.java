package com.netdeal.teste.services;

import com.netdeal.teste.models.PasswordHashModel;
import com.netdeal.teste.models.UserModel;
import com.netdeal.teste.repositories.PasswordHashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordHashService {

    @Autowired
    PasswordHashRepository passwordHashRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    PasswordValidatorService passwordValidatorService;

    public void savePassword(UserModel user, String password) {

        if(!passwordValidatorService.validatePassword(password, 3, 8)){
            throw new RuntimeException("Senha nao cumpre os requisitos");
        }

        PasswordHashModel passwordHashModel = new PasswordHashModel();

        passwordHashModel.setHash(encryptPassword(password));
        passwordHashModel.setUser(user);

        passwordHashRepository.save(passwordHashModel);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
