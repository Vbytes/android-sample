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

package tv.danmaku.ijk.media.sample.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import java.io.File;
import java.io.IOException;

import cn.vbyte.android.sample.R;
import tv.danmaku.ijk.media.sample.application.AppActivity;
import tv.danmaku.ijk.media.sample.application.Settings;
import tv.danmaku.ijk.media.sample.eventbus.FileExplorerEvents;
import tv.danmaku.ijk.media.sample.fragments.FileListFragment;

public class FileExplorerActivity extends AppActivity {
    private Settings mSettings;
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, FileExplorerActivity.class);
        return intent;
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mSettings == null) {
            mSettings = new Settings(this);
        }

        String lastDirectory = mSettings.getLastDirectory();
        if (!TextUtils.isEmpty(lastDirectory) && new File(lastDirectory).isDirectory())
            doOpenDirectory(lastDirectory, false);
        else
            doOpenDirectory("/", false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FileExplorerEvents.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        FileExplorerEvents.getBus().unregister(this);
    }

    private void doOpenDirectory(String path, boolean addToBackStack) {
        Fragment newFragment = FileListFragment.newInstance(path);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.body, newFragment);

        if (addToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    @Subscribe
    public void onClickFile(FileExplorerEvents.OnClickFile event) {
        File f = event.mFile;
        try {
            f = f.getAbsoluteFile();
            f = f.getCanonicalFile();
            if (TextUtils.isEmpty(f.toString()))
                f = new File("/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (f.isDirectory()) {
            String path = f.toString();
            mSettings.setLastDirectory(path);
            doOpenDirectory(path, true);
        } else if (f.exists()) {
            VideoActivity.intentTo(this, f.getPath(), f.getName());
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();
        return true;
    }
}
