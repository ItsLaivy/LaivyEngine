package codes.laivy.engine.exceptions;

import codes.laivy.engine.log.AnsiColor;
import codes.laivy.engine.log.LoggingUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LaivyEngineException extends Error {

    /**
     * If true, the default error logging will be used, otherwise, a custom LaivyEngine exception logging system will be used
     */
    public static boolean EXCEPTION_STACK_TRACE_MESSAGES = false;

    @Override
    public @NotNull Throwable fillInStackTrace() {
        return this;
    }

    private final @NotNull Throwable throwable;
    private final @Nullable String at;

    public LaivyEngineException(@NotNull Throwable throwable) {
        this(throwable, null);
    }
    public LaivyEngineException(@NotNull Throwable throwable, @Nullable String at) {
        this.throwable = throwable;
        this.at = at;

        if (EXCEPTION_STACK_TRACE_MESSAGES) {
            throwable.printStackTrace();
        } else {
            LoggingUtils.error("");
            for (StackTraceElement element : throwable.getStackTrace()) {
                LoggingUtils.error(element.toString());
            }
            LoggingUtils.error("");
            LoggingUtils.error(AnsiColor.RED + "An error ocurred in: \"" + at + AnsiColor.RED + "\"");
            LoggingUtils.error("");
            LoggingUtils.error(AnsiColor.RED + "Error ¹: \"" + throwable.getClass().getSimpleName() + AnsiColor.RED + "\"");
            LoggingUtils.error(AnsiColor.RED + "Error ²: \"" + throwable.getMessage() + AnsiColor.RED + "\"");
            if (throwable.getCause() != null) {
                LoggingUtils.error(AnsiColor.RED + "Error ³: \"" + throwable.getCause().getMessage() + AnsiColor.RED + "\"");
            }
            LoggingUtils.error("");
        }

        System.exit(0);
    }

    public @NotNull Throwable getThrowable() {
        return throwable;
    }
    public @Nullable String getAt() {
        return at;
    }

}
