package com.arvatoservice.arvatoshopping;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.arvatoservice.arvatoshopping.adapter.MiddlePagerAdapter;
import com.arvatoservice.arvatoshopping.beans.PushMsgBean;
import com.arvatoservice.arvatoshopping.db.PushMsgDBHelper;
import com.arvatoservice.arvatoshopping.utils.Constants;
import com.arvatoservice.arvatoshopping.utils.Utils;
import com.arvatoservice.arvatoshopping.view.CenterView;
import com.arvatoservice.arvatoshopping.view.MiddlePagerView;

public class MainActivity extends Activity {
	private MiddlePagerView pager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		pager = (MiddlePagerView) findViewById(R.id.mv);
		pager.initView(true, true, R.raw.sanfran);
		int direction = this.getIntent().getIntExtra(
				Constants.PUSH_INTENT_DIRECTION_KEY,
				Constants.PUSH_INTENT_DIRECTION_CENTER);
		if (direction == Constants.PUSH_INTENT_DIRECTION_BOTTOM) {
			pager.post(new Runnable() {

				@Override
				public void run() {
					goBottomView();
				}

			});
		}
		if (savedInstanceState != null) {
			pager.setCurrentItem(savedInstanceState.getInt("current_page"),
					false);
		}
		if (Utils.isFirstStart(this)) {
			initData();
			Utils.setFirstStartCompleted(this);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (pager != null) {
			outState.putInt("current_page", pager.getCurrentItem());
		}
	}

	private void goBottomView() {
		MiddlePagerAdapter ma = ((MiddlePagerAdapter) pager.getAdapter());
		((CenterView) ma.getCenterView()).goBottom();
	}

	@Override
	protected void onDestroy() {
		((MyApplication) this.getApplication()).getImageManager()
				.recycleBitmaps();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		((MyApplication) this.getApplication()).getImageManager().freeMem();
		super.onPause();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Constants.LOADING_DIALOG:
			ProgressDialog loadingDialog = new ProgressDialog(this);
			loadingDialog
					.setMessage(getString(R.string.loading_loading_dialog_info));
			loadingDialog.setIndeterminate(true);
			loadingDialog.setCancelable(true);
			loadingDialog.setCanceledOnTouchOutside(false);
			return loadingDialog;
		}
		return null;
	}
	
	private void initData(){
		PushMsgDBHelper pmdbHelper = new PushMsgDBHelper(this);
		PushMsgBean pmBean = new PushMsgBean(
				Constants.PUSH_MSG_TYPE_PRODUCT,
				System.currentTimeMillis(),
				getResources().getString(R.string.push_msg_type_product),
				"麦乐鸡",
				1,
				"http://images.ccoo.cn/es_product/2011317/201131713305167.jpg");
		pmdbHelper.insert(pmBean);
		pmBean = new PushMsgBean(
				Constants.PUSH_MSG_TYPE_COUPON,
				System.currentTimeMillis(),
				getResources().getString(R.string.push_msg_type_coupon),
				"巨无霸买一送一",
				2,
				"http://www.openrice.com/UserPhoto/photo/0/QQ/005A2517B8F9A0A6DA6E40m.jpg");
		pmdbHelper.insert(pmBean);
		pmBean = new PushMsgBean(
				Constants.PUSH_MSG_TYPE_INFO,
				System.currentTimeMillis(),
				getResources().getString(R.string.push_msg_type_info),
				"麦当劳大宁店已入驻",
				3,
				"http://pic1a.nipic.com/2008-08-26/2008826205334663_2.jpg");
		pmdbHelper.insert(pmBean);
	}
}
