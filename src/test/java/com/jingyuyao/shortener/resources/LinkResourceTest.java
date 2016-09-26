package com.jingyuyao.shortener.resources;

import com.google.common.collect.ImmutableList;
import com.jingyuyao.shortener.api.CreateLink;
import com.jingyuyao.shortener.api.ShortenedLink;
import com.jingyuyao.shortener.core.Link;
import com.jingyuyao.shortener.core.NumToString;
import com.jingyuyao.shortener.db.LinkDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;
import javax.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// TODO: Test failure cases
public class LinkResourceTest {
    private static final String URL = "http://www.example.org";
    private static final int ID = 1;
    private static final String ENCODED_ID = NumToString.encode(ID);
    private static final Link DUMMY_LINK = new Link(1, "http://www.example.org", 0);
    private static final List<Link> LINKS = ImmutableList.of(DUMMY_LINK);

    @Mock
    private Validator validator;
    @Mock
    private LinkDAO dao;
    @Mock
    private CreateLink createLink;
    @Captor
    private ArgumentCaptor<Link> linkCaptor;

    private LinkResource resource;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(validator.validate(any(Link.class))).thenReturn(Collections.emptySet());

        resource = new LinkResource(validator, dao);
    }

    @Test
    public void getLinks() {
        when(dao.findAll()).thenReturn(LINKS);

        Response response = resource.getLinks();
        List<ShortenedLink> links = (List<ShortenedLink>) response.getEntity();

        assertThat(links.size()).isEqualTo(1);
        assertThat(links.get(0).getUrl()).isEqualTo(DUMMY_LINK.getUrl());
    }

    @Test
    public void createLink() {
        when(createLink.getUrl()).thenReturn(URL);
        when(dao.save(any())).then(returnsFirstArg());

        Response response = resource.createLink(createLink);

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao).save(linkCaptor.capture());
        assertThat(linkCaptor.getValue().getUrl()).isEqualTo(URL);
        assertThat(linkCaptor.getValue().getVisits()).isZero();
    }

    @Test
    public void redirect() {
        when(dao.getById(ID)).thenReturn(Optional.of(DUMMY_LINK));

        Response response = resource.redirect(ENCODED_ID);

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.TEMPORARY_REDIRECT);
        assertThat(response.getLocation().toString()).isEqualTo(DUMMY_LINK.getUrl());
    }
}
