package org.acme;

import io.smallrye.mutiny.Uni;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;

@ApplicationScoped
@ActivateRequestContext
public class CommentConsumer {

    @Inject
    Mutiny.Session session;

    @Incoming(CommentChannel.COMMENTS)
    public Uni<Void> processComments(ConsumerRecord<String, String> record) {
        final String key = record.key();
        final String value = record.value();
        final Comment obj = Comment.verified(key, value);

        return save(obj);
    }

    @Incoming(CommentChannel.COMMENTS_BLACKLIST)
    public Uni<Void> processCommentBlacklist(ConsumerRecord<String, String> record) {
        final String key = record.key();
        final String value = record.value();
        final Comment obj = Comment.blackListed(key, value);

        return save(obj);
    }

    private Uni<Void> save(Comment obj) {
        return session.withTransaction((t) -> obj
                        .persistAndFlush()
                        .replaceWithVoid())
                .onTermination().call(() -> session.close());
    }
}
