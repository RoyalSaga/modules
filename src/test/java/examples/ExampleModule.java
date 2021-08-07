/**
 * MIT License
 *
 * Copyright (c) 2021 RoyalSaga
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package examples;

import net.royalsaga.minecraft.modules.annotations.ModuleInfo;
import net.royalsaga.minecraft.modules.exceptions.ModuleException;
import net.royalsaga.minecraft.modules.modules.CommandModule;
import net.royalsaga.minecraft.modules.modules.Module;
import net.royalsaga.minecraft.modules.modules.ModuleCommand;
import net.royalsaga.minecraft.modules.modules.ModuleListener;
import net.royalsaga.minecraft.modules.placeholders.PlaceholderProvider;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@ModuleInfo(id = "example")
public class ExampleModule extends Module<ExamplePlugin> implements CommandModule, PlaceholderProvider {

    /**
     * @param plugin plugin
     * @throws ModuleException if the class is not annotated with {@link ModuleInfo}
     */
    public ExampleModule(@NotNull ExamplePlugin plugin) throws ModuleException {
        super(plugin, true);
    }

    @Override
    public List<? extends ModuleCommand<? extends Module<ExamplePlugin>>> getCommands() {
        return Collections.singletonList(new ExampleCommand(this));
    }

    @Override
    public List<? extends ModuleListener<? extends Module<ExamplePlugin>>> getListeners() {
        return Collections.singletonList(new ExampleListener(this));
    }

    @Override
    public @Nullable String parse(@Nullable OfflinePlayer offlinePlayer, @NotNull String params) {
        if (offlinePlayer == null) {
            return null;
        }

        return offlinePlayer.getName();
    }

}
