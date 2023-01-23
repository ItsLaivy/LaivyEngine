package codes.laivy.engine.graphics.layout.grid;

import codes.laivy.engine.graphics.layout.grid.disposition.GridDisposition;
import com.sun.glass.ui.Size;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GridColumn {

    private final @NotNull GridRow row;
    private final @NotNull Set<@NotNull GridDisposition> dispositions = new LinkedHashSet<>();
    private @Range(from = 1, to = 12) int maxDispositions = 3; // 3 is the default amount of max dispositions.

    private @NotNull GridColumn.Breakpoints breakpoints;

    public GridColumn(@NotNull GridRow row, @NotNull Breakpoints breakpoints) {
        this.row = row;
        this.breakpoints = breakpoints;
    }

    public @NotNull Breakpoints getBreakpoints() {
        return breakpoints;
    }
    public void setBreakpoints(@NotNull Breakpoints breakpoints) {
        this.breakpoints = breakpoints;
    }

    /**
     * The maximum dispositions number is the amount of dispositions (starting from the first to last) that will
     * be displayed into the screen, if i have 10 dispositions at the {@link #getDispositions()} method but 3 {@link #getMaxDispositions()}
     * only the first 3 dispositions will be displayed
     *
     * @since 1.0 22/01/2023
     * @author ItsLaivy
     * @return a number between 1 and 12 of the maximum dispositions at this row
     */
    public @Range(from = 1, to = 12) int getMaxDispositions() {
        return maxDispositions;
    }
    /**
     * @since 1.0 22/01/2023
     * @author ItsLaivy
     * @param maxDispositions a number between 1 and 12 of the maximum dispositions at this row
     */
    public void setMaxDispositions(@Range(from = 1, to = 12) int maxDispositions) {
        this.maxDispositions = maxDispositions;
    }

    @Contract(pure = true)
    public @NotNull GridRow getRow() {
        return row;
    }

    /**
     * The dispositions that will be displayed.
     * <br><br>
     * <strong>Note:</strong> Only will be displayed the amount of {@link #getMaxDispositions()} starting from the first to last.
     * if the {@link #getMaxDispositions()} is 3, only the first 3 dispositions will be displayed.

     * @since 1.0 22/01/2023
     * @author ItsLaivy
     * @return The dispositions
     */
    @ApiStatus.Internal
    @Contract(pure = true)
    public @NotNull Set<@NotNull GridDisposition> getDispositions() {
        return dispositions;
    }

    /**
     * A column breakpoint is the best way to turn your column responsive.
     * Examples:
     * <ol>
     *   <li>If you set a breakpoint for LG size to 6, when the screen size turns to LG, the column will have 6 spaces;</li>
     *   <li>If you set a breakpoint for MD size to 12, when the screen size turns to MD, the column will have 12 spaces (This single column will use the entire row).</li>
     * </ol>
     *
     * <strong>Observations:</strong>
     * <ul>
     *   <li>It's <strong>required</strong> to have atleast one breakpoint;</li>
     *   <li>
     *       Note that example of breakpoints hierarchy:
     *       <ol>
     *          <li>I've created a MD breakpoint for 6 spaces.</li>
     *              <p>now XS, SM, MD, LG, XL, and XXL will have 6 spaces too.</p>
     *          <li>Then i've created a LG breakpoint for 4 spaces.</li>
     *              <p>now XS, SM and MD will have 6 spaces</p>
     *              <p>and LG, XL and XXL will have 4 spaces.</p>
     *          <li>Then i've created a XL breakpoint for 2 spaces.</li>
     *              <p>now XS, SM and MD will have 6 spaces;</p>
     *              <p>LG will have 4 spaces,</p>
     *              <p>and XL and XXL will have 2 spaces.</p>
     *       </ol>
     *       As you can see, the breakpoints respects the last breakpoint's spacing (or the first, if doesn't have a last breakpoint).
     *   </li>
     * </ul>
     *
     * @since 1.0 22/01/2023
     * @author ItsLaivy
     */
    public static class Breakpoints {

        protected final @NotNull Map<@NotNull Size, @NotNull Integer> breakpoints;

        /**
         * @deprecated Use {@link Breakpoints#Breakpoints(Map)} instead, if you want to use that way, be sure that you are defining the breakpoints using the {@link Breakpoints#addBreakpoint(Size, int)} method.
         * @since 1.0 22/01/2023
         * @author ItsLaivy
         */
        @Deprecated
        public Breakpoints() {
            this(new LinkedHashMap<>());
        }

        /**
         * @since 1.0 22/01/2023
         * @author ItsLaivy
         * @param breakpoints the breakpoints map
         */
        public Breakpoints(@NotNull Map<@NotNull Size, @NotNull Integer> breakpoints) {
            this.breakpoints = breakpoints;
        }

        public void addBreakpoint(@NotNull Size size, @Range(from = 1, to = 12) int spacing) {
            this.breakpoints.put(size, spacing);
        }

        /**
         * Gets the spacing, following the {@linkplain Breakpoints Hierarchy Example}.
         * @param size the screen size
         * @return The size between 1 and 12
         */
        public @Range(from = 1, to = 12) int getSpacing(@NotNull Size size) {
            // TODO: 22/01/2023 Doing this!
        }

    }

}
