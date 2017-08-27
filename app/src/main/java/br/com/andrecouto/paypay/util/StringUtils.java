package br.com.andrecouto.paypay.util;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static final int IDENTIFICATION_LENGTH = 8;
    public static final int RECEIPT_LENGTH = 20;
    public static final int CHEQUE_LENGTH = 30;
    public static final int VOUCHER_COMMENT_LENGTH = 80;
    private static final String SPACE = " ";
    private static final List<DoNotCapitalize> DO_NOT_CAPITALIZES = new ArrayList<>();

    private StringUtils() {
    }

    public static String getNumberWithTwoDigits(int value) {
        return String.format("%02d", value);
    }

    public static String onlyNumbers(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("\\D+", "");
    }

    public static String getFirstWordAndCapitalize(String text) {
        return capitalize(getFirstWord(text));
    }

    private static String capitalize(String text) {
        if (text == null) {
            return null;
        }
        if (text.length() == 1) {
            return text.toUpperCase();
        }
        if (text.length() > 1) {
            return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
        }
        return "";
    }

    public static String getFirstWord(String text) {
        if (text == null) {
            return null;
        }
        if (text.contains(SPACE)) {
            return text.substring(0, text.indexOf(SPACE));
        }
        return text;
    }

    public static String getNameInitials(String name) {
        if (name == null) {
            return null;
        }
        if (name.length() == 1) {
            return name.toUpperCase();
        }

        String[] names = name.split(SPACE);

        String firstName = names[0];

        if (names.length > 1) {
            String lastName = names[names.length - 1];
            return getFirstLetter(firstName) + getFirstLetter(lastName);
        }

        return getFirstLetter(firstName);
    }

    private static String getFirstLetter(String text) {
        if (text == null) {
            return null;
        }
        if (text.length() == 1) {
            return text.toUpperCase();
        }
        if (text.length() > 1) {
            return text.substring(0, 1).toUpperCase();
        }

        return "";
    }

    public static boolean contains(String value, String part) {
        return !TextUtils.isEmpty(value) && !TextUtils.isEmpty(part) &&
                value.toLowerCase().contains(part.toLowerCase());
    }

    public static String capitalizeWords(final String text) {

        if (TextUtils.isEmpty(text)) {
            return text;
        }

        String word;
        DoNotCapitalize doNotCapitalize;
        String[] words = text.split(SPACE);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                builder.append(SPACE);
            }

            word = words[i];
            doNotCapitalize = getDoNotCapitalize(word);
            if (doNotCapitalize != null) {
                builder.append(doNotCapitalize.isToLowerCase() ? word.toLowerCase() : word);
            } else {
                builder.append(capitalize(word));
            }
        }

        return builder.toString();
    }

    private static List<DoNotCapitalize> getDoNotCapitalizes() {
        if (DO_NOT_CAPITALIZES.isEmpty()) {
            DO_NOT_CAPITALIZES.add(new DoNotCapitalize("da", true));
            DO_NOT_CAPITALIZES.add(new DoNotCapitalize("de", true));
            DO_NOT_CAPITALIZES.add(new DoNotCapitalize("do", true));
            DO_NOT_CAPITALIZES.add(new DoNotCapitalize("s.a"));
            DO_NOT_CAPITALIZES.add(new DoNotCapitalize("s.a."));
            DO_NOT_CAPITALIZES.add(new DoNotCapitalize("s/a"));
        }

        return DO_NOT_CAPITALIZES;
    }

    private static DoNotCapitalize getDoNotCapitalize(String value) {
        for (final DoNotCapitalize doNotCapitalize : getDoNotCapitalizes()) {
            if (doNotCapitalize.getWord().equalsIgnoreCase(value)) {
                return doNotCapitalize;
            }
        }

        return null;
    }

    public static String subStringIfNeeded(String value, int length) {
        String term = value;
        if (isntEmpty(term) && term.length() > length) {
            term = term.substring(term.length() - length);
        }
        return term;
    }

    public static List<String> splitString(String value, final int split) {
        if (value == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < value.length(); i = i + split) {
            int endindex = Math.min(i + split, value.length());
            list.add(value.substring(i, endindex));
        }
        return list;
    }

    public static String[] iterableToArray(Iterable<String> iter) {
        List<String> list = new ArrayList<>();
        for (String item : iter) {
            list.add(item);
        }
        return list.toArray(new String[list.size()]);
    }

    public static boolean isntEmpty(String string) {
        return string != null && string.trim().length() > 0;
    }

    public static String fillStringWithChar(String str, Character charToFill, int maxLenght) {
        String term = str;
        if (term.length() < maxLenght) {
            term = charToFill + term;
            return fillStringWithChar(term, charToFill, maxLenght);
        } else {
            return term;
        }
    }

    /**
     * hcolangelo - 18/03/2016
     * Method that handles LIS messages when they came with a code before the text.
     * Example: "[000] Verifique a existência...."
     */
    /*public static KeyValue handleLisMessage(String message) {
        if (message == null) {
            return new KeyValue("", "");
        }

        KeyValue lisMessage = new KeyValue("", message);
        if (!message.contains("[")) {
            return lisMessage;
        }
        lisMessage.setKey(message.substring(0, 5));
        lisMessage.setValue(message.substring(6));
        return lisMessage;
    }*/

    /*public static KeyValue handleWarningMessage(String message) {
        if (message == null) {
            return new KeyValue("", "");
        }

        KeyValue lisMessage = new KeyValue("", message);
        if (!message.contains("[")) {
            return lisMessage;
        }
        int end = message.indexOf("]");
        lisMessage.setKey(message.substring(0, end + 1));
        lisMessage.setValue(message.substring(end + 1));
        return lisMessage;
    }*/

    public static String removeAccents(String targetStr) {
        return Normalizer.normalize(targetStr, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static InputFilter generateSpecialCharactersInputFilter(@Nullable final String characterRegex) {

        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (isCharAllowed(c, characterRegex)) {
                        sb.append(c);
                    } else {
                        keepOriginal = false;
                    }
                }

                if (keepOriginal) {
                    return null;
                } else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }
            }

            private boolean isCharAllowed(char c, String characterRegex) {
                final String regex;

                if (TextUtils.isEmpty(characterRegex)) {
                    regex = "[a-zA-Z0-9., ]+";
                } else {
                    regex = characterRegex;
                }

                final Pattern compile = Pattern.compile(regex);
                Matcher matcher = compile.matcher(String.valueOf(c));

                return matcher.matches();
            }
        };
    }

    /*public static void setLengthAndSpecialCharacterFilter(MaterialEditText field, int fieldLength) {
        final InputFilter inputFilter = generateSpecialCharactersInputFilter(null);
        InputFilter lengthFilter = new InputFilter.LengthFilter(IDENTIFICATION_LENGTH);

        switch (fieldLength) {
            case RECEIPT_LENGTH:
                lengthFilter = new InputFilter.LengthFilter(RECEIPT_LENGTH);
                break;
            case CHEQUE_LENGTH:
                lengthFilter = new InputFilter.LengthFilter(CHEQUE_LENGTH);
                break;
            case VOUCHER_COMMENT_LENGTH:
                lengthFilter = new InputFilter.LengthFilter(VOUCHER_COMMENT_LENGTH);
                break;

            default:
                break;
        }
        field.setFilters(new InputFilter[]{inputFilter, lengthFilter});
    }

    public static void setLengthAndSpecialCharacterFilter(MaterialEditText field, int fieldLength, String regexCharacterAllowed) {
        final InputFilter inputFilter = generateSpecialCharactersInputFilter(regexCharacterAllowed);
        final InputFilter lengthFilter = new InputFilter.LengthFilter(fieldLength);
        field.setFilters(new InputFilter[]{inputFilter, lengthFilter});
    }*/

    public static String getFirstName(String name) {
        if (TextUtils.isEmpty(name)) {
            return "";
        }

        if (name.contains(" ")) {
            return name.substring(0, name.indexOf(" "));
        }

        return name;
    }

    /*public static String getFemaleLiteralNumber(Context context, int number) {
        if (number == 1) {
            return context.getString(R.string.notification_index_one);
        } else if (number == 2) {
            return context.getString(R.string.notification_index_two);
        } else {
            return Integer.toString(number);
        }
    }*/

    public static Spanned getHtmlSpannedText(@NonNull String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return getSpannedAndroidN(text);
        } else {
            //noinspection deprecation
            return getSpanned(text);
        }
    }

    private static Spanned getSpanned(@NonNull String text) {
        return Html.fromHtml(text);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Spanned getSpannedAndroidN(@NonNull String text) {
        return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
    }

    public static String getLastName(String name) {
        String lastName = "";

        if (TextUtils.isEmpty(name)) {
            return name;
        }

        String[] names = name.split(SPACE);
        if (names.length > 1) {
            lastName = names[names.length - 1];
        }
        return lastName;
    }

    /**
     * Trata a remoção de caracteres em campos formatados com máscaras, por exemplo Boleto, CPF,
     * CNPJ, etc
     *
     * @param editable campo preenchido
     * @param mask     máscara utilizada
     * @return StringBuilder atualizado
     */
    public static StringBuilder removeCaracterOnMaskedField(Editable editable, char[] mask) {
        final StringBuilder builder = new StringBuilder(editable);

        // Obtém a posição do último caracter excluído
        final int posicaoUltimoCaracter =
                mask.length > editable.length() ? editable.length() : mask.length - 1;

        // Verifica se o último caracter que foi excluído fazia parte da máscara
        final boolean ultimoCaracterEraMascara = mask[posicaoUltimoCaracter] != '#';

        // Se o último caracter excluido fazia parte da máscara, deve excluir até o primeiro
        // caracter que não faz parte da máscara
        if (ultimoCaracterEraMascara) {
            boolean encontrouCaracterValido = false;
            while (builder.length() > 0 && !encontrouCaracterValido) {
                encontrouCaracterValido = mask[builder.length() - 1] == '#';
                builder.deleteCharAt(builder.length() - 1);
            }
        }

        return builder;
    }

    public static boolean isEmpty(@Nullable String str) {
        return str == null || str.isEmpty();
    }

    public static String getFirstAndLastName(String fullName) {
        if (StringUtils.isntEmpty(fullName)) {
            String name = StringUtils.getFirstName(fullName).concat(" ").concat(StringUtils.getLastName(fullName));
            return StringUtils.capitalizeWords(name);
        }
        return "";
    }

    private static class DoNotCapitalize {
        private final String word;
        private final boolean toLowerCase;

        DoNotCapitalize(final String word) {
            this(word, false);
        }

        DoNotCapitalize(final String word, final boolean toLowerCase) {
            this.word = word;
            this.toLowerCase = toLowerCase;
        }

        String getWord() {
            return word;
        }

        public boolean isToLowerCase() {
            return toLowerCase;
        }
    }

    /***
     * Se a String for nula retorna "", em qualquer outro caso o valor não é alterado.
     * Útil especialmente pra acessibilidade.
     * @param str String a ser verificada e que receberá o valor de volta
     */
    public static String makeEmptyIfNull(@Nullable String str) {
        return str == null ? "" : str;
    }
}
