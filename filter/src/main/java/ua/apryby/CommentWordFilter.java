package ua.apryby;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CommentWordFilter {

    @ConfigProperty(name = "ua.apryby.comment.words.blacklist")
    List<String> wordsBlacklist;

    @Inject
    @Channel("comments")
    Emitter<String> commentsProducer;

    @Inject
    @Channel("comments_blacklist")
    Emitter<String> badCommentsProducer;

    @Incoming("comment_requests")
    public void process(ConsumerRecord<String, String> record) {
        final String msg = record.value();
        final CommentSenderDispatcher senderStrategy = new CommentSenderDispatcher(msg, commentsProducer, badCommentsProducer);

        senderStrategy
                .validate(wordsBlacklist)
                .send(createMetadata(record));
    }

    private static OutgoingKafkaRecordMetadata<String> createMetadata(ConsumerRecord<String, String> record) {
        return OutgoingKafkaRecordMetadata.<String>builder()
                .withKey(record.key())
                .build();
    }
}
