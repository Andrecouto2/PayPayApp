package br.com.andrecouto.paypay.helper;


public class ManagerHelper {

    private ManagerHelper() {}

    public static String nameInitialLettters(final String managerName) {

        final String[] nameArray = managerName.split(" ");

        if (nameArray.length == 0) {
            return "";
        }

        final String firstName = nameArray[0];

        if (nameArray.length > 1) {
            String initialLetters = "";

            if (firstName.length() > 0) {
                initialLetters += Character.toUpperCase(firstName.charAt(0));
            }

            final String secondName = nameArray[nameArray.length - 1];

            if (secondName.length() > 0) {
                initialLetters += Character.toUpperCase(secondName.charAt(0));
            }

            return initialLetters;
        }

        if (nameArray.length == 1 && firstName.length() > 0) {
            return nameWithOneCharacter(firstName);
        }

        return "";
    }

    private static String nameWithOneCharacter(String firstName) {
        if (firstName.length() == 1) {
            return firstName.toUpperCase();
        }
        return firstName.substring(0, 2).toUpperCase();
    }
}
