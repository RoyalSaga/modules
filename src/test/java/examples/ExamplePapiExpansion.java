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

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.royalsaga.minecraft.modules.modules.ModuleManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class ExamplePapiExpansion extends PlaceholderExpansion {

    private final ModuleManager<ExamplePlugin> moduleManager;

    public ExamplePapiExpansion(ModuleManager<ExamplePlugin> moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "example";
    }

    @Override
    public @NotNull String getAuthor() {
        return "GabyTM";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        return moduleManager.parsePlaceholder(player, params);
    }

}
