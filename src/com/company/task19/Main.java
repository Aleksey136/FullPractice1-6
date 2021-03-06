package com.company.task19;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String srcFolder = "src/com/company/task19/images";
        String dstFolder = "src/com/company/task19/newImages";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        System.out.println("Количество потоков: " + Runtime.getRuntime().availableProcessors());
        File[] files = srcDir.listFiles();
        try
        {
            if (!Files.exists(Paths.get(dstFolder)))
            {
                Files.createDirectories(Paths.get(dstFolder));
            }
            int quantityOfCores = Runtime.getRuntime().availableProcessors();
            assert files != null;
            if (Runtime.getRuntime().availableProcessors() > files.length)
            {
                System.out.println("Количество потоков больше, чем количество фотографий, поэтому будет использоваться меньше потоков.");
                quantityOfCores = files.length;
            }
            int i1 = 0;
            int i2 = files.length/quantityOfCores;
            while (i2 < files.length + files.length/quantityOfCores){
                File[] files1 = new File[i2-i1];
                System.arraycopy(files, i1, files1, 0, files1.length);
                i1+= files.length/quantityOfCores;
                i2+= files.length/quantityOfCores;
                Thread resize = new Thread(new ImageResize(files1, dstFolder, start));
                resize.start();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
