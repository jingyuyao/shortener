package com.jingyuyao.cms.resources;

import com.jingyuyao.cms.core.Greeting;
import com.jingyuyao.cms.db.GreetingDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GreetingResourceTest {
    private static final String TEXT = "lalalala";

    @Mock
    private GreetingDAO dao;
    @Mock
    private List<Greeting> greetings;

    private GreetingResource resource;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resource = new GreetingResource(dao);
    }

    @Test
    public void getGreetings() {
        when(dao.findAll()).thenReturn(greetings);

        Response response = resource.getGreetings();

        assertThat(response.getEntity()).isEqualTo(greetings);
    }

    @Test
    public void getGreeting() {
        Greeting dummy = new Greeting();
        dummy.setText(TEXT);
        dummy.setVisits(0);
        when(dao.getOrCreate(TEXT)).thenReturn(dummy);
        when(dao.save(dummy)).thenReturn(dummy);

        Response response = resource.getGreeting(TEXT);

        assertThat(dummy.getVisits()).isEqualTo(1);
        assertThat(response.getEntity()).isEqualTo(dummy);
    }
}
