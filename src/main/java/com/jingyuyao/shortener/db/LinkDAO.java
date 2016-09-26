package com.jingyuyao.shortener.db;

import com.jingyuyao.shortener.core.Link;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class LinkDAO extends AbstractDAO<Link> {
    @Inject
    public LinkDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Link> findAll() {
        return list(namedQuery(Link.FIND_ALL));
    }

    public Link save(Link link) {
        return persist(link);
    }

    /**
     * Retrieves a {@link Link} using its ID.
     * @param id the ID of the link to retrieve
     * @return the {@link Link} with the given ID
     */
    public Optional<Link> getById(long id) {
        Link link = uniqueResult(namedQuery(Link.FIND_ID).setParameter("id", id));
        return Optional.ofNullable(link);
    }

    /**
     * Creates a {@link Link} from the given {@code url}. This method does not save the {@link Link} in the database.
     * @param url the url for this link
     * @return the created {@link Link}
     */
    public Link createLink(String url) {
        Link link = new Link();
        link.setUrl(url);
        link.setVisits(0);
        return link;
    }
}
