package org.acme;

import io.smallrye.mutiny.Multi;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("comments")
@ApplicationScoped
public class CommentResource {

    @GET
    public Multi<CommentDTO> getAll() {
        return Comment.streamAll().map(it -> mapToDto((Comment) it));
    }

    private CommentDTO mapToDto(Comment it) {
        return new CommentDTO(it.id, it.postId, it.comment);
    }
}
