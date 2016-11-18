package com.jingyuyao.shortener.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = User.FIND_NAME,
                        query = "SELECT u FROM User u WHERE u.name = :name"
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
    public static final String FIND_NAME = "com.jingyuyao.shortener.core.User.findName";
    public static final String FIND_ID = "com.jingyuyao.shortner.core.User.findId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Length(max = 255)
    private String name;

    @Column(nullable = false)
    private String password;
}
