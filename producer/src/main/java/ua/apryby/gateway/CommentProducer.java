package ua.apryby.gateway;

import ua.apryby.dto.Comment;

public interface CommentProducer {

    void send(Comment comment);
}
