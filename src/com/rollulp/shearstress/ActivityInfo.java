package com.rollulp.shearstress;

import java.util.Locale;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ImageView;

public class ActivityInfo extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if ( ! Locale.getDefault().getLanguage().equalsIgnoreCase("it") ) {
			ImageView im;
			im = (ImageView)findViewById(R.id.img001);
			im.setImageResource(R.drawable.info_1_en);
			im = (ImageView)findViewById(R.id.img002);
			im.setImageResource(R.drawable.info_2_en);
			im = (ImageView)findViewById(R.id.img003);
			im.setImageResource(R.drawable.info_3_en);
			im = (ImageView)findViewById(R.id.img004);
			im.setImageResource(R.drawable.info_4_en);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
 			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
}
