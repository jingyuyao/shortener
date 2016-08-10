package com.jingyuyao.cms.resources;

import com.jingyuyao.cms.api.Greeting;
import com.jingyuyao.cms.core.GreetingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GreetingResourceTest {
    @Mock
    private GreetingService service;
    @Mock
    private Greeting greeting;

    private GreetingResource resource;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resource = new GreetingResource(service);
    }

    @Test
    public void getGreeting() {
        when(service.getGreeting()).thenReturn(greeting);

        Response response = resource.getGreeting();

        assertThat(response.getEntity()).isEqualTo(greeting);
    }
}
