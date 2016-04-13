package me.dineshkarpe.wikitude;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;
import java.io.IOException;
import me.dineshkarpe.hellowikitude.R;
/**
 *
 */
public class HelloWikitude extends AppCompatActivity {

	static final String TAG = HelloWikitude.class.getSimpleName();

	private ArchitectView architectView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.hellowikitude);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		this.architectView = (ArchitectView)this.findViewById(R.id.architectView);
		if(ActivityCompat.checkSelfPermission(HelloWikitude.this,
				Manifest.permission.CAMERA)
				!= PackageManager.PERMISSION_GRANTED){

			if (ActivityCompat.shouldShowRequestPermissionRationale(HelloWikitude.this,
					Manifest.permission.CAMERA)) {

				// Show an expanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.

				Log.i(TAG,
						"Displaying camera permission rationale to provide additional context.");
				Snackbar.make(architectView, "Camera permission is needed to show the camera preview.",
						Snackbar.LENGTH_INDEFINITE)
						.setAction("OK", new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								ActivityCompat.requestPermissions(HelloWikitude.this,
										new String[]{Manifest.permission.CAMERA,Manifest.permission.BLUETOOTH},
										0);
							}
						})
						.show();
			} else {

				// No explanation needed, we can request the permission.

				ActivityCompat.requestPermissions(this,
						new String[]{Manifest.permission.CAMERA},
						0);

				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		}else{
			loadArchitectView();
		}

	}

	@Override
	public void onResume() {

		try{
			super.onResume();

			this.architectView.onResume();

		}catch (NullPointerException e){
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {

		try{
			super.onDestroy();
		}catch (NullPointerException e){
		}
	}

	@Override
	protected void onPause() {
		try{
			super.onPause();

			this.architectView.onResume();
			this.architectView.onPause();

		}catch (NullPointerException e){

		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 0: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					loadArchitectView();

				} else {

					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				return;
			}

			// other 'case' lines to check for other
			// permissions this app might request
		}
	}

	private void loadArchitectView() {

		final StartupConfiguration startupConfiguration = new StartupConfiguration(getApplicationContext().getString(R.string.wikitude_trial_licence_key));
		this.architectView.onCreate(startupConfiguration);

		this.architectView.onPostCreate();
		try {
			this.architectView.load("index.html");
		}catch (IOException e){

		}

		this.architectView.callJavascript("loadLogin()");
		this.architectView.callJavascript("loadWelcome()");
	}
}