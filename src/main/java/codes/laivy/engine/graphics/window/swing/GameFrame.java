package codes.laivy.engine.graphics.window.swing;

import codes.laivy.engine.assets.ImageAsset;
import codes.laivy.engine.graphics.window.GameWindow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private final @NotNull GameWindow window;
    private @Nullable ImageAsset icon;

    public GameFrame(@NotNull GameWindow window) {
        this.window = window;
    }

    @Contract(pure = true)
    public @NotNull GameWindow getWindow() {
        return window;
    }

    @Override
    public void setIconImage(Image image) {
        throw new UnsupportedOperationException("Use the GameFrame#setIcon method instead");
    }

    public void setIcon(@Nullable ImageAsset icon) {
        this.icon = icon;

        if (icon != null) {
            super.setIconImage(icon.getBufferedImage());
        } else {
            super.setIconImage(null);
        }
    }
    public @Nullable ImageAsset getIcon() {
        return icon;
    }

    public void setTitle(@Nullable String title) {
        if (title == null) title = "";
        super.setTitle(title);
    }
    public @NotNull String getTitle() {
        return super.getTitle();
    }

}
