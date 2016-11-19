package com.jingyuyao.shortener.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = User.FIND_ALL,
                        query = "SELECT u FROM User u"
                ),
                @NamedQuery(
                        name = User.FIND_ID,
                        query = "SELECT u FROM User u WHERE u.id = :id"
                )
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    public static final String FIND_ALL = "com.jingyuyao.shortener.core.User.findAll";
    public static final String FIND_ID = "com.jingyuyao.shortner.core.User.findId";

    /**
     * The internal ID for this application
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * ID from external sources such as Google auth
     */
    @Column(nullable = false, name = "external_id")
    private int externalId;

    /**
     * Where this account originated from. i.e Google, Facebook...
     */
    @Column(nullable = false, name = "external_source")
    private String externalSource;

    @Column(nullable = false)
    private String email;

    @Column
    private String name;
}
