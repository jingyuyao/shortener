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

    public void delete(Link link) {
        currentSession().delete(link);
    }

    /**
     * Retrieves a {@link Link} using its ID.
     * @param id the ID of the link to retrieve
     * @return the {@link Link} with the given ID
     */
    public Optional<Link> getById(int id) {
        Link link = uniqueResult(namedQuery(Link.FIND_ID).setParameter("id", id));
        return Optional.ofNullable(link);
    }
}
