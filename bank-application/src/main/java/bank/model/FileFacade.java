package bank.model;

import java.io.File;
import java.io.PrintWriter;


public enum FileFacade {
    CUSTOMER(new File(FilePaths.CUSTOMER.getPath())),
    ACCOUNT(new File(FilePaths.ACCOUNT.getPath())),
    CREDIT(new File(FilePaths.CREDIT.getPath()));

    private final File file;

    FileFacade(File file) {
        this.file = file;
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }
}
