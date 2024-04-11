package com.netdeal.teste.services;

import java.util.ArrayList;
import java.util.List;

public class PasswordStrengthBuilder {

    public static class Builder {

        private int strength = 0;
        private final String password;

        public Builder(String password){
            this.password = password;
        }

        public Builder LettersOnlyDeduction() {
            strength -= password.matches("[a-zA-Z]+") ? password.length() : 0;
            return this;
        }

        public Builder NumberOnlyDeduction() {
            strength -= password.matches("[0-9]+") ? password.length() : 0;
            return this;
        }

        public Builder ConsecutiveUppercaseLettersDeduction() {

            int consecutiveUppercaseCount = 0;

            for (int i = 0; i < password.length() - 1; i++) {

                char currentChar = password.charAt(i);
                char nextChar = password.charAt(i + 1);

                if (Character.isUpperCase(currentChar) && Character.isUpperCase(nextChar)) {
                    consecutiveUppercaseCount++;
                }
            }

            strength -= consecutiveUppercaseCount * 2;
            return this;
        }

        public Builder ConsecutiveLowercaseLettersDeduction() {

            int consecutiveLowercaseCount = 0;

            for (int i = 0; i < password.length() - 1; i++) {

                char currentChar = password.charAt(i);
                char nextChar = password.charAt(i + 1);

                if (Character.isLowerCase(currentChar) && Character.isLowerCase(nextChar)) {
                    consecutiveLowercaseCount++;
                }
            }

            strength -= consecutiveLowercaseCount * 2;
            return this;
        }

        public Builder ConsecutiveNumberDeduction() {
            int consecutiveNumberCount = 0;

            for (int i = 0; i < password.length() - 1; i++) {

                char currentChar = password.charAt(i);
                char nextChar = password.charAt(i + 1);

                if (Character.isDigit(currentChar) && Character.isDigit(nextChar)) {
                    consecutiveNumberCount++;
                }
            }
            strength -= (consecutiveNumberCount * 2);
            return this;
        }

        public Builder SequentialLettersDeduction() {

            if(password.length() < 3) {
                return this;
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

            strength -= count * 3;
            return this;
        }

        public Builder SequentialNumbersDeduction() {

            if(password.length() < 3) {
                return this;
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

            strength -= count * 3;
            return this;
        }

        public Builder SequentialSymbolsDeduction() {

            if(password.length() < 3) {
                return this;
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

            strength -= count * 3;
            return this;
        }

        public Builder NumberCharactersAddition() {
            strength += password.length() * 4;
            return this;
        }

        public Builder UppercaseLettersAddition() {
            long uppercase = password.chars().filter(Character::isUpperCase).count();
            strength += (int) ((password.length() - uppercase) * 2);
            return this;
        }

        public Builder LowercaseLettersAddition() {
            long lowercase = password.chars().filter(Character::isLowerCase).count();
            strength += (int) ((password.length() - lowercase) * 2);
            return this;
        }

        public Builder NumbersAddition() {
            long numbers = password.chars().filter(Character::isDigit).count();
            strength += password.chars().filter(Character::isLetter).count() == 0 ? 0 : (int) numbers * 4;
            return this;
        }

        public Builder SymbolsAddition() {
            long symbols = password.chars().filter(c -> !Character.isLetterOrDigit(c)).count();
            strength += (int) (symbols * 6);
            return this;
        }

        public Builder MiddleNumbersAndSymbolsAddition() {
            if(password.length() > 2){
                String middle = password.substring(1, password.length() - 1);
                long numbers = middle.chars().filter(Character::isDigit).count();
                long symbols = middle.chars().filter(c -> !Character.isLetterOrDigit(c)).count();

                strength += (int) (numbers + symbols) * 2;
            }
            return this;
        }

        public Builder ValidatorAddition(){
            strength += PasswordValidatorService.validatePasswordCount(password, 8) * 2;
            return this;
        }

        public Builder RepeatCharactersDeduction(){
            //not implemented yetreturn this;
            return this;
        }

        public int build() {
            return strength;
        }
    }
}
