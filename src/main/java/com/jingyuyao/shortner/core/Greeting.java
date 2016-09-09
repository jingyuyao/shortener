package com.jingyuyao.shortner.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Data
@Entity
@NamedQueries(
        {
            @NamedQuery(
                    name = Greeting.FIND_ALL,
                    query = "SELECT g FROM Greeting g"
            ),
            @NamedQuery(
                    name = Greeting.FIND_TEXT,
                    query = "SELECT g FROM Greeting g WHERE g.text = :text"
            )
        }
)
@NoArgsConstructor
@Table(name = "greeting")
public class Greeting {
    public static final String FIND_ALL = "com.jingyuyao.shortner.core.Greeting.findAll";
    public static final String FIND_TEXT = "com.jingyuyao.shortner.core.Greeting.findText";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Length(max = 50)
    private String text;

    @Column(nullable = false)
    private long visits;
}
