package net.celestialproductions.api.util.os;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * @author Savior
 */
public final class Files {

    /**
     * Deletes a file and all its subfiles
     * @param file File to be deleted
     * @return true if the deletion was successful
     */
    public static boolean deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                Arrays.stream(file.listFiles()).forEach(Files::deleteFile);
            }
            return file.delete();
        }
        return false;
    }


    /**
     * Lets the OS open a file
     * @param file File
     */
    public static void openFile(final File file) {
        if (Desktop.isDesktopSupported() && file != null) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Lets the OS open the directory of a file
     * @param file File
     */
    public static void openFileDirectory(File file) {
        if (file != null) {
            if (!file.isDirectory()) {
                file = file.getParentFile();
            }
            openFile(file);
        }
    }

    /**
     * Writes all bytes of data into a file.
     * The file gets overwritten.
     * @param file File to store data
     * @param data Data to be stored
     * @return Returns false if the file is not valid
     */
    public static boolean writeBinaryFile(final File file, final byte[] data) {
        if (file != null && !file.isDirectory()) {
            try {
                if (file.exists()) {
                    file.delete();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }

                final OutputStream os = new FileOutputStream(file);
                os.write(data);

                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
