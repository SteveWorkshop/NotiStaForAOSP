package io.github.materialapps.notistar.util;

import android.content.DialogInterface;

import java.lang.reflect.Field;

//todo:好像某些组件失效
public class DialogUtil {
    public static void canCloseDialog(DialogInterface dialogInterface, boolean close) {
        try {
            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialogInterface, close);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
