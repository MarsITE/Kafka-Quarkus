package ua.apryby;

import io.smallrye.common.annotation.NonBlocking;
import org.jboss.resteasy.reactive.RestPath;
import ua.apryby.dto.Comment;
import ua.apryby.dto.CommentMessage;
import ua.apryby.gateway.CommentProducer;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/comments")
public class CommentRestResource {

    @Inject
    CommentProducer commentProducer;

    @POST
    @Path("/{postId}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @NonBlocking
    public void postCommentWithPostId(@RestPath @NotNull String postId,
                                      @Valid CommentMessage comment) {
        commentProducer.send(createComment(postId, comment));
    }

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    @NonBlocking
    public void postComment(@Valid Comment comment) {
        commentProducer.send(comment);
    }

    private static Comment createComment(String postId, CommentMessage comment) {
        return Comment.builder()
                .postId(postId)
                .comment(comment.getComment())
                .build();
    }
}