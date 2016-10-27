package net.celestialproductions.api.util;

import com.runemate.game.api.hybrid.Environment;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Savior
 */
public final class ImageUtils {
    private final static String FS = File.separator;
    private final static String STORAGE = Environment.getSharedStorageDirectory().getAbsolutePath() + FS + "Celestial Productions" + FS + "images" + FS;

    public static File saveImage(final BufferedImage image, final String dir) {
        final File file = new File(STORAGE + dir + FS + System.currentTimeMillis() + ".png");
        if (!file.exists()) {
            file.mkdirs();
            try {
                ImageIO.write(image, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }
}
