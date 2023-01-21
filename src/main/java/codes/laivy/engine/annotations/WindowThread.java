package codes.laivy.engine.annotations;

import java.lang.annotation.*;

/**
 * Methods annotated with that annotation needs to be executed on the WindowThread
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WindowThread {
}
