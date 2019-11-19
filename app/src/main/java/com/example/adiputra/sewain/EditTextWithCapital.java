package com.example.adiputra.sewain;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class EditTextWithCapital extends AppCompatEditText {
    Drawable mCapitalButtonImage;

    public EditTextWithCapital(Context context) {
        super(context);
        init();
    }
    public EditTextWithCapital(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public EditTextWithCapital(Context context,
                             AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCapitalButtonImage = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_content_paste_black_24dp, null);
        // If the clear (X) button is tapped, clear the text.
        setOnTouchListener(new OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((getCompoundDrawablesRelative()[2] != null)) {
                    float clearButtonStart; // Used for LTR languages
                    float clearButtonEnd;  // Used for RTL languages
                    boolean isClearButtonClicked = false;
                    // Detect the touch in RTL or LTR layout direction.
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        // If RTL, get the end of the button on the left side.
                        clearButtonEnd = mCapitalButtonImage
                                .getIntrinsicWidth() + getPaddingStart();
                        // If the touch occurred before the end of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() < clearButtonEnd) {
                            isClearButtonClicked = true;
                        }
                    } else {
                        // Layout is LTR.
                        // Get the start of the button on the right side.
                        clearButtonStart = (getWidth() - getPaddingEnd()
                                - mCapitalButtonImage.getIntrinsicWidth());
                        // If the touch occurred after the start of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() > clearButtonStart) {
                            isClearButtonClicked = true;
                        }
                    }

                    // Check for actions if the button is tapped.
                    if (isClearButtonClicked) {
                        // Check for ACTION_DOWN (always occurs before ACTION_UP).
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            // Switch to the black version of clear button.
                            mCapitalButtonImage =
                                    ResourcesCompat.getDrawable(getResources(),
                                            R.drawable.ic_content_paste_black_24dp, null);
                            showClearButton();
                        }
                        // Check for ACTION_UP.
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            // Switch to the opaque version of clear button.
                            mCapitalButtonImage =
                                    ResourcesCompat.getDrawable(getResources(),
                                            R.drawable.ic_content_paste_black_24dp, null);
                            // Clear the text and hide the clear button.
                            getText().clear();
                            getText().append("adi@gmail.com");
                            hideClearButton();
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

        // TODO: If the text changes, show or hide the clear (X) button.
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Shows the clear (X) button.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,                      // Start of text.
                        null,               // Above text.
                        mCapitalButtonImage,  // End of text.
                        null);              // Below text.
    }

    /**
     * Hides the clear button.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,             // Start of text.
                        null,      // Above text.
                        null,      // End of text.
                        null);     // Below text.
    }
}
