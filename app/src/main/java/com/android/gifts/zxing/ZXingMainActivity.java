package com.android.gifts.zxing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ZXingMainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Instance variables to represent the "Scan Now" button
     * and two TextViews we created in the layout XML file
     */
    private Button scanButton;
    private TextView contentTextView, formatTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_main);

        /**
         * Instantiate these variables - scanButton, contentTextView and formatTextView - using the ID values
         * we specified in the layout XML file.
         */
        scanButton = (Button) findViewById(R.id.scan_btn);
        contentTextView = (TextView) findViewById(R.id.scan_content_tv);
        formatTextView = (TextView) findViewById(R.id.scan_format_tv);

        /**
         * Add a listener to the scanButton so that we can handle presses
         */
        scanButton.setOnClickListener(this);

    }


    /**
     * The results of the scan will be returned and we'll be able to retrieve it in the onActivityResult() method
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * Parse the result into an instance of the IntentResult class we imported
         */
        IntentResult scanningIntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        /**
         * Check the scanningIntentResult object is it null or not, to proceed only if we have a valid result
         */
        if (scanningIntentResult != null) {
            /**
             * Retrieve the content and the format of the scan as strings value.
             */
            String scanContent = scanningIntentResult.getContents();
            String scanFormat = scanningIntentResult.getFormatName();
            /**
             * if condition after getting scanContent and scanFormat,
             * checking on them if there are consisting real data or not.
             */
            if(scanContent != null && scanFormat != null) {
                /**
                 * Now our program has the format and content of the scanned data,
                 * so you can do whatever you want with it.
                 */
                contentTextView.setText("Content : " + scanContent);
                formatTextView.setText("Format : " + scanFormat);
            } else {
                Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT).show();
            }
        } else {
            /**
             * If scan data is not received
             * (for example, if the user cancels the scan by pressing the back button),
             * we can simply output a message.
             */
            Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scan_btn:
                /**
                 * create an instance of the IntentIntegrator class we imported,
                 * and then call on the initiateScan() method to start scanning
                 */
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
        }
    }
}
