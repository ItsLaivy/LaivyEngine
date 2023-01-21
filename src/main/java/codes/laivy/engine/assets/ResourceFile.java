package codes.laivy.engine.assets;

import codes.laivy.engine.exceptions.LaivyEngineException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;

/**
 * This class has created to block the {@link File#renameTo(File)}. File renaming can cause problems during the {@link Asset} mapping.
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
    public final boolean renameTo(@NotNull File dest) {
        throw new LaivyEngineException(new IllegalStateException("You cannot change the file name of a ResourceFile!"), "ResourceFile#renameTo(File) method");
    }

}
