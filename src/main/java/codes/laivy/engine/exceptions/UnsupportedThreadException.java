package codes.laivy.engine.exceptions;

import codes.laivy.engine.threads.GameThread;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnsupportedThreadException extends LaivyEngineException {
    public UnsupportedThreadException(@NotNull Class<? extends GameThread> threadClass) {
        this(threadClass, null);
    }
    public UnsupportedThreadException(@NotNull Class<? extends GameThread> threadClass, @Nullable String at) {
        this(threadClass.getSimpleName(), at);
    }

    public UnsupportedThreadException(@NotNull String thread) {
        this(thread, null);
    }
    public UnsupportedThreadException(@NotNull String thread, @Nullable String at) {
        super(new UnsupportedOperationException("This method can only be executed at the '" + thread + "' thread!"), at);
    }
}
