package com.jingyuyao.shortener.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUser {
    private String name;
    private String password;
}
