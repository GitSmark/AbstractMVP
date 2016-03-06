package com.ttt.common.mvp;

import android.view.View;

public interface CommonFragmentInterface {

	int inflateView();
	void initView(View view);
	void setListener();
}
