package codes.laivy.engine.graphics.layout.grid;

import codes.laivy.engine.graphics.layout.grid.disposition.GridDisposition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.LinkedHashMap;
import java.util.Map;

public class GridColumn {

    private final @NotNull GridRow row;
    private @Nullable GridDisposition disposition;

    private @NotNull GridColumn.Breakpoints breakpoints;

    public GridColumn(@NotNull GridRow row, @NotNull Breakpoints breakpoints) {
        this.row = row;
        this.breakpoints = breakpoints;
        getRow().getColumns().add(this);
    }

    public @Nullable GridDisposition getDisposition() {
        return disposition;
    }

    public void setDisposition(@Nullable GridDisposition disposition) {
        this.disposition = disposition;
    }

    public @NotNull Breakpoints getBreakpoints() {
        return breakpoints;
    }
    public void setBreakpoints(@NotNull Breakpoints breakpoints) {
        this.breakpoints = breakpoints;
    }

    @Contract(pure = true)
    public @NotNull GridRow getRow() {
        return row;
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

        protected final @NotNull Map<@NotNull GridSize, @NotNull Integer> breakpoints;

        /**
         * @deprecated Use {@link Breakpoints#Breakpoints(Map)} instead, if you want to use that way, be sure that you are defining the breakpoints using the {@link Breakpoints#addBreakpoint(GridSize, int)} method.
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
        public Breakpoints(@NotNull Map<@NotNull GridSize, @NotNull Integer> breakpoints) {
            this.breakpoints = breakpoints;
        }

        public void addBreakpoint(@NotNull GridSize size, @Range(from = 1, to = 12) int spacing) {
            this.breakpoints.put(size, spacing);
        }

        /**
         * Gets the spacing, following the {@linkplain Breakpoints Hierarchy Example}.
         * @param size the screen size
         * @return The size between 1 and 12
         */
        public @Range(from = 1, to = 12) int getSpacing(@NotNull GridSize size) {
            Integer value = breakpoints.get(size);
            if (value != null) {
                return value;
            }
            GridSize next = size.getNext();
            GridSize prev = size.getPrevious();
            while (next != null || prev != null) {
                if (breakpoints.containsKey(next)) {
                    return breakpoints.get(next);
                }
                if (breakpoints.containsKey(prev)) {
                    return breakpoints.get(prev);
                }
                if (next != null) next = next.getNext();
                if (prev != null) prev = prev.getPrevious();
            }
            throw new IllegalStateException("This Breakpoint doesn't have breakpoints yet!");
        }

    }

}
