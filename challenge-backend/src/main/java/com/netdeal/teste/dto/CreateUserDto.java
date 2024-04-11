package com.netdeal.teste.dto;

public record CreateUserDto(
    Long idSuperior,
    String name,
    String password
) {
}
