package nvdhai12.androiddev.fashionfusion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import nvdhai12.androiddev.fashionfusion.adapter.PhotoAdapter;
import nvdhai12.androiddev.fashionfusion.model.Photo;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private PhotoAdapter photoAdapter;
    private List<Photo> mListPhoto;
    private CountdownView countdownView;
    private long totalTimeInMillis = 6000000;
    private Handler handler = new Handler(Looper.myLooper());
    private ConstraintLayout flashSale;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = viewPager2.getCurrentItem();
            if (currentPosition == mListPhoto.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(currentPosition + 1);
            }
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownView = findViewById(R.id.countdownView);
        countdownView.start(totalTimeInMillis);

        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                flashSale.setVisibility(View.GONE);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.yellow));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            int flags = decorView.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(flags);
        }

        mListPhoto = getListPhoto();
        viewPager2 = findViewById(R.id.viewpager2);
        photoAdapter = new PhotoAdapter(mListPhoto);
        viewPager2.setAdapter(photoAdapter);
        flashSale = findViewById(R.id.flash_sale);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.img1));
        list.add(new Photo(R.drawable.img4));
        list.add(new Photo(R.drawable.img5));

        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
}