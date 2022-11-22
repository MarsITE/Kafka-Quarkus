package ua.apryby;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

@ApplicationScoped
public class CommentConsumer {
    @Inject
    CommentTopics topics;

    @Inject
    BlackList blackList;

    @Inject
    @Channel("comments_blacklist")
    Emitter<String> blackListedCommentsEmitter;
    @Inject
    @Channel("comments")
    Emitter<String> commentsEmitter;


    @Incoming("comment_requests")
    public void process(ConsumerRecord<String, String> record) {
        final String key = record.key();
        final String comment = record.value();

        if (blackList.validate(comment)) {
            blackListedCommentsEmitter.send(createMsg(topics.getBlackListTopic(), key, comment));
        } else {
            commentsEmitter.send(createMsg(topics.getCommentsTopic(), key, comment));
        }
    }

    private Message<String> createMsg(@NotNull final String topic,
                                      @NotNull final String key,
                                      @NotNull final String value) {
        return Message.of(value).addMetadata(OutgoingKafkaRecordMetadata.<String>builder()
                .withKey(key)
                .withTopic(topic)
                .build());
    }

}
