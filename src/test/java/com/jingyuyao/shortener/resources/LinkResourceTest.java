package com.jingyuyao.shortener.resources;

import com.jingyuyao.shortener.api.CreateLink;
import com.jingyuyao.shortener.core.Link;
import com.jingyuyao.shortener.db.LinkDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LinkResourceTest {
    private static final String URL = "http://www.example.org";

    @Mock
    private LinkDAO dao;
    @Mock
    private List<Link> links;
    @Mock
    private CreateLink createLink;
    @Mock
    private Link dummyLink;
    @Captor
    private ArgumentCaptor<Link> linkCaptor;

    private LinkResource resource;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resource = new LinkResource(dao);
    }

    @Test
    public void getLinks() {
        when(dao.findAll()).thenReturn(links);

        Response response = resource.getLinks();

        assertThat(response.getEntity()).isEqualTo(links);
    }

    @Test
    public void createLink() {
        when(createLink.getUrl()).thenReturn(URL);
        when(dao.createLink(URL)).thenReturn(dummyLink);
        when(dao.save(dummyLink)).thenReturn(dummyLink);

        Response response = resource.createLink(createLink);

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao).save(linkCaptor.capture());
        assertThat(linkCaptor.getValue()).isEqualTo(dummyLink);
    }
}
