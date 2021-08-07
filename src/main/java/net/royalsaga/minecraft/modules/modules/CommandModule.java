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

import me.mattstudios.mf.base.components.CompletionResolver;
import me.mattstudios.mf.base.components.MessageResolver;
import me.mattstudios.mf.base.components.ParameterResolver;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Class that represents a {@link Module} with commands
 * @see ModuleCommand
 * @author GabyTM
 * @since 1.0.0
 */
public interface CommandModule {

    /**
     * Get the commands of a module
     * @return list of {@link ModuleCommand}
     * @see ModuleManager#register(Module)
     * @since 1.0.0
     */
    List<? extends ModuleCommand<? extends Module<?>>> getCommands();

    /**
     * Get the commands completions of a module
     * @return map of {@link CompletionResolver}s and their ID
     * @see me.mattstudios.mf.base.CompletionHandler#register(String, CompletionResolver)
     * @see ModuleManager#register(Module)
     * @since 1.0.0
     */
    default Map<String, CompletionResolver> getCompletions() {
        return Collections.emptyMap();
    }

    /**
     * Get the commands messages of a module
     * @return map of {@link MessageResolver}s and their ID
     * @see me.mattstudios.mf.base.MessageHandler#register(String, MessageResolver)
     * @see ModuleManager#register(Module)
     * @since 1.0.0
     */
    default Map<String, MessageResolver> getMessages() {
        return Collections.emptyMap();
    }

    /**
     * Get the commands parameters of a module.
     * @return map of {@link ParameterResolver}s and their {@link Class}
     * @see me.mattstudios.mf.base.ParameterHandler#register(Class, ParameterResolver)
     * @see ModuleManager#register(Module)
     * @since 1.0.0
     */
    default Map<Class<?>, ParameterResolver> getParameters() {
        return Collections.emptyMap();
    }

}
