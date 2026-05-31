import java.nio.file.Files;
import java.nio.file.Path;

public class Validator {

    public static void checkInputFile(String path) {
        if (!Files.exists(Path.of(path))) {
            throw new IllegalArgumentException("Файл не найден: " + path);
        }
    }

    public static void checkKey(int key) {
        if (key < 0 || key >= CaesarCipher.ALPHABET.length()) {
            throw new IllegalArgumentException(
                    "Ключ должен быть от 0 до " + (CaesarCipher.ALPHABET.length() - 1)
            );
        }
    }
}