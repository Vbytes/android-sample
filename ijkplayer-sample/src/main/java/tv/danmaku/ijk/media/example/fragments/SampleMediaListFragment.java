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

package tv.danmaku.ijk.media.example.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import cn.vbyte.android.sample.R;
import tv.danmaku.ijk.media.example.activities.VideoActivity;
import tv.danmaku.ijk.media.example.utils.VbyteP2PMediaPlayer;

public class SampleMediaListFragment extends Fragment {
    private ListView mLiveListView;
    private SampleMediaAdapter mLiveAdapter;

    private ListView mVodListView;
    private SampleMediaAdapter mVodAdapter;

    public static SampleMediaListFragment newInstance() {
        SampleMediaListFragment f = new SampleMediaListFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_media_list, container, false);
        mLiveListView = (ListView) viewGroup.findViewById(R.id.live_list_view);
        mVodListView = (ListView) viewGroup.findViewById(R.id.vod_list_view);
        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();

        mLiveAdapter = new SampleMediaAdapter(activity);
        mLiveListView.setAdapter(mLiveAdapter);
        mLiveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SampleMediaItem item = mLiveAdapter.getItem(position);
                String name = item.mName;
                String url = item.mUrl;
                VideoActivity.intentTo(activity, url, name, VbyteP2PMediaPlayer.VIDEO_LIVE);
            }
        });

        mLiveAdapter.addItem("14395321695743290", "cctv5");
        mLiveAdapter.addItem("14496521645631186", "安徽卫视");
        mLiveAdapter.addItem("585ca8a75ea113526f839747", "huoying gop-8");
        mLiveAdapter.addItem("hitlive10616538946203751", "test gop-10");
        mLiveAdapter.addItem("57aaf3603d6bc39a2c61f2e0", "时钟流 gop-10");
        mLiveAdapter.addItem("5799dbb16b66eb25691396b4", "HLS-CCTV5测试");

        mVodAdapter = new SampleMediaAdapter(activity);
        mVodListView.setAdapter(mVodAdapter);
        mVodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SampleMediaItem item = mVodAdapter.getItem(position);
                String name = item.mName;
                String url = item.mUrl;
                VideoActivity.intentTo(activity, url, name, VbyteP2PMediaPlayer.VIDEO_VOD);
            }
        });

        mVodAdapter.addItem("http://split.vbyte.cn/files/1read3.mp4", "让我们荡起双桨");
        mVodAdapter.addItem("http://vod.vbyte.cn/lalala.flv", "汤唯");
        mVodAdapter.addItem("http://vod.vbyte.cn/huanlexijurenChaoqing.flv", "欢乐喜剧人");
        mVodAdapter.addItem("http://testvod.starschinalive.com/test/hls/high/2.m3u8", "m3u8");
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
