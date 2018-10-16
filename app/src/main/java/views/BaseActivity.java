package views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import net.smartinnovationtechnology.qbg.AppController;
import net.smartinnovationtechnology.qbg.R;
import net.smartinnovationtechnology.qbg.SearchActivity;

/**
 * Created by Shamyyoun on 3/11/2015.
 */
public class BaseActivity extends ActionBarActivity {
    private TextView textTitle;
    private ImageButton buttonIcon;
    private ImageButton buttonOpenSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // customize actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);
        View actionbarRootView = actionBar.getCustomView();
        textTitle = (TextView) actionbarRootView.findViewById(R.id.text_title);
        buttonIcon = (ImageButton) actionbarRootView.findViewById(R.id.button_icon);
        buttonIcon.setClickable(false);
        buttonOpenSearch = (ImageButton) actionbarRootView.findViewById(R.id.button_openSearch);
        buttonOpenSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.child_enter, R.anim.parent_exit);
            }
        });
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }

    public void setActionBarTitle(int stringRes) {
        textTitle.setText(getString(stringRes));
    }

    public void setActionbarIcon(int iconRes) {
        buttonIcon.setImageResource(iconRes);

        // check language
        if (iconRes == R.drawable.ic_back_white && AppController.getInstance(getApplicationContext()).getLang().equals("en")) {
            // reverse back button
            buttonIcon.setScaleX(-1);
        }
    }

    public void setActionBarIconClickListener(View.OnClickListener listener) {
        buttonIcon.setClickable(true);
        buttonIcon.setOnClickListener(listener);
    }

    public void removeOpenSearchIcon() {
        buttonOpenSearch.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.parent_enter, R.anim.child_exit);
    }
}
