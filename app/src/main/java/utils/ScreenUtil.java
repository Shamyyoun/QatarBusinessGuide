package utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class ScreenUtil {
    public static int getWidth(Activity activity) {
        return getScreenDimensions(activity).x;
    }

    public static int getHeight(Activity activity) {
        return getScreenDimensions(activity).y;
    }

    private static Point getScreenDimensions(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

}
