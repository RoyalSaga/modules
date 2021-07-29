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
package net.royalsaga.minecraft.modules;

import net.royalsaga.minecraft.modules.annotations.ModuleInfo;
import net.royalsaga.minecraft.modules.config.Config;
import net.royalsaga.minecraft.modules.exceptions.ModuleException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * @author GabyTM
 * @since 1.0.0
 * @param <P> the plugin
 */
public class Module<P extends JavaPlugin> {

    protected final P plugin;
    protected final String id;
    protected final Config config;

    /**
     *
     * @param plugin plugin
     * @throws ModuleException if the class is not annotated with {@link ModuleInfo}
     */
    public Module(@NotNull P plugin) throws ModuleException {
        this.plugin = plugin;

        if (!getClass().isAnnotationPresent(ModuleInfo.class)) {
            throw new ModuleException("Module class " + getClass().getCanonicalName() + " needs to be annotated with @ModuleInfo");
        }

        this.config = new Config(this);

        final ModuleInfo info = getClass().getAnnotation(ModuleInfo.class);
        this.id = info.id();
    }

    /**
     * Log a message through {@link Module#plugin}'s logger with the module {@link Module#id} as prefix
     * @param level level
     * @param message message
     * @since 1.0.0
     */
    private void log(@NotNull final Level level, @NotNull final String message) {
        plugin.getLogger().log(level, String.format("[%s] %s", id, message));
    }

    public final P getPlugin() {
        return plugin;
    }

    public final Config getConfig() {
        return config;
    }

    public final String getId() {
        return id;
    }

    public List<? extends ModuleListener<Module<?>>> getListeners() {
        return Collections.emptyList();
    }

    public void onReload() { }

    /**
     * Log an error with {@link Level#SEVERE SEVERE} level through {@link #plugin}'s logger with module's {@link #id} as prefix
     * @param message message
     * @param throwable error to log
     * @since 1.0.0
     */
    public final void error(@NotNull final String message, @NotNull final Throwable throwable) {
        plugin.getLogger().log(Level.SEVERE, String.format("[%s] %s", id, message), throwable);
    }

    /**
     * Log an info message with {@link Level#INFO INFO} level
     * @param message message
     * @see #log(Level, String)
     * @since 1.0.0
     */
    public final void info(@NotNull final String message) {
        log(Level.INFO, message);
    }

    /**
     * Log a warning message with {@link Level#WARNING WARNING} level
     * @param message message
     * @see #log(Level, String)
     * @since 1.0.0
     */
    public final void warn(@NotNull final String message) {
        log(Level.WARNING, message);
    }

}
