package ua.apryby.gateway.impl;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import ua.apryby.dto.Comment;
import ua.apryby.gateway.CommentProducer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CommentProducerImpl implements CommentProducer {
    @Inject
    @Channel("comment_requests")
    Emitter<String> emitter;

    @Override
    public void send(Comment comment) {
        emitter.send(createMsg(comment));
    }

    private Message<String> createMsg(Comment comment) {
        return Message.of(comment.getComment()).addMetadata(createMetadata(comment));
    }

    private OutgoingKafkaRecordMetadata<String> createMetadata(Comment comment) {
        return OutgoingKafkaRecordMetadata.<String>builder().withKey(comment.getPostId()).build();
    }
}
