package com.jingyuyao.shortener.factory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import redis.clients.jedis.Jedis;

@Data
public class JedisFactory {
    @NotEmpty
    @JsonProperty
    private String host;

    public Jedis build() {
        return new Jedis(host);
    }
}
