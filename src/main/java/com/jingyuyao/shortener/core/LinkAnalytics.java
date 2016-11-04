package com.jingyuyao.shortener.core;

import com.jingyuyao.shortener.db.LinkDAO;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Optional;

public class LinkAnalytics {
    private final LinkDAO dao;

    public LinkAnalytics(LinkDAO dao) {
        this.dao = dao;
    }

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
