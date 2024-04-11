package com.netdeal.teste.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordStrengthService {

    public int countStrength(String password) {
        List<PasswordStrengthRule> rules = List.of(
                new LettersOnlyDeduction(),
                new NumberOnlyDeduction(),
                new ConsecutiveUppercaseLettersDeduction(),
                new ConsecutiveLowercaseLettersDeduction(),
                new ConsecutiveNumberDeduction(),
                new SequentialLettersDeduction(),
                new SequentialNumbersDeduction(),
                new SequentialSymbolsDeduction(),
                new NumberCharactersAddition(),
                new UppercaseLettersAddition(),
                new LowercaseLettersAddition(),
                new NumbersAddition(),
                new SymbolsAddition(),
                new MiddleNumbersAndSymbolsAddition(),
                new ValidatorAddition()
    );

        return rules.stream()
                .mapToInt(rule -> rule.checkResults(password))
                .sum();
    }

    interface PasswordStrengthRule {
        int checkResults(String password);
    }

    static class LettersOnlyDeduction implements PasswordStrengthRule {
        @Override
        public int checkResults(String password) {
            return password.matches("[a-zA-Z]+") ? -password.length() : 0;
        }
    }

    static class NumberOnlyDeduction implements PasswordStrengthRule {
        @Override
        public int checkResults(String password) {
            return password.matches("[0-9]+") ? -password.length() : 0;
        }
    }

    static class ConsecutiveUppercaseLettersDeduction implements PasswordStrengthRule {
        @Override
        public int checkResults(String password) {
            int consecutiveUppercaseCount = 0;

            for (int i = 0; i < password.length() - 1; i++) {

                char currentChar = password.charAt(i);
                char nextChar = password.charAt(i + 1);

                if (Character.isUpperCase(currentChar) && Character.isUpperCase(nextChar)) {
                    consecutiveUppercaseCount++;
                }
            }

            return -consecutiveUppercaseCount * 2;
        }
    }

    static class ConsecutiveLowercaseLettersDeduction implements PasswordStrengthRule {
        @Override
        public int checkResults(String password) {
            int consecutiveLowercaseCount = 0;

            for (int i = 0; i < password.length() - 1; i++) {

                char currentChar = password.charAt(i);
                char nextChar = password.charAt(i + 1);

                if (Character.isLowerCase(currentChar) && Character.isLowerCase(nextChar)) {
                    consecutiveLowercaseCount++;
                }
            }

            return -consecutiveLowercaseCount * 2;
        }
    }

    static class ConsecutiveNumberDeduction implements PasswordStrengthRule {
        @Override
        public int checkResults(String password) {
            int consecutiveNumberCount = 0;

            for (int i = 0; i < password.length() - 1; i++) {

                char currentChar = password.charAt(i);
                char nextChar = password.charAt(i + 1);

                if (Character.isDigit(currentChar) && Character.isDigit(nextChar)) {
                    consecutiveNumberCount++;
                }
            }

            return -consecutiveNumberCount * 2;
        }
    }

    static class SequentialLettersDeduction implements PasswordStrengthRule {
        @Override
        public int checkResults(String password) {
            if(password.length() < 3) {
                return 0;
            }

            int count = 0;

            List<String> sequences = new ArrayList<>();

            for (int i = 0; i < password.length() - 2; i++) {
                char first = password.charAt(i);
                char second = password.charAt(i + 1);
                char third = password.charAt(i + 2);

                if(Character.isLetter(first) && Character.isLetter(second) && Character.isLetter(third) &&
                        first + 1 == second && second + 1 == third &&
                        !sequences.contains("" + first + second + third)){

                    sequences.add("" + first + second + third);
                    count++;
                }
            }

            return -count * 3;
        }
    }

    static class SequentialNumbersDeduction implements PasswordStrengthRule {
        @Override
        public int checkResults(String password) {
            if(password.length() < 3) {
                return 0;
            }

            int count = 0;

            List<String> sequences = new ArrayList<>();

            for (int i = 0; i < password.length() - 2; i++) {
                char first = password.charAt(i);
                char second = password.charAt(i + 1);
                char third = password.charAt(i + 2);

                if(Character.isDigit(first) && Character.isDigit(second) && Character.isDigit(third) &&
                        first + 1 == second && second + 1 == third &&
                        !sequences.contains("" + first + second + third)){

                    sequences.add("" + first + second + third);
                    count++;
                }
            }

            return -count * 3;
        }
    }

    static class SequentialSymbolsDeduction implements PasswordStrengthRule {
        @Override
        public int checkResults(String password) {
            if(password.length() < 3) {
                return 0;
            }

            int count = 0;

            List<String> sequences = new ArrayList<>();
            List<String> sequentialSymbols = new ArrayList<>(){{
                add("!@#");
                add("@#$");
                add("#$%");
                add("$%^");
                add("%^&");
                add("^&*");
                add("&*(");
            }};

            for (int i = 0; i < password.length() - 2; i++) {
                char first = password.charAt(i);
                char second = password.charAt(i + 1);
                char third = password.charAt(i + 2);

                if(sequentialSymbols.contains("" + first + second + third) && !sequences.contains("" + first + second + third)){
                    sequences.add("" + first + second + third);
                    count++;
                }
            }

            return -count * 3;
        }
    }

    static class NumberCharactersAddition implements PasswordStrengthRule{
        @Override
        public int checkResults(String password) {
            return password.length() * 4;
        }
    }

    static class UppercaseLettersAddition implements PasswordStrengthRule{
        @Override
        public int checkResults(String password) {
            long uppercase = password.chars().filter(Character::isUpperCase).count();

            return (int) ((password.length() - uppercase) * 2);
        }
    }

    static class LowercaseLettersAddition implements PasswordStrengthRule{
        @Override
        public int checkResults(String password) {
            long lowercase = password.chars().filter(Character::isLowerCase).count();

            return (int) ((password.length() - lowercase) * 2);
        }
    }

    static class NumbersAddition implements PasswordStrengthRule{
        @Override
        public int checkResults(String password) {
            long numbers = password.chars().filter(Character::isDigit).count();

            return password.chars().filter(Character::isLetter).count() == 0 ? 0 : (int) numbers * 4;
        }
    }

    static class SymbolsAddition implements PasswordStrengthRule{
        @Override
        public int checkResults(String password) {
            long symbols = password.chars().filter(c -> !Character.isLetterOrDigit(c)).count();

            return (int) symbols * 6;
        }
    }

    static class MiddleNumbersAndSymbolsAddition implements PasswordStrengthRule{
        @Override
        public int checkResults(String password) {

            if(password.length() > 2){
                String middle = password.substring(1, password.length() - 1);
                long numbers = middle.chars().filter(Character::isDigit).count();
                long symbols = middle.chars().filter(c -> !Character.isLetterOrDigit(c)).count();

                return (int) (numbers + symbols) * 2;
            }

            return 0;
        }
    }

    static class ValidatorAddition implements PasswordStrengthRule{
        @Override
        public int checkResults(String password) {
            return PasswordValidatorService.validatePasswordCount(password, 8) * 2;
        }
    }

    static class RepeatCharactersDeduction implements PasswordStrengthRule{
        //not implemented yet
        @Override
        public int checkResults(String password) {
            return 0;
        }
    }
}

