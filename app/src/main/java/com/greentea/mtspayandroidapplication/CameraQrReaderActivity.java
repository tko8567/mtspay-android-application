package com.greentea.mtspayandroidapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.greentea.mtspayandroidapplication.util.view.CameraPreview;


/**
 * This class is used for API below LOLLIPOP
 */
@SuppressWarnings("deprecation")
public class CameraQrReaderActivity extends AppCompatActivity {

    private static final String TAG = "CameraQrReaderActivity";
    private Camera mCamera;
    private CameraPreview mCameraPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_preview);

        BarcodeDetector detector = new BarcodeDetector.Builder(CameraQrReaderActivity.this)
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                .build();

        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(this, mCamera);

        mCameraPreview.setOnClickListener(l -> {mCamera.autoFocus(null);});




        mCameraPreview.getCamera().setOneShotPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Bitmap bitmap = Bitmap.createBitmap(
                        camera.getParameters().getPreviewSize().width,
                        camera.getParameters().getPreviewSize().height,
                        Bitmap.Config.ARGB_8888);
                Allocation bmData = renderScriptNV21ToRGBA888(
                        CameraQrReaderActivity.this,
                        camera.getParameters().getPreviewSize().width,
                        camera.getParameters().getPreviewSize().height,
                        data);
                bmData.copyTo(bitmap);

                Barcode barcode = detector.detect(new Frame.Builder().setBitmap(bitmap).build()).valueAt(0);
                if (barcode != null) Toast.makeText(CameraQrReaderActivity.this, "Got qr data: " + barcode.rawValue, Toast.LENGTH_LONG).show();
            }
        });

        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mCameraPreview);
    }


    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(0); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    public Allocation renderScriptNV21ToRGBA888(Context context, int width, int height, byte[] nv21) {
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicYuvToRGB yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs));

        Type.Builder yuvType = new Type.Builder(rs, Element.U8(rs)).setX(nv21.length);
        Allocation in = Allocation.createTyped(rs, yuvType.create(), Allocation.USAGE_SCRIPT);

        Type.Builder rgbaType = new Type.Builder(rs, Element.RGBA_8888(rs)).setX(width).setY(height);
        Allocation out = Allocation.createTyped(rs, rgbaType.create(), Allocation.USAGE_SCRIPT);

        in.copyFrom(nv21);

        yuvToRgbIntrinsic.setInput(in);
        yuvToRgbIntrinsic.forEach(out);
        return out;
    }
}
