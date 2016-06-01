# ZXing-Library-Tutorial

In this tutorial we are having the ZXing (Zebra Crossing) Android Library Tutorial which is an Open Source Android library, multi-format 1D/2D barcode image processing library implemented in Java, that allows developers to easily carry out barcode scanning within an Android application. By importing the ZXing integration classes into our application, we can make user scans barcode easier and focus our development efforts on handling the scan results. The ZXing project is Apache licensed, so it is free to use without any kind of restrictions.

# Applications integrated with ZXing:
Barcode Scanner is an Android application, from the open source project ZXing (Zebra Crossing), that allows a user to scan 1-D or 2-D “graphical barcodes” with the camera on their Android device.

# Our Application:
Our application will use ZXing Library to make an application like to Barcode Scanner, allow the user to scan any bar code after clicking on “Scan Now” button, then the returned scan data -content and format of the barcode- will be written to a defined TextViews.

# Creating ZXing Example Project:

1.In Android Studio, create a new activity, select Empty Activity and lets name ZXingMainActivity.

2.Create a package called: com.google.zxing.integration.android, then replace the two classes that you downloaded from here. After replacing the two classes, your project hierarchy might be like the below image:

![alt tag](http://androidgifts.com/wp-content/uploads/2016/04/Screen-Shot-2016-04-24-at-02.18.38.png)

3.Open activity_zxing_main.xml file and add this code: this would create a button called “scan_btn” since the user will press on this button to scan any barcode he/she wants, also create two TextViews called “scan_content_tv” to show the barcode content to the user and “scan_format_tv” to show the format of the barcode that the user scanned.

```xml
<xml version="1.0" encoding="utf-8">
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.android.gifts.zxing.ZXingMainActivity">
 
    <Button
        android:id="@+id/scan_btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true"
        android:text="@string/scan" />
 
    <TextView
        android:id="@+id/scan_content_tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@+id/scan_format_tv"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="10dp"
        android:gravity="center"
        android:text="@string/content"
        android:textAppearance="?android:attr/textAppearanceLarge" />
 
    <TextView
        android:id="@+id/scan_format_tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="20dp"
        android:gravity="center"
        android:text="@string/format"
        android:textAppearance="?android:attr/textAppearanceLarge" />
 
</RelativeLayout>
```

4.Then open ZXingMainActivity class, and this code:

  * The ZXing app will do all the work of scanning, you Activity will simply call the ZXing app using Intents and get the scanned results from it. In this class, we create 2 textviews, one to display the url which is contained in the QR code and other to display the format itself.

  * The scanner will start if it’s installed on the user’s device. If not, they’ll be prompted to download it.

  * The results of the scan will be returned to the ZXingMainActivity class where scanning was initiated, so we’ll be able to retrieve it in the onActivityResult() method.
  
```java
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
            if(scanContent != null &amp;&amp; scanFormat != null) {
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
```

5.Run your app on a device instead of an emulator so that you can see the scan functioning. Try scanning a book or any other barcode you might have, and the result should be like the below images:

This screen is the main screen that the user will press on “Scan Now” button to scan any barcode he/she wants.

![alt tag](http://androidgifts.com/wp-content/uploads/2016/04/Scan-Screen.png)

After that, ZXing Example application will be redirected to Barcode Scanner application to scan the barcode that the user wants

![alt tag](http://androidgifts.com/wp-content/uploads/2016/04/Open-Barcode-Scanner-Application-768x432.png)

And once the scanned barcode is valid, the content and the format of this barcode will be returned from Barcode Scanner application to be shown at ZXing (Zebra Crossing) Android Library Example

![alt tag](http://androidgifts.com/wp-content/uploads/2016/04/Scan-result-data-576x1024.png)

If the user press back button or the barcode is invalid, a Toast will be shown to the user telling him/her that no scan data are received.

![alt tag](http://androidgifts.com/wp-content/uploads/2016/04/No-scan-data-are-recieved-576x1024.png)

[You can check the whole article here](http://androidgifts.com/zxing-zebra-crossing-android-library-tutorial-library-4/)
