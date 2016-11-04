package com.jingyuyao.shortener.core;

import com.jingyuyao.shortener.db.LinkDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;

import java.util.Optional;

/**
 * Performs analytics when a {@link Link} is visited. Instance of this class should
 * be created from {@link UnitOfWorkAwareProxyFactory} so a Hibernate session can be
 * created outside of a resource.
 */
public class LinkAnalytics {
    private final LinkDAO dao;

    LinkAnalytics(LinkDAO dao) {
        this.dao = dao;
    }

    /**
     * Performs analytics on a link with the given {@code id}.
     * @param id the ID of the {@link Link} that is visited
     * @return the visited link if the id is valid
     */
    @UnitOfWork
    public Optional<Link> visit(int id) {
        Optional<Link> optionalLink = dao.getById(id);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            link.setVisits(link.getVisits() + 1);
            dao.save(link);
        }
        return optionalLink;
    }
}
