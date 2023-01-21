package codes.laivy.engine;

public enum GameState {

    /**
     * This state is reached when the game graphics is initializing.
     */
    GRAPHICS_INITIALIZING(),

    /**
     * This state is reached when the game is initializing, the method {@link Game#init()} is called.
     */
    GAME_INITIALIZING(),

    /**
     * This state is reached when the game is fully loaded.
     * @since 1.0-0 (18/01/2023)
     */
    GAME_LOADED(),
    ;

}
