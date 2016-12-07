package com.hme.turman.ui.activity;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ListView;

import com.eftimoff.androipathview.PathView;
import com.hme.turman.R;
import com.hme.turman.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;

/**
 * Created by diaoqf on 2016/11/18.
 */

public class LaunchActivity extends BaseActivity {
    @BindView(R.id.path_view)
    PathView path_view;

    @Override
    protected void init(Bundle savedInstanceState) {
        path_view.setPaths(makeH(150, 150));
        path_view.setPath(makeCircle(150,150,140));
        path_view.setFillAfter(true);
        path_view.useNaturalColors();

        path_view.getPathAnimator().
                delay(100).
                duration(1500).
                interpolator(new AccelerateDecelerateInterpolator()).
                start();

        addTask(Observable.timer(3, TimeUnit.SECONDS).subscribe(result->{
            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }));
    }

    private List<Path> makeH(float length, float height) {
        final List<Path> paths = new ArrayList<>();

        Path path1 = new Path();
        path1.moveTo(length,height);
        path1.lineTo(length-50,height);
        path1.lineTo(length-50,height+70);
        paths.add(path1);

        Path path2 = new Path();
        path2.moveTo(length,height);
        path2.lineTo(length+50,height);
        path2.lineTo(length+50,height+70);
        paths.add(path2);

        Path path3 = new Path();
        path3.moveTo(length-50,height);
        path3.lineTo(length-50,height - 70);
        paths.add(path3);

        Path path4 = new Path();
        path4.moveTo(length+50,height);
        path4.lineTo(length+50,height - 70);
        paths.add(path4);

        return paths;
    }

    private Path makeCircle(float x, float y, float radius) {
        final Path path = new Path();
        path.moveTo(1.0f,1.0f);
        path.addCircle(x,y,radius, Path.Direction.CCW);
        path.close();
        return path;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.act_launch;
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }
}
