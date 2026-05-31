import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CaesarCipher cipher = new CaesarCipher();

    public static void main(String[] args) {
        while (true) {
            printMenu();

            int choice = readInt("Выберите пункт меню: ");

            try {
                if (choice == 1) {
                    encryptFile();
                } else if (choice == 2) {
                    decryptFile();
                } else if (choice == 3) {
                    bruteForce();
                } else if (choice == 4) {
                    statisticalAnalysis();
                } else if (choice == 0) {
                    System.out.println("Программа завершена.");
                    break;
                } else {
                    System.out.println("Такого пункта меню нет.");
                }
            } catch (IOException e) {
                System.out.println("Ошибка работы с файлом: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("================================");
        System.out.println(" Caesar Cipher Tool by Malyshev ");
        System.out.println("================================");
        System.out.println("1. Зашифровать файл");
        System.out.println("2. Расшифровать файл с ключом");
        System.out.println("3. Brute force");
        System.out.println("4. Статистический анализ");
        System.out.println("0. Выход");
        System.out.println("================================");
    }

    private static void encryptFile() throws IOException {
        String inputPath = readString("Введите путь к исходному файлу: ");
        String outputPath = readString("Введите путь для результата: ");
        int key = readInt("Введите ключ: ");

        Validator.checkInputFile(inputPath);
        Validator.checkKey(key);

        String text = FileManager.readFile(inputPath);
        String encryptedText = cipher.encrypt(text, key);
        FileManager.writeFile(outputPath, encryptedText);

        System.out.println("Файл успешно зашифрован.");
    }

    private static void decryptFile() throws IOException {
        String inputPath = readString("Введите путь к зашифрованному файлу: ");
        String outputPath = readString("Введите путь для результата: ");
        int key = readInt("Введите ключ: ");

        Validator.checkInputFile(inputPath);
        Validator.checkKey(key);

        String text = FileManager.readFile(inputPath);
        String decryptedText = cipher.decrypt(text, key);
        FileManager.writeFile(outputPath, decryptedText);

        System.out.println("Файл успешно расшифрован.");
    }

    private static void bruteForce() throws IOException {
        String inputPath = readString("Введите путь к зашифрованному файлу: ");
        String outputPath = readString("Введите путь для результата: ");

        Validator.checkInputFile(inputPath);

        String text = FileManager.readFile(inputPath);
        StringBuilder result = new StringBuilder();

        for (int key = 0; key < CaesarCipher.ALPHABET.length(); key++) {
            result.append("Ключ ")
                    .append(key)
                    .append(": ")
                    .append(cipher.decrypt(text, key))
                    .append("\n\n");
        }

        FileManager.writeFile(outputPath, result.toString());

        System.out.println("Brute force завершён.");
    }

    private static void statisticalAnalysis() throws IOException {
        String inputPath = readString("Введите путь к зашифрованному файлу: ");
        String outputPath = readString("Введите путь для результата: ");

        Validator.checkInputFile(inputPath);

        String text = FileManager.readFile(inputPath);

        char mostFrequentChar = findMostFrequentLetter(text);
        int key = CaesarCipher.ALPHABET.indexOf(mostFrequentChar)
                - CaesarCipher.ALPHABET.indexOf('о');

        if (key < 0) {
            key += CaesarCipher.ALPHABET.length();
        }

        String decryptedText = cipher.decrypt(text, key);

        FileManager.writeFile(outputPath,
                "Предполагаемый ключ: " + key + "\n\n" + decryptedText);

        System.out.println("Статистический анализ завершён.");
    }

    private static char findMostFrequentLetter(String text) {
        int maxCount = 0;
        char mostFrequentChar = 'о';

        for (int i = 0; i < CaesarCipher.ALPHABET.length(); i++) {
            char currentChar = CaesarCipher.ALPHABET.charAt(i);

            if (!Character.isLetter(currentChar)) {
                continue;
            }

            int count = 0;

            for (int j = 0; j < text.length(); j++) {
                if (Character.toLowerCase(text.charAt(j)) == currentChar) {
                    count++;
                }
            }

            if (count > maxCount) {
                maxCount = count;
                mostFrequentChar = currentChar;
            }
        }

        return mostFrequentChar;
    }

    private static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private static int readInt(String message) {
        System.out.print(message);
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }
}