/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.settings;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.settings.notification.RedactionInterstitial;

/**
 * Setup Wizard's version of RedactionInterstitial screen. It inherits the logic and basic structure
 * from RedactionInterstitial class, and should remain similar to that behaviorally. This class
 * should only overload base methods for minor theme and behavior differences specific to Setup
 * Wizard. Other changes should be done to RedactionInterstitial class instead and let this class
 * inherit those changes.
 */
public class SetupRedactionInterstitial extends RedactionInterstitial {

    @Override
    public Intent getIntent() {
        Intent modIntent = new Intent(super.getIntent());
        modIntent.putExtra(EXTRA_SHOW_FRAGMENT,
                SetupRedactionInterstitialFragment.class.getName());
        return modIntent;
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SetupRedactionInterstitialFragment.class.getName().equals(fragmentName);
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        resid = SetupWizardUtils.getTheme(getIntent());
        super.onApplyThemeResource(theme, resid, first);
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        LinearLayout layout = (LinearLayout) findViewById(R.id.content_parent);
        layout.setFitsSystemWindows(false);
    }

    public static class SetupRedactionInterstitialFragment extends RedactionInterstitialFragment
            implements View.OnClickListener {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.setup_redaction_interstitial, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            final Button button = (Button) view.findViewById(R.id.redaction_next_button);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.redaction_next_button) {
                final SetupRedactionInterstitial activity =
                        (SetupRedactionInterstitial) getActivity();
                if (activity != null) {
                    activity.setResult(RESULT_OK, activity.getResultIntentData());
                    finish();
                }
            }
        }
    }
}
