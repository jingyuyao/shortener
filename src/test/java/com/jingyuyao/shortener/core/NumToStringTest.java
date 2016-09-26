package com.jingyuyao.shortener.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumToStringTest {
    @Test
    public void matches() {
        int num = 7923474;
        String encoded = NumToString.encode(num);
        int decoded = NumToString.decode(encoded);
        assertThat(num).isEqualTo(decoded);
    }
}
