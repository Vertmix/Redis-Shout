package com.vertmix.shout.registry;

import me.lucko.helper.cooldown.Cooldown;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleShoutRegistry implements ShoutRegistry {

    private final Map<UUID, Cooldown> cooldowns = new ConcurrentHashMap<>();

    @Override
    public @NotNull Cooldown register(@NotNull UUID uuid, @NotNull Cooldown cooldown) {
        this.cooldowns.put(uuid, cooldown);
        return cooldown;
    }

    @Override
    public void invalidate(@NotNull UUID uuid) {
        this.cooldowns.remove(uuid);
    }

    @Override
    public @NotNull Optional<Cooldown> get(@NotNull UUID uuid) {
        return Optional.ofNullable(this.cooldowns.get(uuid));
    }
}
