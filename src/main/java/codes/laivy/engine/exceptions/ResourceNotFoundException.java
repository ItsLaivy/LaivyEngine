package codes.laivy.engine.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * @author ItsLaivy
 * @since 1.0 build 0 (~01/07/2022)
 */
public class ResourceNotFoundException extends LaivyEngineException {
    public ResourceNotFoundException(@NotNull String at) {
        super(new NullPointerException("Cannot get this resource"), at);
    }
}
