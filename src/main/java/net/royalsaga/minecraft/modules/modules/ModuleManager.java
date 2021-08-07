/**
 * MIT License
 * <p>
 * Copyright (c) 2021 RoyalSaga
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.royalsaga.minecraft.modules.modules;

import me.mattstudios.mf.base.CommandManager;
import net.royalsaga.minecraft.modules.placeholders.PlaceholderProvider;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager<P extends JavaPlugin> {

    private final Map<String, Module<P>> registeredModules = new HashMap<>();
    private final Map<String, PlaceholderProvider> placeholderProviders = new HashMap<>();

    private final P plugin;
    private final CommandManager commandManager;

    public ModuleManager(@NotNull P plugin, @Nullable CommandManager commandManager) {
        this.plugin = plugin;
        this.commandManager = commandManager == null ? new CommandManager(plugin, true) : commandManager;
    }

    public void register(@NotNull final Module<P> module) {
        registeredModules.put(module.id, module);

        module.getListeners().forEach(it -> Bukkit.getServer().getPluginManager().registerEvents(it, plugin));

        if (module instanceof CommandModule) {
            final CommandModule commandModule = (CommandModule) module;

            commandModule.getCompletions().forEach(commandManager.getCompletionHandler()::register);
            commandModule.getMessages().forEach(commandManager.getMessageHandler()::register);
            commandModule.getParameters().forEach(commandManager.getParameterHandler()::register);
            commandModule.getCommands().forEach(commandManager::register);
        }

        if (module instanceof PlaceholderProvider) {
            placeholderProviders.put(module.id, (PlaceholderProvider) module);
        }

        module.info("Registered!");
        module.onRegister();
    }

    /**
     * Get a module by its {@link Module#id id}
     * @param id id
     * @return module if found, otherwise null
     */
    @Nullable
    public Module<P> getModule(@NotNull final String id) {
        return registeredModules.get(id);
    }

    @Nullable
    public String parsePlaceholder(@Nullable final OfflinePlayer player, @NotNull final String params) {
        final String[] split = params.split("_", 2);
        final PlaceholderProvider provider = placeholderProviders.get(split[0]);

        if (provider == null) {
            return null;
        }

        return provider.parse(player, (split.length == 1) ? "" : split[1]);
    }

}
