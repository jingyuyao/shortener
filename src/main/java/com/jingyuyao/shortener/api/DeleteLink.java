package com.jingyuyao.shortener.api;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the data sent by client to delete a {@link com.jingyuyao.shortener.core.Link}
 */
@Data
@NoArgsConstructor
public class DeleteLink {
    /**
     * The {@link com.jingyuyao.shortener.core.Link#id} to delete
     */
    private String id;
}
