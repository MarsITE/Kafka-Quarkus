package ua.apryby;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommentTopics {
    @ConfigProperty(name = "mp.messaging.outgoing.comments_blacklist.topic")
    String blackListTopic;
    @ConfigProperty(name = "mp.messaging.outgoing.comments.topic")
    String commentsTopic;

    public String getBlackListTopic() {
        return blackListTopic;
    }

    public String getCommentsTopic() {
        return commentsTopic;
    }
}
