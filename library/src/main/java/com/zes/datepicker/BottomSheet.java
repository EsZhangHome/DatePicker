package com.zes.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

public class BottomSheet extends Dialog implements DialogInterface.OnCancelListener,
        DialogInterface.OnDismissListener, View.OnClickListener {

    private View mContentView;// dialog content view
    private String mTitleText;
    private ImageView mLeftBtn;
    private ImageView mRightBtn;
    private TextView mTitleTv;
    private View mLine;
    private View.OnClickListener mLeftBtnClickListener;
    private View.OnClickListener mRightBtnClickListener;

    private OnDialogCloseListener mOnDismissListener;

    private View mTitleLayout;
    private boolean clearFlag = false;

    public BottomSheet(Context context) {
        super(context, R.style.CommonDialog);
        this.clearFlag = clearFlag;
        init();
    }

    public BottomSheet(Context context, boolean clearFlag) {
        super(context, R.style.CommonDialog2);
        this.clearFlag = clearFlag;
        init();
    }


    public BottomSheet(Context context, int themeResId) {
        super(context, R.style.CommonDialog);
        init();
    }

    protected BottomSheet(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    protected BottomSheet(Context context, boolean clearFlag, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.clearFlag = clearFlag;
        init();
    }

    private void init() {
        setCanceledOnTouchOutside(true);
        Window dialogWindow = getWindow();
        setContentView(R.layout.bottom_sheet_dialog);
        dialogWindow.setWindowAnimations(R.style.BottomSheetAnim);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.dimAmount = 0.5f;
        if (clearFlag) {
            dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        dialogWindow.setAttributes(params);
        dialogWindow.setLayout(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.BOTTOM);
        mLine = dialogWindow.findViewById(R.id.back_line);
        mTitleLayout = dialogWindow.findViewById(R.id.title);
        mLeftBtn = (ImageView) dialogWindow.findViewById(R.id.left_btn);
        mRightBtn = (ImageView) dialogWindow.findViewById(R.id.right_btn);
        mTitleTv = (TextView) dialogWindow.findViewById(R.id.middle_txt);

        mLeftBtn.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);

        setOnCancelListener(this);
        setOnDismissListener(this);
    }

    public void setTitleVisibility(int visibility) {
        if (mTitleLayout != null) {
            mTitleLayout.setVisibility(visibility);
        }
    }

    public void setContent(View content) {
        ((FrameLayout) getWindow().findViewById(R.id.content_dialog)).addView(content);
    }

    public void setContent(int contentId) {
        View content = getLayoutInflater().inflate(contentId, null);
        ((FrameLayout) getWindow().findViewById(R.id.content_dialog)).addView(content);
    }

    public void setContent(View content, FrameLayout.LayoutParams params) {
        if (params instanceof FrameLayout.LayoutParams) {
            ((FrameLayout) getWindow().findViewById(R.id.content_dialog)).addView(content, params);
        } else {
            ((FrameLayout) getWindow().findViewById(R.id.content_dialog)).addView(content);
        }
    }

    public void setTitleBackground(@ColorInt int color) {
        if (mTitleLayout != null) {
            mTitleLayout.setBackgroundColor(color);
        }
    }

    public void setLineColor(@ColorInt int color) {
        if (mLine != null) {
            mLine.setBackgroundColor(color);
        }
    }

    public void setTitleHeight(int height) {
        if (height <= 0) {
            //throw new IllegalArgumentException("height must > 0");
            return;
        }

        if (mTitleLayout != null) {
            ViewGroup.LayoutParams params = mTitleLayout.getLayoutParams();

            LinearLayout.LayoutParams titleParams = (LinearLayout.LayoutParams) params;
            titleParams.height = height;
        }
    }

    public void setMiddleText(String text) {
        mTitleTv.setText(text);
    }

    public void setMiddleTextColor(@ColorInt int color) {
        mTitleTv.setTextColor(color);
    }

    public void setTitleTextSize(int middleTextSize) {
        if (middleTextSize > 0) {
            mTitleTv.setTextSize(middleTextSize);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left_btn) {
            dismiss();
            if (mLeftBtnClickListener != null) {
                mLeftBtnClickListener.onClick(v);
            }
        } else if (v.getId() == R.id.right_btn) {
            dismiss();
            if (mRightBtnClickListener != null) {
                mRightBtnClickListener.onClick(v);
            }
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mOnDismissListener != null) {
            mOnDismissListener.onCancel();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss();
        }
    }

    public void setLeftBtnClickListener(View.OnClickListener listener) {
        mLeftBtnClickListener = listener;
    }

    public void setRightBtnClickListener(View.OnClickListener listener) {
        mRightBtnClickListener = listener;
    }

    public void setOnDismissListener(OnDialogCloseListener listener) {
        mOnDismissListener = listener;
    }

    public interface OnDialogCloseListener {
        public void onCancel();

        public void onDismiss();
    }
}
