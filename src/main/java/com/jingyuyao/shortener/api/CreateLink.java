package com.jingyuyao.shortener.api;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the POST data sent by the client to create a new {@link com.jingyuyao.shortener.core.Link}
 */
@Data
@NoArgsConstructor
public class CreateLink {
    private String url;
}
