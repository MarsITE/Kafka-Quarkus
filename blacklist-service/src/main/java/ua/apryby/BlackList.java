package ua.apryby;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BlackList {

    @ConfigProperty(name = "words.blacklist")
    List<String> blacklist;

    List<String> getBlacklist() {
        if (blacklist == null || blacklist.isEmpty()) {
            return new ArrayList<>(0);
        }

        return blacklist;
    }

    boolean validate(String message) {
        return getBlacklist().stream().anyMatch(message::contains);
    }

}
