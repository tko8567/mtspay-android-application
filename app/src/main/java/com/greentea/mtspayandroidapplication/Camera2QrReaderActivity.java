package com.greentea.mtspayandroidapplication;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;

import com.greentea.mtspayandroidapplication.util.AppHelper;

/**
 * Here we know that the device and context allow us to use the camera,
 * so every exception is a "wtf" (what a terrible failure) <br>
 * This class is supposed to find the front camera and use it to scan a qr
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2QrReaderActivity extends AppCompatActivity{

    private CameraDevice mCameraDevice;
    private CameraManager mCameraManager;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView

        mCameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        String[] cameraIdList;
        try {
            cameraIdList = mCameraManager.getCameraIdList();
            for (String cameraId: cameraIdList) {

            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Log.wtf("Camera2QrReaderActivity", "Camera Access is not permitted");
            AppHelper.showExceptionAlertDialog(
                    e,
                    this,
                    null,
                    dialog -> Camera2QrReaderActivity.this.finish(),
                    null
            );

        }
    }

    @Override
    protected void onResume() {
        super.onResume();


/*
        if (Build.VERSION.SDK_INT >= 21) {

            CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
                @Override
                public void onOpened(CameraDevice camera) {
                    camera.createCaptureSession(
                            new ArrayList<Surface>() {{
                                add(surfaceView.getHolder().getSurface());
                            }},


                    );
                }

                @Override
                public void onDisconnected(CameraDevice camera) {

                }

                @Override
                public void onError(CameraDevice camera, int error) {

                }
            };

            Handler cameraHandler = new Handler();

            try {
                cameraManager.openCamera(
                        cameraManager.getCameraIdList()[0],
                        stateCallback,
                        null
                );
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
*/
    }
}
