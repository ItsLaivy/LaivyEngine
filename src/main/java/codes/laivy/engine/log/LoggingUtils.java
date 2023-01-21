package codes.laivy.engine.log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LoggingUtils {

    public static void log(@NotNull String message, @Nullable AnsiColor color) {
        System.out.println((color != null ? color.getAnsiCode() : "") + "[LaivyEngine] " + message + AnsiColor.RESET);
    }
    public static void error(@NotNull String message) {
        System.out.println(AnsiColor.RED + "[LaivyEngine] " + message + AnsiColor.RESET);
    }

}
