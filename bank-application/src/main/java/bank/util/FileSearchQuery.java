package bank.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public final class FileSearchQuery {

    private FileSearchQuery() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String> searchMultipleLines(String fileName, String... query) throws IOException {
        return Files.lines(Paths.get(fileName))
                .filter(line -> {
                    for (String s : query) {
                        if (!line.contains(s)) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    public static String search(String fileName, String name) {
        return FileReadOperation.read(fileName)
                .stream()
                .filter(line -> line.contains(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }

    private static class FileReadOperation {
        public static List<String> read(String fileName) {
            try {
                return Files.readAllLines(Paths.get(fileName));
            } catch (IOException e) {
                throw new RuntimeException("Dosya okunamadı");
            }
        }
    }

}
