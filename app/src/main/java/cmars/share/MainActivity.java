package cmars.share;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.Locale;

import cmars.share.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public ObservableInt color = new ObservableInt(Color.GREEN);
    public ObservableField<String> text = new ObservableField<>();

    private GestureDetectorCompat gestureDetectorCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        createShareIntent();

        gestureDetectorCompat = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                color.set(Color.RED);
                share();
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                color.set(Color.YELLOW);

                text.set(String.format(Locale.getDefault(), "count = %d",++count));
                setShareIntent(text.get());

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    private Intent shareIntent;
    private void setShareIntent(String text) {
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
    }


    private int count;
    private Intent createShareIntent() {
        shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This is initial text to send.");
        shareIntent.setType("text/plain");
        return shareIntent;
    }

    private void share() {
        startActivity(shareIntent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuitem = menu.findItem(R.id.share);
        ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuitem);

        actionProvider.setShareIntent(shareIntent);

        return true;
    }
}
