import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static String path = "C://Games";
    static String savegamsPath = "C://Games/savegames/";
    static String zipPath = "C://Games/savegames/";

    public static void main(String[] args) throws IOException {
       createDir(path + "/src");
        createDir(path + "/rec" );
        createDir(path + "/savegames" );
        createDir(path + "/temp");
       createDir(path + "/src/main");
        createDir(path + "/src/test");
        createFile(path + "/src/main/", "Main.java");
        createFile(path + "/src/main/", "Utils.java");
        createDir(path + "/res/drawables");
       createDir(path + "/res/vectors");
        createDir(path + "/res/icons");
        createFile(path + "/temp", "temp.java");


    }

    public static void createDir(String dirPath) {
        StringBuilder sb = new StringBuilder();
        File dir = new File(dirPath);
        String text = "Папка создана";
        sb.append(text);
        if (dir.mkdir()) {
            System.out.println(sb);
            try {
                FileWriter writer = new FileWriter(path + "/temp/temp.txt", true);
                writer.write(String.valueOf(sb));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void createFile(String dirPath, String fileName) {
        StringBuilder sb = new StringBuilder();
        File file = new File(dirPath, fileName);
        String text = "Файл" + fileName + "создан";
        sb.append(text);
        try {
            if (file.createNewFile()) {
                System.out.println(text);
                try {
                    FileWriter writer = new FileWriter(path + "/temp/temp.txt", true);
                    writer.write(String.valueOf(sb));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        GameProgress game1 = new GameProgress(10, 10, 10, 10);
        GameProgress game2 = new GameProgress(7, 7, 7, 7);
        GameProgress game3 = new GameProgress(5, 5, 5, 5);
        saveGame(savegamsPath + "save1.dat", game1);
        saveGame(savegamsPath + "save2.dat", game2);
        saveGame(savegamsPath + "save3.dat", game3);
        zipFiles(zipPath + "save.zip", savegamsPath);

        delete(savegamsPath + "save1.dat");
        delete(savegamsPath + "save2.dat");
        delete(savegamsPath + "save3.dat");
    }
    public static void saveGame(String savePath, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(savePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipPath, String savePath) {

        try (FileOutputStream fos = new FileOutputStream(zipPath); ZipOutputStream zos = new ZipOutputStream(fos)) {

            File dir = new File(savePath);
            for (File item : dir.listFiles()) {
                FileInputStream fis = new FileInputStream(savePath + item.getName());
                ZipEntry entry = new ZipEntry(item.getName());
                zos.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                zos.closeEntry();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void delete(String savePath) {
        File file = new File(savePath);
        if (file.delete()) {
            System.out.println("Незаархивированные файлы удалены");
        } else {
            System.out.println("Файлы не удалены");
        }
    }
}