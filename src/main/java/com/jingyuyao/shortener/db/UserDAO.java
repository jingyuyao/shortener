package com.jingyuyao.shortener.db;

import com.jingyuyao.shortener.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {
    @Inject
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> findAll() {
        return list(namedQuery(User.FIND_ALL));
    }

    public User save(User user) {
        return persist(user);
    }

    public void delete(User user) {
        currentSession().delete(user);
    }

    public Optional<User> getById(int id) {
        return Optional.ofNullable(uniqueResult(namedQuery(User.FIND_ID).setParameter("id", id)));
    }

    public Optional<User> getByExternalId(String id) {
        return Optional.ofNullable(uniqueResult(namedQuery(User.FIND_EXTERNAL_ID).setParameter("externalId", id)));
    }
}
