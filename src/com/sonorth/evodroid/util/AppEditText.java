package com.sonorth.evodroid.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class AppEditText extends EditText {

	private EditTextImeBackListener mOnImeBack;
	private OnSelectionChangedListener onSelectionChangedListener;

	public AppEditText(Context context) {
		super(context);
	}

	public AppEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AppEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onSelectionChanged(int selStart, int selEnd) {
		if (onSelectionChangedListener != null)
			onSelectionChangedListener.onSelectionChanged();
	}

	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_UP) {
			clearFocus();
			if (mOnImeBack != null)
				mOnImeBack.onImeBack(this, this.getText().toString());
		}
		
		return super.onKeyPreIme(keyCode, event);
	}

	public void setOnEditTextImeBackListener(EditTextImeBackListener listener) {
		mOnImeBack = listener;
	}

	public interface EditTextImeBackListener {

		public abstract void onImeBack(AppEditText ctrl, String text);
	}
	
	public void setOnSelectionChangedListener(OnSelectionChangedListener listener) {
		onSelectionChangedListener = listener;
	}
	
	public interface OnSelectionChangedListener {
		public abstract void onSelectionChanged();
	}
}
