package com.netdeal.teste.services;

import com.netdeal.teste.dto.CreateUserDto;
import com.netdeal.teste.models.UserModel;
import com.netdeal.teste.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordHashService passwordHashService;

    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public UserModel createUser(CreateUserDto createUserDto) {
        UserModel newUser = new UserModel();

        newUser.setName(createUserDto.name());

        if (createUserDto.idSuperior() != null) {

            newUser.setSuperior(
                    userRepository.findById(createUserDto.idSuperior()).orElseThrow(EntityNotFoundException::new)
            );

            newUser.getSuperior().addQtdSubordinate(BigInteger.ONE);
        }

        newUser.setPasswordStrength(
                new PasswordStrengthBuilder.Builder(createUserDto.password())
                        .NumberCharactersAddition()
                        .UppercaseLettersAddition()
                        .LowercaseLettersAddition()
                        .NumbersAddition()
                        .SymbolsAddition()
                        .MiddleNumbersAndSymbolsAddition()
                        .ValidatorAddition()
                        .LettersOnlyDeduction()
                        .NumberOnlyDeduction()

                        .ConsecutiveUppercaseLettersDeduction()
                        .ConsecutiveLowercaseLettersDeduction()
                        .ConsecutiveNumberDeduction()
                        .SequentialLettersDeduction()
                        .SequentialNumbersDeduction()
                        .SequentialSymbolsDeduction()
                        .build()
        );

        userRepository.save(newUser);

        passwordHashService.savePassword(newUser, createUserDto.password());

        return newUser;
    }

    @Transactional
    public UserModel updateUser(Long id, UserModel updatedUser) {
        UserModel userToUpdate = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        userToUpdate.setName(updatedUser.getName());

        return userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public Set<UserModel> findByNameContaining(String name) {
        return name.isBlank() ? new HashSet<>() : userRepository.findByNameContaining(name);
    }

    public Set<UserModel> getSuperiors() {
        return userRepository.getSuperiors();
    }

    public Set<UserModel> getSubordinates(Long id) {
        return userRepository.getSubordinates(id);
    }
}
