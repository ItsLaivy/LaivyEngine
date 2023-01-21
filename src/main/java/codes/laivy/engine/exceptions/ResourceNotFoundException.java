package codes.laivy.engine.exceptions;

import org.jetbrains.annotations.NotNull;

public class ResourceNotFoundException extends LaivyEngineException {
    public ResourceNotFoundException(@NotNull String at) {
        super(new NullPointerException("Cannot get this resource"), at);
    }
}
