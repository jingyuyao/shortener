package com.jingyuyao.shortner.db;

import com.jingyuyao.shortner.core.Greeting;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

public class GreetingDAO extends AbstractDAO<Greeting> {
    @Inject
    public GreetingDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Greeting getOrCreate(String text) {
        Greeting greeting = uniqueResult(namedQuery(Greeting.FIND_TEXT).setParameter("text", text));
        if (Objects.nonNull(greeting)) {
            return greeting;
        }

        return createInitialGreeting(text);
    }

    public List<Greeting> findAll() {
        return list(namedQuery(Greeting.FIND_ALL));
    }

    public Greeting save(Greeting greeting) {
        return persist(greeting);
    }

    private Greeting createInitialGreeting(String text) {
        Greeting greeting = new Greeting();
        greeting.setText(text);
        greeting.setVisits(0);
        return greeting;
    }
}
