package com.jingyuyao.shortener.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

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
                    name = Link.FIND_ALL,
                    query = "SELECT l FROM Link l"
            ),
            @NamedQuery(
                    name = Link.FIND_ID,
                    query = "SELECT l FROM Link l WHERE l.id = :id"
            )
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "link")
public class Link {
    public static final String FIND_ALL = "com.jingyuyao.shortener.core.Link.findAll";
    public static final String FIND_ID = "com.jingyuyao.shortener.core.Link.findId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Length(max = 50)
    @NotBlank
    @URL
    private String url;

    // TODO: Move this out of the link table when we track more stuff
    @Column(nullable = false)
    private int visits;
}
