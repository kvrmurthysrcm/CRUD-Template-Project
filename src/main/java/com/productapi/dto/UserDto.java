package com.productapi.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    Long id;

    String userName;

    String password;

    String fullName;

    String email;

    String phone;

}
