package com.vertmix.shout;

import com.vertmix.shout.command.ShoutCommand;
import com.vertmix.shout.registry.ShoutRegistry;
import com.vertmix.shout.registry.SimpleShoutRegistry;
import com.vertmix.shout.service.ShoutService;
import com.vertmix.shout.service.SimpleShoutService;
import me.lucko.helper.plugin.ExtendedJavaPlugin;

public class ShoutPlugin extends ExtendedJavaPlugin {

    @Override
    protected void enable() {
        final ShoutRegistry shoutRegistry = provideService(ShoutRegistry.class, new SimpleShoutRegistry());
        final ShoutService shoutService = provideService(ShoutService.class, bindModule(new SimpleShoutService(shoutRegistry)));

        bindModule(new ShoutCommand(shoutService));
    }

}
