package com.jingyuyao.cms.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Greeting {
    private String text;
    private long visits;
}
