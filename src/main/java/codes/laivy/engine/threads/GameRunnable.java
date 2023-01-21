package codes.laivy.engine.threads;

import codes.laivy.engine.exceptions.LaivyEngineException;

public interface GameRunnable extends Runnable {
    default void execute() {
        try {
            run();
        } catch (Throwable e) {
            throw new LaivyEngineException(e, "GameRunnable execution");
        }
    }
}
