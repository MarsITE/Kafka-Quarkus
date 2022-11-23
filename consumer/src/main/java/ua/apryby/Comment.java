package ua.apryby;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Cacheable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends PanacheEntity {

    @NotNull
    String postId;

    @NotNull
    @Size(min = 20, max = 1024)
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
