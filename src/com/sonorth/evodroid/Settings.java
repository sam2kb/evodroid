package com.sonorth.evodroid;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sonorth.evodroid.util.AppTitleBar;
import com.sonorth.evodroid.util.AppTitleBar.OnBlogChangedListener;


public class Settings extends Activity {
	protected static Intent svc = null;
	private String originalUsername;
	AppTitleBar titleBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.settings);
	
        
        titleBar = (AppTitleBar) findViewById(R.id.settingsActionBar);
        titleBar.refreshButton.setEnabled(false);
		titleBar.setOnBlogChangedListener(new OnBlogChangedListener() {
			// user selected new blog in the title bar
			public void OnBlogChanged() {
				
				loadSettingsForBlog();

			}
		});
		  
        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	
            	 Bundle bundle = new Bundle();
                 
                 bundle.putString("returnStatus", "CANCEL");
                 Intent mIntent = new Intent();
                 mIntent.putExtras(bundle);
                 setResult(RESULT_CANCELED, mIntent);
                 finish();
            }
        });
        
        loadSettingsForBlog();
        
	}
	
	@Override
	protected void onNewIntent (Intent intent){
		super.onNewIntent(intent);
		
		titleBar.refreshBlog();
		loadSettingsForBlog();
		
	}
	
	private void loadSettingsForBlog() {
		Spinner spinner = (Spinner)this.findViewById(R.id.maxImageWidth);
	    ArrayAdapter<Object> spinnerArrayAdapter = new ArrayAdapter<Object>(this,
	    		R.layout.spinner_textview,
	            new String[] { "Original Size", "100", "200", "300", "400", "500", "600", "700", "800", "900", "1000"});
	    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(spinnerArrayAdapter);

    	EditText usernameET = (EditText)findViewById(R.id.username);
    	usernameET.setText(b2evolution.currentBlog.getUsername());
    	originalUsername = b2evolution.currentBlog.getUsername();

    	EditText passwordET = (EditText)findViewById(R.id.password);
    	passwordET.setText(b2evolution.currentBlog.getPassword());

    	EditText httpUserET = (EditText)findViewById(R.id.httpuser);
    	httpUserET.setText(b2evolution.currentBlog.getHttpuser());

    	EditText httpPasswordET = (EditText)findViewById(R.id.httppassword);
    	httpPasswordET.setText(b2evolution.currentBlog.getHttppassword());
    	TextView httpPasswordLabel = (TextView) findViewById(R.id.l_httppassword);
		TextView httpUserLabel = (TextView) findViewById(R.id.l_httpuser);
		httpPasswordLabel.setVisibility(View.VISIBLE);
		httpPasswordET.setVisibility(View.VISIBLE);

		httpUserLabel.setVisibility(View.VISIBLE);
		httpUserET.setVisibility(View.VISIBLE);

    	CheckBox fullSize = (CheckBox)findViewById(R.id.fullSizeImage);
    	fullSize.setChecked(b2evolution.currentBlog.isFullSizeImage());
    	CheckBox scaledImage = (CheckBox)findViewById(R.id.scaledImage);
    	scaledImage.setChecked(b2evolution.currentBlog.isScaledImage());
    	EditText scaledImageWidth = (EditText)findViewById(R.id.scaledImageWidth);
    	scaledImageWidth.setText(""+b2evolution.currentBlog.getScaledImageWidth());
    	showScaledSetting(b2evolution.currentBlog.isScaledImage());
    	//sets up a state listener for the scaled image checkbox
        ((CheckBox)findViewById(R.id.scaledImage)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox scaledImage = (CheckBox)findViewById(R.id.scaledImage);
				showScaledSetting(scaledImage.isChecked());
				if(scaledImage.isChecked()){
					CheckBox fullSize = (CheckBox)findViewById(R.id.fullSizeImage);
					fullSize.setChecked(false);
				}
			}
        });
        //sets up a state listener for the fullsize checkbox
        ((CheckBox)findViewById(R.id.fullSizeImage)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox fullSize = (CheckBox)findViewById(R.id.fullSizeImage);
				if(fullSize.isChecked()){
					CheckBox scaledImage = (CheckBox)findViewById(R.id.scaledImage);
					if(scaledImage.isChecked()){
						scaledImage.setChecked(false);
						showScaledSetting(false);
					}
				}
			}
        });
    	//don't show location option for devices that have no location support.
    	boolean hasLocationProvider = false;
    	LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    	List<String> providers = locationManager.getProviders(true);
    	for (String providerName:providers) {
    	  if (providerName.equals(LocationManager.GPS_PROVIDER) 
    	      || providerName.equals(LocationManager.NETWORK_PROVIDER)) {
    	    hasLocationProvider = true;
    	  }
    	}
    	
    	CheckBox locationCB = (CheckBox)findViewById(R.id.location);
    	if (hasLocationProvider) {
    		locationCB.setChecked(b2evolution.currentBlog.isLocation());
    	} else {
    		locationCB.setChecked(false);
    		RelativeLayout locationLayout = (RelativeLayout) findViewById(R.id.section3);
    		locationLayout.setVisibility(View.GONE);
    	}

    	spinner.setSelection(b2evolution.currentBlog.getMaxImageWidthId());


        final Button saveButton = (Button) findViewById(R.id.save);
        
        saveButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	// TODO sam2kb> refresh blog contents if username has changed
            	
                //capture the entered fields *needs validation*
                EditText usernameET = (EditText)findViewById(R.id.username);
                b2evolution.currentBlog.setUsername(usernameET.getText().toString());
                EditText passwordET = (EditText)findViewById(R.id.password);
                b2evolution.currentBlog.setPassword(passwordET.getText().toString());
                EditText httpuserET = (EditText)findViewById(R.id.httpuser);
                b2evolution.currentBlog.setHttpuser(httpuserET.getText().toString());
                EditText httppasswordET = (EditText)findViewById(R.id.httppassword);
                b2evolution.currentBlog.setHttppassword(httppasswordET.getText().toString());
                
                CheckBox fullSize = (CheckBox)findViewById(R.id.fullSizeImage);
                b2evolution.currentBlog.setFullSizeImage(fullSize.isChecked());
                CheckBox scaledImage = (CheckBox)findViewById(R.id.scaledImage);
                b2evolution.currentBlog.setScaledImage(scaledImage.isChecked());
                if(b2evolution.currentBlog.isScaledImage()){
                	EditText scaledImgWidth = (EditText)findViewById(R.id.scaledImageWidth);
                	
                	boolean error = false;
                	int width = 0;
                	try {
                		width = Integer.parseInt(scaledImgWidth.getText().toString().trim());
                	} catch (NumberFormatException e) {
						error = true;
					}
                	
                	if (width == 0)
                		error = true;
                	
                	if (error) {
                		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Settings.this);
						  dialogBuilder.setTitle(getResources().getText(R.string.error));
			            dialogBuilder.setMessage(getResources().getText(R.string.scaled_image_error));
			            dialogBuilder.setPositiveButton("OK",  new
			          		  DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int whichButton) {
			                }
			            });
			            dialogBuilder.setCancelable(true);
			           dialogBuilder.create().show();
			           return;
                	} else {
                		b2evolution.currentBlog.setScaledImageWidth(width);
                	}
                }
                Spinner spinner = (Spinner)findViewById(R.id.maxImageWidth);
                b2evolution.currentBlog.setMaxImageWidth(spinner.getSelectedItem().toString());
                
                long maxImageWidthId = spinner.getSelectedItemId();
                int maxImageWidthIdInt = (int) maxImageWidthId;
                
                b2evolution.currentBlog.setMaxImageWidthId(maxImageWidthIdInt);
                
                CheckBox locationCB = (CheckBox)findViewById(R.id.location);
                b2evolution.currentBlog.setLocation(locationCB.isChecked());

                b2evolution.currentBlog.save(Settings.this, originalUsername);
        		//exit settings screen
                Bundle bundle = new Bundle();
                
                bundle.putString("returnStatus", "SAVE");
                Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
            }
        }); 
		
	}
	
	/**
	 * Hides / shows the scaled image settings
	 * @param show
	 */
	private void showScaledSetting(boolean show){
		TextView tw = (TextView)findViewById(R.id.l_scaledImage);
		EditText et = (EditText)findViewById(R.id.scaledImageWidth);
		tw.setVisibility(show?View.VISIBLE:View.GONE);
		et.setVisibility(show?View.VISIBLE:View.GONE);
	}

	@Override
    public void onConfigurationChanged(Configuration newConfig) {
      //ignore orientation change
      super.onConfigurationChanged(newConfig);
    } 
	
}
