package com.prash.flashlightbasic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private Camera camera ;
    private Camera.Parameters params ;
    private ImageButton myImageButton ;
    boolean isFlashLightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myImageButton = (ImageButton)findViewById(R.id.flashlight_button);
        myImageButton.setOnClickListener(new FlashOnOffListner());

        if(isFlashSupported())
        {
            camera = Camera.open() ;
            params = camera.getParameters();

        }
        else
        {
            showNoFlashAlert() ;
        }
    }

    private class FlashOnOffListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(isFlashLightOn)
            {
                myImageButton.setImageResource(R.drawable.flash_lightoff);
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(params);
                camera.stopPreview();
                isFlashLightOn = false ;

            }
            else
            {
                myImageButton.setImageResource(R.drawable.flash_lighton);
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(params);
                camera.startPreview();
                isFlashLightOn = true;

            }

        }
    }
    private void showNoFlashAlert() {
        new AlertDialog.Builder(this)
                .setMessage("Your device hardware does not support flashlight!")
                .setIcon(android.R.drawable.ic_dialog_alert).setTitle("Error")
                .setPositiveButton("Ok", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).show();
    }

    private boolean isFlashSupported() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    protected void onDestroy() {
        if(camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        super.onDestroy();
    }

}
