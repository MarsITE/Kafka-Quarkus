package ua.apryby;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
@ApplicationScoped
public class CommentResource {
    @GET
    @Transactional
    public List<CommentDTO> getAll() {
        return Comment.listAll().stream()
                .map(it -> mapToDto((Comment) it)).toList();
    }

    @GET
    @Path("/{postId}")
    @Transactional
    public List<CommentDTO> getByPostId(@PathParam("postId") String postId) {
        return Comment.list("postId", postId).stream()
                .map(it -> mapToDto((Comment) it)).toList();
    }

    @GET
    @Path("/searchVerified")
    @Transactional
    public List<CommentDTO> findVerifiedComments() {
        return Comment.list("type", Comment.Type.VERIFIED).stream()
                .map(it -> mapToDto((Comment) it)).toList();
    }

    @GET
    @Path("/searchBlacklisted")
    @Transactional
    public List<CommentDTO> findBlacklistedComments() {
        return Comment.list("type", Comment.Type.BLACKLISTED).stream()
                .map(it -> mapToDto((Comment) it)).toList();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Comment updateCommentById(@PathParam("id") Long id,
                                        @Valid Comment comment) {
        Comment entity = Comment.findById(id);

        if (entity == null) {
            throw new NotFoundException();
        }

        entity.update(comment);

        return entity;
    }

    @DELETE
    @Transactional
    public void deleteAll() {
        Comment.deleteAll();
    }

    @DELETE
    @Path("/{postId}")
    @Transactional
    public void deleteByPostId(@PathParam("postId") String postId) {
        Comment.delete("postId", postId);
    }

    @DELETE
    @Path("/deleteVerified")
    @Transactional
    public void deleteVerified() {
        Comment.delete("type", Comment.Type.VERIFIED);
    }

    @DELETE
    @Path("/deleteBlacklisted")
    @Transactional
    public void deleteBlacklisted() {
        Comment.delete("type", Comment.Type.BLACKLISTED);
    }

    private CommentDTO mapToDto(Comment it) {
        return new CommentDTO(it.id, it.postId, it.comment);
    }
}