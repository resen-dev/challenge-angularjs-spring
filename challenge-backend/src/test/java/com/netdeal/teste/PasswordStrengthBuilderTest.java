package com.netdeal.teste;

import com.netdeal.teste.services.PasswordStrengthBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordStrengthBuilderTest {

    @Test
    public void testPasswordStrength(){

        String password = "Testa@12345";

        int expectedStrength = 101;

        int strength = new PasswordStrengthBuilder.Builder(password)
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
                .build();

        assertEquals(expectedStrength, strength, "A força da senha deve ser calculada corretamente");
    }
}
