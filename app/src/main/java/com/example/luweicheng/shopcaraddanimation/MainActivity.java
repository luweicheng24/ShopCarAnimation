package com.example.luweicheng.shopcaraddanimation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyleView;
    static ImageView shopCar;
    static TextView redCircle;
    ImageView message;


    private int[] images = {R.drawable.watch1, R.drawable.watch2, R.drawable.watch3,
            R.drawable.watch4, R.drawable.watch5, R.drawable.watch6,
            R.drawable.watch7, R.drawable.watch8, R.drawable.watch9, R.drawable.watch10, R.drawable.watch11, R.drawable.watch12};
    private RecycleViewAdapter adapter;
    private Context mContext;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        shopCar = (ImageView) findViewById(R.id.shopCar);
        redCircle = (TextView) findViewById(R.id.redCircle);
        mRecyleView = (RecyclerView) findViewById(R.id.mRecyleView);
        mContext = this;
        adapter = new RecycleViewAdapter(mContext, images);
        mRecyleView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        mRecyleView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int[] location = new int[2];
        shopCar.getLocationInWindow(location);
        Log.e(TAG, "onCreate: location ="+location[0]+":"+location[1]);
    }
}
