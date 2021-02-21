package com.huangxy.abstractmvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;

import com.huangxy.abstractmvp.activity.LoginActivity2;
import com.huangxy.abstractmvp.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.constraint_layout)
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ConstraintSet mConstraintSet = new ConstraintSet();
        mConstraintSet.clone(this, R.layout.activity_main_anim);

        AutoTransition transition = new AutoTransition();
        transition.setDuration(1000);

        new Handler().postDelayed(() -> {
            //TODO  todo somthing here
            TransitionManager.beginDelayedTransition(constraintLayout, transition);
            mConstraintSet.applyTo(constraintLayout);
        },1000);
    }

    @OnClick({R.id.constraint_btn1, R.id.constraint_btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.constraint_btn1:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.constraint_btn2:
                startActivity(new Intent(MainActivity.this, LoginActivity2.class));
                break;
            default:
                break;
        }
    }
}