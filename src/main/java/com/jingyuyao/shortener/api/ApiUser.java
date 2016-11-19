package com.jingyuyao.shortener.api;

import com.jingyuyao.shortener.core.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiUser {
    private int id;
    private String name;

    public ApiUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
