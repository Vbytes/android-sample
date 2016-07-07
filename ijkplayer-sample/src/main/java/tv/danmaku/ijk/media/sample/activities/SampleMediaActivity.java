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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.vbyte.android.sample.R;
import tv.danmaku.ijk.media.sample.application.AppActivity;

public class SampleMediaActivity extends AppActivity  {

    private ListView mFileListView;
    private SampleMediaAdapter mAdapter;

    final Activity activity = this;
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
        setContentView(R.layout.activity_sample_online);
        Toolbar toolbar = (Toolbar) findViewById(R.id.online_toolbar);
        toolbar.setTitle("");
        TextView titleText=(TextView) findViewById(R.id.online_toolbarTitleText);
        titleText.setText("在线视频");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFileListView = (ListView) findViewById(R.id.online_file_list);

        mAdapter = new SampleMediaAdapter(activity);
        mFileListView.setAdapter(mAdapter);
        mFileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                SampleMediaItem item = mAdapter.getItem(position);
                String name = item.mName;
                String url = item.mUrl;
                VideoActivity.intentTo(activity, url, name);
            }
        });

        mAdapter.addItem("http://split.vbyte.cn/files/1read3.mp4", "让我们荡起双桨");
        mAdapter.addItem("http://vod.vbyte.cn/wasu7057551.mp4", "our test");
        mAdapter.addItem("http://vod.vbyte.cn/1PiceTest.flv", "our test");
        mAdapter.addItem("http://vod.vbyte.cn/lalala.flv", "our test");
        mAdapter.addItem("http://split.vbyte.cn/files/1read3.mp4", "让我们荡起双桨@ 650 kbps");


        //长按 listView  弹出选择框
        ItemOnLongClick1();

    }
    private void ItemOnLongClick1() {
        //注：setOnCreateContextMenuListener是与下面onContextItemSelected配套使用的
        mFileListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "添加");
                menu.add(0, 1, 0, "删除");
                menu.add(0, 2, 0, "删除所有");

            }
        });
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();

    }
    // 长按菜单响应函数
    public boolean onContextItemSelected(MenuItem item) {


        switch(item.getItemId()) {
            case 0:
                //添加
                addItem();
                break;

            case 1:
                // 删除操作
                Toast.makeText(activity.getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                break;

            case 2:
                // 删除ALL操作
                Toast.makeText(activity.getApplicationContext(),"已删除所有",Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return super.onContextItemSelected(item);

    }
    //增加一个条目 包括名称和链接
    private void addItem(){

        LayoutInflater factory = LayoutInflater.from(activity);
        final View textEntryView = factory.inflate(R.layout.dialog, null);


        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("请输入文件名及链接");
        builder.setView(textEntryView);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(
                    DialogInterface dialoginterface, int i) {

                Toast.makeText(activity.getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();


            }
        });
        builder.setNegativeButton("取消", null);

        AlertDialog dialog=builder.create();
        dialog.show();

    }
    final class SampleMediaItem {
        String mUrl;
        String mName;

        public SampleMediaItem(String url, String name) {
            mUrl = url;
            mName = name;
        }
    }
    final class SampleMediaAdapter extends ArrayAdapter<SampleMediaItem> {
        public SampleMediaAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_2);
        }

        public void addItem(String url, String name) {
            add(new SampleMediaItem(url, name));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            }

            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder();
                viewHolder.mNameTextView = (TextView) view.findViewById(android.R.id.text1);
                viewHolder.mUrlTextView = (TextView) view.findViewById(android.R.id.text2);
            }

            SampleMediaItem item = getItem(position);
            viewHolder.mNameTextView.setText(item.mName);
            viewHolder.mUrlTextView.setText(item.mUrl);

            return view;
        }

        final class ViewHolder {
            public TextView mNameTextView;
            public TextView mUrlTextView;
        }
    }



}
