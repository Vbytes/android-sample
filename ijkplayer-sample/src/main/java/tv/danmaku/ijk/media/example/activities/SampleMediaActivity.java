/*
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
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
 * limitations under the License.
 */

package tv.danmaku.ijk.media.example.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import cn.vbyte.android.sample.R;
import cn.vbyte.p2p.VbyteP2PModule;
import tv.danmaku.ijk.media.example.application.AppActivity;
import tv.danmaku.ijk.media.example.fragments.SampleMediaListFragment;
import tv.danmaku.ijk.media.example.utils.VbyteP2PMediaPlayer;

public class SampleMediaActivity extends AppActivity  {

    // 初始化VbyteP2PModule的相关变量,ours
    final String APP_ID = "577cdcabe0edd1325444c91f";
    final String APP_KEY = "G9vjcbxMYZ5ybgxy";
    final String APP_SECRET = "xdAEKlyF9XIjDnd9IwMw2b45b4Fq9Nq9";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SampleMediaActivity.class);
        return intent;
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化P2P模块
        try {
            VbyteP2PModule.create(this.getBaseContext(), APP_ID, APP_KEY, APP_SECRET);
            VbyteP2PModule.enableDebug();
            // CrashReport.initCrashReport(getApplicationContext(), "9c088d8d97", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Fragment mediaFragment = SampleMediaListFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.body, mediaFragment);
        transaction.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean show = super.onPrepareOptionsMenu(menu);
        if (!show)
            return show;

        MenuItem item = menu.findItem(R.id.action_recent);
        if (item != null)
            item.setVisible(false);

        return true;
    }
}
