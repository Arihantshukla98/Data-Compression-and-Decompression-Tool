package comp_decomp;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class compressor {

    public static File compressIt(File file) throws IOException {
        String path = file.getParent();
        String outputPath = path + "/compressed_" + file.getName() + ".gz";

        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(outputPath);
        GZIPOutputStream gzip = new GZIPOutputStream(fos);

        byte[] buffer = new byte[1024];
        int len;

        while ((len = fis.read(buffer)) != -1) {
            gzip.write(buffer, 0, len);
        }

        fis.close();
        gzip.close();
        fos.close();

        return new File(outputPath);
    }

    public static void main(String[] args) throws IOException {
        File file = new File("C:/Users/SUBHENDU/Desktop/COMPRESSOR_DECOMPRESSOR/src/comp_decomp/test1.mp4");
        File compressed = compressIt(file);
        System.out.println("Compressed File Path: " + compressed.getAbsolutePath());
    }
}
