package com.example.luweicheng.shopcaraddanimation;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by luweicheng on 2016/12/21.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private Context mContext;
    private int[] images;

    public RecycleViewAdapter(Context mContext, int[] images) {
        this.mContext = mContext;
        this.images = images;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recy_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.img.setImageResource(images[position]);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] startLocation = new int[2];
                holder.img.getLocationInWindow(startLocation);
                ImageView ball = new ImageView(mContext);
                ball.setScaleType(ImageView.ScaleType.FIT_XY);
                ball.setImageResource(images[position]);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ball.setLayoutParams(lp);
                setAnim(ball, startLocation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }

    /**
     * 创建回调接口
     */
    public interface OnItemClickListener {
        void imgClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 创建执行的动画层
     *
     * @return 动画的执行的ViewGroup
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) ((Activity) mContext).getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 添加执行动画的view的布局
     *
     * @param view          执行动画的View
     * @param startLocation 动画的开始位置
     * @return 执行动画的view
     */
    private View addViewToAnimLayout(View view, int[] startLocation) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = startLocation[0];
        lp.topMargin = startLocation[1];
        view.setLayoutParams(lp);
        return view;
    }

    private ViewGroup anim_layout;
    private static final String TAG = "RecycleViewAdapter";
    private int n = 0;

    /**
     * 执行动画
     *
     * @param view          执行动画的View
     * @param startLocation 执行动画的起始位置坐标
     */
    private void setAnim(final View view, int[] startLocation) {
        anim_layout = createAnimLayout();//创建动画执行层
        anim_layout.addView(view);//将动画小球添加到执行层
        final View ballView = addViewToAnimLayout(view, startLocation);
        int[] endLocation = new int[2];
        MainActivity.shopCar.getLocationInWindow(endLocation);

        int animLocationX = endLocation[0] - startLocation[0] + 80;
        int animLocatiopnY = endLocation[1] - startLocation[1] + 40;
        Log.e(TAG, "setAnim: X=" + animLocationX + ":Y=" + animLocatiopnY);
        /**
         * 创建动画
         */
        //左右移动画
        TranslateAnimation translateAnimationX = new TranslateAnimation(0, animLocationX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);
        //上下移动画
        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, animLocatiopnY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.3f, 0.1f, 0.3f, 0.1f);
        scaleAnimation.setDuration(1);
        scaleAnimation.setFillAfter(true);

        //动画集合
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimationX);
        animationSet.addAnimation(translateAnimationY);
        animationSet.setDuration(400);
        ballView.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ballView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ballView.setVisibility(View.GONE);
                MainActivity.redCircle.setText(++n + "");
                /**
                 * 动画结束，下面可以执行向数据库添加购买的货物
                 */
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
