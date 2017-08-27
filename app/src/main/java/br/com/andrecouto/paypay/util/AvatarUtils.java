package br.com.andrecouto.paypay.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;


public class AvatarUtils {
    private static final String PICTURE_DIRECTORY = "Imagens";
    private static final String AVATAR_PICTURE_NAME = "avatar.jpg";
    private static final String AVATAR_PICTURE_TEMP_NAME = "tempAvatar.jpg";
    private static File pictureDirectory;
    private static File avatarPicture;
    private static File avatarPictureTemp;

    private AvatarUtils() {}

    private static File getPictureDirectory(Context context) {
        if (pictureDirectory == null) {
            pictureDirectory = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), PICTURE_DIRECTORY);
            if (pictureDirectory.exists() && !pictureDirectory.mkdirs()) {
                return null;
            }
        }
        return pictureDirectory;
    }

    public static File getAvatarPictureTemp(Context context) {
        if (avatarPictureTemp == null) {
            avatarPictureTemp = loadPicture(context, AVATAR_PICTURE_TEMP_NAME);
        }

        return avatarPictureTemp;
    }

    public static File getAvatarPicture(Context context) {
        if (avatarPicture == null) {
            avatarPicture = loadPicture(context, AVATAR_PICTURE_NAME);
        }
        return avatarPicture;
    }

    private static File loadPicture(Context context, String picture) {
        return new File(getPictureDirectory(context), picture);
    }

    public static boolean existsAvatarPicture(Context context) {
        return getAvatarPicture(context).exists();
    }

    /*public static boolean deleteAvatarPicture(Context context) {
        return FileUtils.deleteFile(getAvatarPicture(context));
    }

    public static boolean deleteAvatarPictureTemp(Context context) {
        return FileUtils.deleteFile(getAvatarPictureTemp(context));
    }*/

}
