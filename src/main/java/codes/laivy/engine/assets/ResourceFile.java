package codes.laivy.engine.assets;

import codes.laivy.engine.exceptions.LaivyEngineException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;

/**
 * This class has created to block the {@link File#renameTo(File)}. File renaming can cause problems during the {@link Asset} mapping.
 *
 * @author ItsLaivy
 * @since 1.0 build 0 (~03/07/2022)
 */
public class ResourceFile extends File {

    public ResourceFile(@NotNull File file) {
        super(file.toURI());
    }
    public ResourceFile(@NotNull String pathname) {
        super(pathname);
    }
    public ResourceFile(@NotNull String parent, @NotNull String child) {
        super(parent, child);
    }
    public ResourceFile(@NotNull File parent, @NotNull String child) {
        super(parent, child);
    }
    public ResourceFile(@NotNull URI uri) {
        super(uri);
    }

    @Override
    @Contract("_ -> fail")
    public final boolean renameTo(@NotNull File dest) {
        throw new LaivyEngineException(new IllegalStateException("You cannot change the file name of a ResourceFile!"), "ResourceFile#renameTo(File) method");
    }

}
