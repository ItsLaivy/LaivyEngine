package codes.laivy.engine;

import codes.laivy.engine.assets.ImageAsset;
import codes.laivy.engine.exceptions.LaivyEngineException;
import codes.laivy.engine.graphics.GameGraphics;
import codes.laivy.engine.graphics.window.GameWindow;
import codes.laivy.engine.threads.GameThread;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Game {

    public static @NotNull Map<@NotNull String, @NotNull Game> GAMES = new LinkedHashMap<>();
    public static @NotNull ImageAsset LAIVY_ENGINE_LOGO;

    static {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            if (e instanceof LaivyEngineException) {
                throw (LaivyEngineException) e;
            } else {
                throw new LaivyEngineException(e, "Exception at the thread '" + t.getName() + "', id '" + t.getId() + "'");
            }
        });

        try {
            URL resource = Game.class.getClassLoader().getResource("LaivyEngine.png");
            if (resource != null) {
                LAIVY_ENGINE_LOGO = new ImageAsset(resource.toURI());
            } else {
                throw new NullPointerException("Couldn't find LaivyEngine's logo resource");
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static @NotNull Game getGame(@NotNull String name) {
        if (GAMES.containsKey(name)) {
            return GAMES.get(name);
        } else {
            throw new NullPointerException("Couldn't find a game with that name '" + name + "'");
        }
    }

    /*
    *
     *
    * Game Abstractions
     *
    *
     */

    /**
     * Called when the game inits
     *
     * @since 1.0 build 0 (30/06/2022)
     * @author ItsLaivy
     *
     */
    public abstract void init();

    /**
     * Called when the window layout will be repainted
     * <br>
     * <strong>Note:</strong> That method is executed on the Window WindowThread.
     *
     * @since 1.0 build 0 (30/06/2022)
     * @author ItsLaivy
     *
     */
    public abstract void render();

    /**
     * Called when an exception happens on the threads
     * @return true if the exception will be throwed, false to ignore
     *
     * @since 1.0 build 0 (19/01/2022)
     * @author ItsLaivy
     *
     */
    public abstract boolean exception(@NotNull Thread thread, @NotNull Throwable e);

    public abstract @NotNull String getName();

    /*
    *
     *
    * Game Object
     *
    *
     */

    private @NotNull GameState state;
    protected @NotNull GameGraphics graphics;

    private final @NotNull List<@NotNull GameThread> threads = new LinkedList<>();

    public Game() {
        // Game instance saving
        if (GAMES.containsKey(getName())) {
            throw new IllegalArgumentException("A game named '" + getName() + "' already exists.");
        } GAMES.put(getName(), this);
        //

        state = GameState.GRAPHICS_INITIALIZING;
        graphics = new GameGraphics(this, new GameWindow(this));

        state = GameState.GAME_INITIALIZING;
        init();

        state = GameState.GAME_LOADED;

        getGraphics().getWindow().setVisible(true);
    }

    public @NotNull List<GameThread> getThreads() {
        return threads;
    }

    public @NotNull GameState getState() {
        return state;
    }

    public @NotNull GameGraphics getGraphics() {
        return graphics;
    }
    public @NotNull GameWindow getWindow() {
        return getGraphics().getWindow();
    }
}
