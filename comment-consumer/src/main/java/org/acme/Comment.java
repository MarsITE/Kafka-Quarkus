package org.acme;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends PanacheEntity {

    String postId;

    String comment;

    @Enumerated(EnumType.STRING)
    Type type;

    static Comment verified(String id, String comment) {
        return new Comment(id, comment, Type.VERIFIED);
    }

    static Comment blackListed(String id, String comment) {
        return new Comment(id, comment, Type.BLACKLISTED);
    }

    enum Type {
        VERIFIED, BLACKLISTED
    }
}
