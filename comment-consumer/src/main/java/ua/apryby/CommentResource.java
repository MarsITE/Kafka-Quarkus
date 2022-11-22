package ua.apryby;

import org.jboss.resteasy.reactive.RestPath;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
@ApplicationScoped
public class CommentResource {

    @GET
    @Transactional
    public List<CommentDTO> getAll() {
        return Comment.listAll().stream().map(it -> mapToDto((Comment) it)).toList();
    }

    @GET
    @Path("/{postId}")
    @Transactional
    public List<CommentDTO> getByPostId(@RestPath String postId) {
        return Comment.list("postId", postId).stream()
                .map(it -> mapToDto((Comment) it)).toList();
    }

    private CommentDTO mapToDto(Comment it) {
        return new CommentDTO(it.id, it.postId, it.comment);
    }
}