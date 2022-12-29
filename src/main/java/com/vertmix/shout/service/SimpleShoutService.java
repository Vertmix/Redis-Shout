package com.vertmix.shout.service;

import com.vertmix.shout.registry.ShoutRegistry;
import me.lucko.helper.Schedulers;
import me.lucko.helper.Services;
import me.lucko.helper.messaging.Channel;
import me.lucko.helper.messaging.ChannelAgent;
import me.lucko.helper.messaging.Messenger;
import me.lucko.helper.promise.Promise;
import me.lucko.helper.redis.Redis;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SimpleShoutService implements ShoutService, TerminableModule {

    private static final String SHOUT_CHANNEL_NAME = "shout-service";

    private final ShoutRegistry shoutRegistry;
    private final Messenger messenger;
    private final Channel<ShoutMessage> shoutChannel;
    private final ChannelAgent<ShoutMessage> shoutChannelAgent;

    public SimpleShoutService(@NotNull ShoutRegistry shoutRegistry) {
        this.shoutRegistry = shoutRegistry;
        this.messenger = Services.load(Redis.class);
        this.shoutChannel = messenger.getChannel(SHOUT_CHANNEL_NAME, ShoutMessage.class);
        this.shoutChannelAgent = this.shoutChannel.newAgent();
    }

    @Override
    public @NotNull Promise<Boolean> attemptShout(@NotNull Player player, @NotNull String content) {
        return Promise.supplyingAsync(() -> {
            if (this.shoutRegistry.get(player.getUniqueId()).isPresent()) {
                if (!this.shoutRegistry.get(player.getUniqueId()).get().test()) {
                    return false;
                }
            }

            this.shoutChannel.sendMessage(new ShoutMessage(player.getUniqueId(), player.getName(), content));
            return true;
        });
    }

    @Override
    public void setup(@NotNull TerminableConsumer consumer) {
        shoutChannelAgent.addListener((agent, message) -> Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message.content))));
    }

    static class ShoutMessage {

        private UUID playerUuid;
        private String playerName;
        private String content;

        public ShoutMessage(UUID playerUuid, String playerName, String content) {
            this.playerUuid = playerUuid;
            this.playerName = playerName;
            this.content = content;
        }

        public UUID getPlayerUuid() {
            return playerUuid;
        }

        public String getPlayerName() {
            return playerName;
        }

        public String getContent() {
            return content;
        }
    }
}
