package ua.apryby;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public final class Comment {

    @NotNull
    private String postId;

    @NotNull
    @Size(min = 20, max = 1024)
    private String comment;
}
