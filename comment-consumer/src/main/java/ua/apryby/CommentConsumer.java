package ua.apryby;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;

@ApplicationScoped
@ActivateRequestContext
public class CommentConsumer {

    @Incoming(CommentChannel.COMMENTS)
    @Blocking
    @Transactional
    public void processComments(ConsumerRecord<String, String> record) {
        final Comment obj = Comment.verified(record.key(), record.value());

        obj.persistAndFlush();
    }

    @Incoming(CommentChannel.COMMENTS_BLACKLIST)
    @Blocking
    @Transactional
    public void processCommentBlacklist(ConsumerRecord<String, String> record) {
        final Comment obj = Comment.blackListed(record.key(), record.value());

        obj.persistAndFlush();
    }
}