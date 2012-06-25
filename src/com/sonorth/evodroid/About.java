package com.sonorth.evodroid;

import com.sonorth.evodroid.util.BlackBerryUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class About extends Activity {
	final String app_author_url = "http://evodroid.sonorth.com";
	final String tos_url = "/tos";
	final String privacy_policy_url = "/privacy";

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.about);

		if ( BlackBerryUtils.getInstance().isPlayBook() ) {
			TextView appTitle = (TextView) findViewById(R.id.about_first_line);
			appTitle.setText(getResources().getText(R.string.app_title_playbook));
		}
		
		TextView version = (TextView) findViewById(R.id.about_version);
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo("com.sonorth.evodroid", 0);
			version.setText(getResources().getText(R.string.version) + " "
					+ pi.versionName);
		} catch (NameNotFoundException e) {
		}

		Button tos = (Button) findViewById(R.id.about_tos);
		tos.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Uri uri = Uri.parse(app_author_url + tos_url);
				startActivity(new Intent(Intent.ACTION_VIEW, uri));
			}
		});

		Button pp = (Button) findViewById(R.id.about_privacy);
		pp.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Uri uri = Uri.parse(app_author_url + privacy_policy_url);
				startActivity(new Intent(Intent.ACTION_VIEW, uri));
			}
		});
	}

	public void onClick(View v) {
		Uri uri = Uri.parse(app_author_url);
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
}
