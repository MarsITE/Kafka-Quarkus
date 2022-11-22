package ua.apryby;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/comments")
@ApplicationScoped
public class CommentResource {

    @GET
    public List<CommentDTO> getAll() {
        return Comment.listAll().stream().map(it -> mapToDto((Comment) it)).toList();
    }

    private CommentDTO mapToDto(Comment it) {
        return new CommentDTO(it.id, it.postId, it.comment);
    }
}
