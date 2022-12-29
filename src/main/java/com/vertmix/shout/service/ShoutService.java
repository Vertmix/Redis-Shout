package com.vertmix.shout.service;

import me.lucko.helper.promise.Promise;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ShoutService {

    @NotNull Promise<Boolean> attemptShout(@NotNull Player player, @NotNull String content);
}
