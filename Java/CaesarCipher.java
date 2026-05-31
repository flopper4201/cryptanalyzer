public class CaesarCipher {
    public static final String ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя.,«»\"':-!? ";

    public String encrypt(String text, int key) {
        return shiftText(text, key);
    }

    public String decrypt(String text, int key) {
        return shiftText(text, -key);
    }

    private String shiftText(String text, int key) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = Character.toLowerCase(text.charAt(i));
            int oldIndex = ALPHABET.indexOf(currentChar);

            if (oldIndex == -1) {
                result.append(text.charAt(i));
            } else {
                int newIndex = (oldIndex + key) % ALPHABET.length();

                if (newIndex < 0) {
                    newIndex += ALPHABET.length();
                }

                result.append(ALPHABET.charAt(newIndex));
            }
        }

        return result.toString();
    }
}