package codes.laivy.engine.graphics.window.listeners;

import codes.laivy.engine.coordinates.Location;
import codes.laivy.engine.graphics.window.swing.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouseManager implements Manager<MouseListener> {

    protected @Nullable Location clickLocation;

    protected boolean pressing = false;

    protected @NotNull MouseListener listener = new MouseAdapter() {
    };

    private final @NotNull GamePanel panel;

    public GameMouseManager(@NotNull GamePanel panel) {
        this.panel = panel;

        getPanel().getWindow().getFrame().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getPanel().getMouseManager().getListener().mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                getPanel().getMouseManager().setClickLocation(new Location(e.getX(), e.getY()));
                getPanel().getMouseManager().setPressing(true);
                getPanel().getMouseManager().getListener().mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                getPanel().getMouseManager().setClickLocation(null);
                getPanel().getMouseManager().setPressing(false);
                getPanel().getMouseManager().getListener().mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                getPanel().getMouseManager().getListener().mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                getPanel().getMouseManager().getListener().mouseExited(e);
            }
        });
    }

    @Contract(pure = true)
    public @NotNull GamePanel getPanel() {
        return panel;
    }

    public @Nullable Location getClickLocation() {
        return clickLocation;
    }
    public void setClickLocation(@Nullable Location clickLocation) {
        this.clickLocation = clickLocation;
    }

    public boolean isPressing() {
        return pressing;
    }
    public void setPressing(boolean pressing) {
        this.pressing = pressing;
    }

    @Override
    public @NotNull MouseListener getListener() {
        return listener;
    }
    @Override
    public void setListener(@NotNull MouseListener listener) {
        this.listener = listener;
    }

}
