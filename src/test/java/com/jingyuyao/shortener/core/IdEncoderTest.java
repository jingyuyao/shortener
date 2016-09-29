package com.jingyuyao.shortener.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IdEncoderTest {
    @Test
    public void matches() {
        int num = 7923474;
        String encoded = IdEncoder.encode(num);
        int decoded = IdEncoder.decode(encoded);
        assertThat(num).isEqualTo(decoded);
    }
}
