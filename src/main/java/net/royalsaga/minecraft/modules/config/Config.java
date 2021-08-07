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
package net.royalsaga.minecraft.modules.config;

import net.royalsaga.minecraft.modules.modules.Module;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class that represents a config located on {@code /plugins/<plugin>/modules/<module id>/}
 *
 * @author GabyTM
 * @see Module#getConfig()
 * @since 1.0.0
 */
public class Config {

    private final Module<?> module;
    private final Path path;

    private final YamlConfigurationLoader loader;
    private CommentedConfigurationNode root;

    /**
     * @param module     module
     * @param path       path starting from {@code /plugins/<plugin>/modules/<module id>/}
     * @param isResource whether the file can be found on plugin's resources folder
     * @see org.bukkit.plugin.java.JavaPlugin#saveResource(String, boolean)
     * @since 1.0.0
     */
    public Config(Module<?> module, Path path, boolean isResource) {
        final Path pathFromDataFolder = Paths.get("modules", module.getId()).resolve(path);

        this.module = module;
        this.path = module.getPlugin().getDataFolder().toPath().resolve(pathFromDataFolder);

        if (!Files.exists(this.path)) {
            if (isResource) {
                try {
                    module.getPlugin().saveResource(pathFromDataFolder.toString(), false);
                } catch (IllegalArgumentException e) {
                    module.error("Could not save " + this.path, e);
                }
            } else {
                try {
                    Files.createFile(this.path);
                } catch (IOException e) {
                    module.error("Could not create " + this.path, e);
                }
            }
        }

        this.loader = YamlConfigurationLoader.builder()
                .indent(2)
                .nodeStyle(NodeStyle.BLOCK)
                .path(this.path)
                .build();

        load();
    }

    /**
     * Secondary constructor for creating a {@code config.yml} file that is copied from plugin's resources folder
     *
     * @param module module instance
     * @since 1.0.0
     */
    public Config(Module<?> module, boolean isResource) {
        this(module, Paths.get("config.yml"), isResource);
    }

    /**
     * Load the config
     *
     * @see YamlConfigurationLoader#load()
     */
    private void load() {
        try {
            this.root = loader.load();
        } catch (ConfigurateException e) {
            module.error("Could not load " + this.path, e);
        }
    }

    /**
     * Reload the config
     *
     * @see #load()
     * @since 1.0.0
     */
    public void reload() {
        load();
    }

    /**
     * Gets the node at the given (relative) path, possibly traversing multiple
     * levels of nodes.
     *
     * <p>This is the main method used to navigate through
     * the configuration.</p>
     *
     * <p>The path parameter effectively consumes an array of keys, which locate
     * the unique position of a given node within the structure. Each element
     * will navigate one level down in the configuration hierarchy</p>
     *
     * <p>A node is <b>always</b> returned by this method. If the given node
     * does not exist in the structure, a {@link CommentedConfigurationNode#virtual() virtual} node will
     * be returned which represents the position.</p>
     *
     * @param path the path to fetch the node at
     * @return the node at the given path, possibly virtual
     **/
    @NotNull
    public CommentedConfigurationNode node(final Object... path) {
        return root.node(path);
    }

}
