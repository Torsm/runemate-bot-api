package net.celestialproductions.api.util;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.util.Resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

/**
 * ResourceUtils
 *
 * @author Torben Schmitz
 */
public class ResourceUtils {

    /**
     * Opens an input stream of the given resource and writes to a file in the bot's storage directory.
     *
     * @param resource bot resource
     * @return the created file, or null if the file could not be written
     */
    public static File writeToFile(String resource) {
        final InputStream resourceStream = Resources.getAsStream(resource);
        final File temp = new File(Environment.getStorageDirectory().getAbsolutePath() + File.separator + Arrays.stream(resource.split("/")).reduce((f, s) -> s).get());
        try {
            Files.copy(resourceStream, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return temp;
    }
}
