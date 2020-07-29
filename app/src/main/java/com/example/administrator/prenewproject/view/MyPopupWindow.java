package com.example.administrator.prenewproject.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.prenewproject.utils.ScreenUtils;

/**
 * Created by maguotang on 2019/4/9
 */
public abstract class MyPopupWindow {
    private final View contentView;
    private final PopupWindow mInstance;
    private Context context;

    public MyPopupWindow(Context context, int layoutRes) {

        this.context=context;
        contentView=LayoutInflater.from(context).inflate(layoutRes, null, false);
        initView(contentView);

        mInstance=new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        initWindow();
    }

    protected void initWindow() {
        mInstance.setFocusable(true);
        mInstance.setOutsideTouchable(true);
        mInstance.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }
    protected abstract void initView(View view);

    public void showBashOfAnchor(View anchor, LayoutGravity layoutGravity, int xmerge, int ymerge) {
        int[] offset=layoutGravity.getOffset(anchor, mInstance);
        mInstance.showAsDropDown(anchor, offset[0]+xmerge, offset[1]+ymerge);
    }


    public void showAsDropDown(View anchor, int xoff, int yoff) {
        mInstance.showAsDropDown(anchor, xoff, yoff);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        mInstance.showAtLocation(parent, gravity, x, y);
    }

    public void popuDiss(){
        mInstance.dismiss();
    }


    public static class LayoutGravity {
        private int layoutGravity;
        // waring, don't change the order of these constants!
        public static final int ALIGN_LEFT=0x1;
        public static final int ALIGN_ABOVE=0x2;
        public static final int ALIGN_RIGHT=0x4;
        public static final int ALIGN_BOTTOM=0x8;
        public static final int TO_LEFT=0x10;
        public static final int TO_ABOVE=0x20;
        public static final int TO_RIGHT=0x40;
        public static final int TO_BOTTOM=0x80;
        public static final int CENTER_HORI=0x100;
        public static final int CENTER_VERT=0x200;

        public LayoutGravity(int gravity) {
            layoutGravity=gravity;
        }

        public int getLayoutGravity() { return layoutGravity; }
        public void setLayoutGravity(int gravity) { layoutGravity=gravity; }

        public void setHoriGravity(int gravity) {
            layoutGravity&=(0x2+0x8+0x20+0x80+0x200);
            layoutGravity|=gravity;
        }
        public void setVertGravity(int gravity) {
            layoutGravity&=(0x1+0x4+0x10+0x40+0x100);
            layoutGravity|=gravity;
        }

        public boolean isParamFit(int param) {
            return (layoutGravity & param) > 0;
        }

        public int getHoriParam() {
            for(int i=0x1; i<=0x100; i=i<<2)
                if(isParamFit(i))
                    return i;
            return ALIGN_LEFT;
        }

        public int getVertParam() {
            for(int i=0x2; i<=0x200; i=i<<2)
                if(isParamFit(i))
                    return i;
            return TO_BOTTOM;
        }

        public int[] getOffset(View anchor, PopupWindow window) {
            int anchWidth=anchor.getWidth();
            int anchHeight=anchor.getHeight();

            int winWidth=window.getWidth();
            int winHeight=window.getHeight();
            View view=window.getContentView();
            if(winWidth<=0)
                winWidth=view.getWidth();
            if(winHeight<=0)
                winHeight=view.getHeight();

            int xoff=0;
            int yoff=0;

            switch (getHoriParam()) {
                case ALIGN_LEFT:
                    xoff=0; break;
                case ALIGN_RIGHT:
                    xoff=anchWidth-winWidth; break;
                case TO_LEFT:
                    xoff=-winWidth; break;
                case TO_RIGHT:
                    xoff=anchWidth; break;
                case CENTER_HORI:
                    xoff=(anchWidth-winWidth)/2; break;
                default:break;
            }
            switch (getVertParam()) {
                case ALIGN_ABOVE:
                    yoff=-anchHeight; break;
                case ALIGN_BOTTOM:
                    yoff=-winHeight; break;
                case TO_ABOVE:
                    yoff=-anchHeight-winHeight; break;
                case TO_BOTTOM:
                    yoff=0; break;
                case CENTER_VERT:
                    yoff=(-winHeight-anchHeight)/2; break;
                default:break;
            }
            return new int[]{ xoff, yoff };
        }
    }


    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     * @param anchorView  呼出window的view
     * @param contentView   window的内容布局
     * @return window显示的左上角的xOff,yOff坐标
     */
    public int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }





}
