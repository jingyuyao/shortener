package com.jingyuyao.shortener.api;

import com.jingyuyao.shortener.core.Link;
import com.jingyuyao.shortener.core.NumToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Api representation of a shortened {@link com.jingyuyao.shortener.core.Link}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortenedLink {
    private String id;
    private String url;
    private int visits;

    public ShortenedLink(Link link) {
        this.id = NumToString.encode(link.getId());
        this.url = link.getUrl();
        this.visits = link.getVisits();
    }
}
