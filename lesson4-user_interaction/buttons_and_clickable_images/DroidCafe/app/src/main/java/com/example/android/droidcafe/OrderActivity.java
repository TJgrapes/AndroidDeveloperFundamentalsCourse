/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.droidcafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This activity is blank for now. Subsequent versions of the app
 * provide input controls and a delivery method for an order.
 */
public class OrderActivity extends AppCompatActivity {

TextView orderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderTextView = findViewById(R.id.order_text_view);

        Intent receivedIntent = getIntent();

        String orderMessageUpdate = receivedIntent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Want to show the default "No Order Placed" message if not icon was clicked
        // If we don't add this check, mOrderMessage will be null, so no text will show if a dessert icon was not clicked
        // Use the orderMessageUpdate variable available to us here, which represents the mOrderMessage from the Main Activity
        if(orderMessageUpdate != null) {

            orderTextView.setText(orderMessageUpdate);

        }


    }
}
