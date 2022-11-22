package ua.apryby;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CommentConsumer {

    @Incoming(CommentChannel.COMMENTS)
    public void processComments(ConsumerRecord<String, String> record) {
        final Comment obj = Comment.verified(record.key(), record.value());

        obj.persistAndFlush();
    }

    @Incoming(CommentChannel.COMMENTS_BLACKLIST)
    public void processCommentBlacklist(ConsumerRecord<String, String> record) {
        final Comment obj = Comment.blackListed(record.key(), record.value());

        obj.persistAndFlush();
    }
}
