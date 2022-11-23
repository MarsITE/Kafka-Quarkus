package ua.apryby;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.List;

@RequiredArgsConstructor
public class CommentSenderDispatcher {
    private final String message;
    private final Emitter<String> commentsProducer;
    private final Emitter<String> badCommentsProducer;

    CommentSenderStrategy validate(List<String> badWords) {
        if (containsBadWords(badWords)) {
            return new CommentSenderStrategy(badCommentsProducer, message);
        } else {
            return new CommentSenderStrategy(commentsProducer, message);
        }
    }

    private boolean containsBadWords(List<String> badWords) {
        if (badWords == null || badWords.isEmpty()) return false;

        return badWords.stream().anyMatch(message::contains);
    }

    record CommentSenderStrategy(Emitter<String> emitter, String message) {
        public void send(OutgoingKafkaRecordMetadata<String> metadata) {
            emitter.send(createMsg(message, metadata));
        }

        private Message<String> createMsg(String msg, OutgoingKafkaRecordMetadata<String> metadata) {
            return Message.of(msg).addMetadata(metadata);
        }
    }
}
