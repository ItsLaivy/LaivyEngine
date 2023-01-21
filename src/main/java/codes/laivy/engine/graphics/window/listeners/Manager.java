package codes.laivy.engine.graphics.window.listeners;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface Manager<T extends EventListener> {

    @NotNull T getListener();
    void setListener(@NotNull T listener);

}
