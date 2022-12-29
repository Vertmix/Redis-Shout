package com.vertmix.shout.command;

import com.vertmix.shout.service.ShoutService;
import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.jetbrains.annotations.NotNull;

public class ShoutCommand implements TerminableModule {

    private final ShoutService shoutService;

    public ShoutCommand(ShoutService shoutService) {
        this.shoutService = shoutService;
    }

    @Override
    public void setup(@NotNull TerminableConsumer consumer) {
        Commands.create().assertPlayer().assertPermission("vertmix.shout.use").handler(c -> {
            final StringBuilder stringBuilder = new StringBuilder(c.args().size());

            for (int i =0; i < c.args().size(); i++) {
                stringBuilder.append(c.args().get(i)).append(" ");
            }

            shoutService.attemptShout(c.sender(), stringBuilder.toString()).thenAcceptSync(response -> c.reply(response ? "&cSent...." : "&cCould not send...")).bindWith(consumer);

        }).registerAndBind(consumer, "shout");
    }
}
