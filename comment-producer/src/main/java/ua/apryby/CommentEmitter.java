package ua.apryby;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CommentEmitter {

    @ConfigProperty(name = "mp.messaging.outgoing.comment_requests.topic")
    String topicName;

    @Inject
    @Channel("comment_requests")
    Emitter<String> emitter;

    public void emitComment(Comment comment) {
        emitter.send(createMsg(comment));
    }

    private Message<String> createMsg(Comment comment) {
        return Message.of(comment.getComment())
                .addMetadata(OutgoingKafkaRecordMetadata.<String>builder()
                        .withKey(comment.getPostId())
                        .withTopic(topicName)
                        .build());
    }
}
