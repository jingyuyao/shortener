package com.jingyuyao.shortener.core;

import com.jingyuyao.shortener.db.LinkDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AnalyticsProcessorTest {
    @Mock
    private LinkDAO dao;
    @Captor
    ArgumentCaptor<Link> linkCaptor;

    private AnalyticsProcessor analyticsProcessor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        analyticsProcessor = new AnalyticsProcessor(dao);
    }

    @Test
    public void visited() {
        Link link = new Link(0 , "http://www.example.org", 0);
        when(dao.getById(0)).thenReturn(Optional.of(link));

        analyticsProcessor.visited(0);

        verify(dao).save(linkCaptor.capture());
        assertThat(linkCaptor.getValue().getVisits()).isEqualTo(1);
    }

    @Test
    public void process() {
        Link link = new Link(0 , "http://www.example.org", 0);

        analyticsProcessor.process(link);

        assertThat(link.getVisits()).isEqualTo(1);
    }
}
