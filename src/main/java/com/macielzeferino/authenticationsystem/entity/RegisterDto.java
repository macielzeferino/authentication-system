package com.macielzeferino.authenticationsystem.entity;

public record RegisterDto(String userLogin, String userPassword, UserRole userRole) {
}
