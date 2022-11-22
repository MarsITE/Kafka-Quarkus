package ua.apryby;

import io.smallrye.common.annotation.NonBlocking;
import org.jboss.resteasy.reactive.RestPath;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/comments")
public class CommentResource {

    @Inject
    CommentEmitter emitter;

    @POST()
    @Path("/{postId}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @NonBlocking
    public void postCommentWithPostId(@RestPath @NotNull String postId,
                                      @Valid CommentMessage comment) {
        final Comment obj = new Comment(postId, comment.getComment());

        emitter.emitComment(obj);
    }

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    @NonBlocking
    public void postComment(@Valid Comment comment) {
        emitter.emitComment(comment);
    }

}