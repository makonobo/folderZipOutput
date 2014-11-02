/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zipoutput;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author ただし
 */
public class ZipOutput {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ZipOutputStream zos = null;
        String directory = args[0];
        
        try {
            
            File zipFileName = new File(args[1]);
            zos = new ZipOutputStream(new FileOutputStream(zipFileName), Charset.forName("Shift_JIS"));
            
            encode(zos, new File(directory));
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ZipOutput.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ZipOutput.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                zos.close();
            } catch (IOException ex) {
                Logger.getLogger(ZipOutput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private static void encode(ZipOutputStream zos, File directory) throws IOException {

        List<String> fileList = new ArrayList<>();

        if (directory.isDirectory()) {

            File[] files = directory.listFiles();
            for (File file : files) {

                if (file.isFile()) {
                    if (file.getName().endsWith("jpg")) {
                        fileList.add(file.getPath());
                    }

                }
            }

        }

        if (!fileList.isEmpty()) {

            byte[] buf = new byte[1024];

            for (String filePath : fileList) {
                
                File jpg = new File(filePath);
                
                zos.putNextEntry(new ZipEntry(jpg.getName()));
                try (InputStream is = new BufferedInputStream(new FileInputStream(jpg))) {
                    for (;;) {
                        int len = is.read(buf);
                        if (len < 0) {
                            break;
                        }
                        zos.write(buf, 0, len);
                    }
                }
            }

        }

    }

}
