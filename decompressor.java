package comp_decomp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class decompressor {
    public static File decompressIt(File file) throws IOException {
        String parent = file.getParent();
        String fileName = file.getName();

        // Remove .gz or .gzip extension to get original filename
        String outFileName;
        if (fileName.endsWith(".gz")) {
            outFileName = fileName.substring(0, fileName.length() - 3);
        } else if (fileName.endsWith(".gzip")) {
            outFileName = fileName.substring(0, fileName.length() - 5);
        } else {
            outFileName = "decompressed_" + fileName; // fallback
        }

        File outFile = new File(parent + File.separator + outFileName);

        FileInputStream fis = new FileInputStream(file);
        GZIPInputStream gzip = new GZIPInputStream(fis);
        FileOutputStream fos = new FileOutputStream(outFile);

        byte[] buffer = new byte[1024];
        int len;
        while ((len = gzip.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }

        fis.close();
        fos.close();
        gzip.close();

        return outFile; // Return the output file
    }

    //Just for testing purpose.
    public static void main(String[] args) throws IOException {
        File file = new File("C:/Users/SUBHENDU/Desktop/COMPRESSOR_DECOMPRESSOR/src/comp_decomp/file.txt.gz");
        decompressIt(file);
    }
}
