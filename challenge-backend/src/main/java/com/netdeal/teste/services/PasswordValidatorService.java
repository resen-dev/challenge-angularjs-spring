package com.netdeal.teste.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class PasswordValidatorService {

    public boolean validatePassword(String password, int minimumRequiredRules, int minimumRequiredLength) {
        List<PasswordValidationRule> rules = List.of(
                new SymbolsRule(),
                new UppercaseRule(),
                new LowercaseRule(),
                new NumbersRule()
        );

        long validRuleCount = rules.stream()
                .filter(rule -> rule.isValid(password))
                .count();

        return validRuleCount >= minimumRequiredRules && password.length() >= minimumRequiredLength;
    }

    public static int validatePasswordCount(String password, int minimumRequiredLength) {
        List<PasswordValidationRule> rules = List.of(
                new SymbolsRule(),
                new UppercaseRule(),
                new LowercaseRule(),
                new NumbersRule()
        );

        long validRuleCount = rules.stream()
                .filter(rule -> rule.isValid(password))
                .count();

        if(password.length() <= minimumRequiredLength || validRuleCount < 3){
            return 0;
        }

        return (int) validRuleCount + 1;
    }

    interface PasswordValidationRule {
        boolean isValid(String password);
    }

    static class SymbolsRule implements PasswordValidationRule {
        @Override
        public boolean isValid(String password) {
            Pattern pattern = Pattern.compile("[!@#$%^&*()-+]");
            return pattern.matcher(password).find();
        }
    }

    static class UppercaseRule implements PasswordValidationRule {
        @Override
        public boolean isValid(String password) {
            return !password.equals(password.toLowerCase());
        }
    }

    static class LowercaseRule implements PasswordValidationRule {
        @Override
        public boolean isValid(String password) {
            return !password.equals(password.toUpperCase());
        }
    }

    static class NumbersRule implements PasswordValidationRule {
        @Override
        public boolean isValid(String password) {
            return password.chars().anyMatch(Character::isDigit);
        }
    }
}
