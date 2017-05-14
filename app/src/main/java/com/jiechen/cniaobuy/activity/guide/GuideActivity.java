package com.jiechen.cniaobuy.activity.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jiechen.cniaobuy.R;
import com.jiechen.cniaobuy.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导界面
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initData();
        initView();
        initAction();
    }

    // 小圆点之前的距离
    public static final int LENGTH_BETWEEN_DOT = 10 * 2;

    // button按钮距离底部距离
    public static final int LENGTH_BTN_BOTTOM_MARGIN = 100;

    // ViewPager
    private ViewPager viewPager;

    // 适配器
    private ViewPagerAdapter vpAdapter;

    // 引导页展示的view
    private List<View> views;

    // 底部小点
    private ImageView[] dots;

    // button按钮
    private Button btn;

    /**
     * 引导图片资源
     */
    private static final int[] pics = { R.drawable.guide_1, R.drawable.guide_4,
            R.drawable.guide_3, R.drawable.guide_2};

    /**
     * 记录当前选中位置
     */
    private int currentIndex;


    private void initView() {

        // 初始化按钮
        btn = new Button(this);
        RelativeLayout.LayoutParams btnLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        btnLayoutParams.bottomMargin = dp2px(GuideActivity.LENGTH_BTN_BOTTOM_MARGIN);
        btnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        btnLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        btn.setLayoutParams(btnLayoutParams);
        btn.setBackgroundResource(R.drawable.guide_btn);

        // 初始化引导图片列表
        views = new ArrayList<View>();
        for (int i = 0; i < pics.length; i++) {
            RelativeLayout fl = new RelativeLayout(this);
            RelativeLayout.LayoutParams rlparams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            fl.setLayoutParams(rlparams);
            fl.setBackgroundResource(pics[i]);
            if (i == pics.length - 1) {
                fl.addView(btn);
            }
            views.add(fl);
        }

        // 初始化ViewPager
        viewPager = (ViewPager) findViewById(R.id.welcome_guide_viewpager);
        vpAdapter = new ViewPagerAdapter(views);
        viewPager.setAdapter(vpAdapter);
    }

    protected void initAction() {
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                GuideActivity.this.startActivity(intent);
                GuideActivity.this.finish();
            }
        });

        // 绑定回调
        viewPager.setOnPageChangeListener(mOnPageChangeListener);
    }

    protected void initData() {
        // 初始化底部小点
        initDots();
    }

    /**
     * 初始化底部小点
     */
    private void initDots() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.welcome_guide_ll_dots);

        dots = new ImageView[pics.length];

        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.welcome_guide_dot);
            dots[i] = imageView;
            dots[i].setEnabled(true);// 都设为可用
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = GuideActivity.LENGTH_BETWEEN_DOT / 2;
            layoutParams.rightMargin = GuideActivity.LENGTH_BETWEEN_DOT / 2;
            viewGroup.addView(imageView, layoutParams);
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为不可用，即选中状态
    }

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length || currentIndex == position) {
            return;
        }

        viewPager.setCurrentItem(position);
    }

    /**
     * 设置当前引导小点的选中
     */
    private void setCurDot(int position) {
        if (position < 0 || position >= pics.length || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int arg0) {
            // 设置底部小点选中状态
            setCurDot(arg0);
        }

        // 当当前页面被滑动时调用
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    class ViewPagerAdapter extends PagerAdapter {

        // 界面列表
        private List<View> views;

        public ViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        // 销毁arg1位置的界面
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View)arg2);
        }

        // 获得当前界面数
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }

            return 0;
        }

        // 初始化arg1位置的界面
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1));
            return views.get(arg1);
        }

        // 判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }
    }

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }
}
