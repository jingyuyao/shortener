package com.jingyuyao.shortener.resources;

import com.jingyuyao.shortener.api.ApiError;
import com.jingyuyao.shortener.api.ApiUser;
import com.jingyuyao.shortener.api.CreateUser;
import com.jingyuyao.shortener.core.User;
import com.jingyuyao.shortener.db.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.HibernateException;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/u")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserDAO dao;
    private Validator validator;

    @Inject
    public UserResource(UserDAO dao, Validator validator) {
        this.dao = dao;
        this.validator = validator;
    }

    @GET
    @UnitOfWork
    public Response getUsers() {
        List<ApiUser> users =
                dao.findAll().stream().map(ApiUser::new).collect(Collectors.toList());
        return Response.ok(users).build();
    }

    @POST
    @UnitOfWork
    public Response createUser(CreateUser createUser) {
        User user = new User();
        user.setEmail(createUser.getEmail());
        user.setExternalId(createUser.getExternalId());
        user.setExternalSource(createUser.getExternalSource());
        user.setName(createUser.getName());

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (violations.isEmpty()) {
            try {
                user = dao.save(user);
                return Response.ok(new ApiUser(user)).build();
            } catch (HibernateException e) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ApiError.create(violations))
                    .build();
        }
    }
}
