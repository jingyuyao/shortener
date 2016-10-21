package com.jingyuyao.shortener.core;

import com.jingyuyao.shortener.db.LinkDAO;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Optional;

public class AnalyticsProcessor {
    private final LinkDAO dao;

    public AnalyticsProcessor(LinkDAO dao) {
        this.dao = dao;
    }

    @UnitOfWork
    public void visited(int id) {
        Optional<Link> optionalLink = dao.getById(id);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            process(link);
            dao.save(link);
        }
    }

    public void process(Link link) {
        link.setVisits(link.getVisits() + 1);
    }
}
