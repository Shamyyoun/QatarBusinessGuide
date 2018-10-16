package utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ViewUtil {
    /**
     * method used to show or hide view slide_in default duration 250
     */
    public static void showView(final View view, boolean show) {
        fadeView(view, show, 250);
    }

    /**
     * method used to show or hide view slide_in passed duration
     */
    public static void showView(final View view, boolean show, int animDuration) {
        fadeView(view, show, animDuration);
    }

    /**
     * method used to shows or hides a view with a smooth animation slide_in specific duration
     */
    private static void fadeView(final View view, final boolean show, int animDuration) {
        view.animate().setDuration(animDuration).alpha(show ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        if (show)
                            view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!show)
                            view.setVisibility(View.GONE);
                    }
                });
    }
}
