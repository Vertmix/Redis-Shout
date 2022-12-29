package com.vertmix.shout.registry;

import me.lucko.helper.cooldown.Cooldown;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface ShoutRegistry {

    @NotNull Cooldown register(@NotNull UUID uuid, @NotNull Cooldown cooldown);

    void invalidate(@NotNull UUID uuid);

    @NotNull Optional<Cooldown> get(@NotNull UUID uuid);

}
