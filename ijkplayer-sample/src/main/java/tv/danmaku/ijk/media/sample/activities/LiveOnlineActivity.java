package tv.danmaku.ijk.media.sample.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import cn.vbyte.android.sample.R;
import tv.danmaku.ijk.media.sample.application.AppActivity;


import com.vbyte.p2p.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LiveOnlineActivity extends AppActivity {

    SQLiteDatabase mDb;
    SQLiteDatabaseDao dao;

    //获取删除的数据库索引
    int mListPos;
    // 存储数据的数组列表
    ArrayList<HashMap<String, Object>> listData;
    // 适配器
    SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_online);
        dao = new SQLiteDatabaseDao();

        Toolbar toolbar = (Toolbar) findViewById(R.id.livetoolbar);
        toolbar.setTitle("");
        TextView titleText=(TextView) findViewById(R.id.toolbarTitleText);
        titleText.setText("电视直播");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView list = (ListView) findViewById(R.id.file_list_view);
        listItemAdapter = new SimpleAdapter(this,
                listData,// 数据源
                android.R.layout.simple_list_item_2,// ListItem的XML实现
                // 动态数组与ImageItem对应的子项
                new String[] {  "name", "channelID" },
                // ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] { android.R.id.text1, android.R.id.text2 });
        list.setAdapter(listItemAdapter);
        list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                // 获取位置索引
                mListPos = info.position;

                menu.add(0, 0, 0, "添加");
                menu.add(0, 1, 0, "删除");
                menu.add(0, 2, 0, "删除所有");

            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                // 获取对应HashMap数据内容
                HashMap<String, Object> map = listData
                        .get(position);

                String name = map.get("name").toString();
                String channel = map.get("channelID").toString();
                VideoActivity.intentTo(LiveOnlineActivity.this, channel, name);
            }
        });
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
                // 获取对应HashMap数据内容
                HashMap<String, Object> map = listData
                        .get(mListPos);
                // 获取id
                int id = Integer.valueOf((map.get("id")
                        .toString()));
                // 获取数组具体值后,可以对数据进行相关的操作,例如更新数据
                if (dao.delete(mDb, "channelInfo", id)) {
                    // 移除listData的数据
                    listData.remove(mListPos);
                    listItemAdapter.notifyDataSetChanged();
                }
                Toast.makeText(this.getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                break;

            case 2:
                // 删除ALL操作
                listData.clear();
                listItemAdapter.notifyDataSetChanged();
                Toast.makeText(this.getApplicationContext(),"已删除所有",Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return super.onContextItemSelected(item);

    }
    //增加一个条目 包括名称和链接
    private void addItem(){

        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.liveonlivedialog, null);


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("请输入电视台名及频道号");
        builder.setView(textEntryView);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(
                    DialogInterface dialoginterface, int i) {
                String name = "";
                EditText nameText = (EditText) textEntryView.findViewById(R.id.channelName);
                name = nameText.getText().toString();

                String channelID = "";
                EditText channelIDText = (EditText) textEntryView.findViewById(R.id.dailNumber);
                channelID = channelIDText.getText().toString();

                dao.insert(mDb, "channelInfo", name, channelID);
                Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("取消", null);

        AlertDialog dialog=builder.create();
        dialog.show();

    }
    // 简单的数据库操作类
    class SQLiteDatabaseDao {

        public SQLiteDatabaseDao() {
//            File dbFile = new File("/data/data/com.vbute.player/databases/Live.db");
//            dbFile.delete();
            mDb = openOrCreateDatabase("Live.db",
                    SQLiteDatabase.CREATE_IF_NECESSARY, null);
            // 初始化创建表
            createTable(mDb, "channelInfo");

            initData(mDb, "channelInfo");
            // 初始化获取所有数据表数据
            getAllData("channelInfo");
        }

        // 创建一个数据库
        public void createTable(SQLiteDatabase mDb, String table) {
            try {
                mDb.execSQL("create table if not exists "
                        + table
                        + " (id integer primary key autoincrement, "
                        + "name text not null, channelID text not null,image text);");
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "数据表创建失败",
                        Toast.LENGTH_LONG).show();
            }
        }

        // 初始化数据
        public void initData(SQLiteDatabase mDb, String table) {

            Cursor c = mDb.rawQuery("select * from " + table, null);

            if (c.getCount()<1){
                // 初始化插入数据
                ContentValues values = new ContentValues();

                values.put("name", "直播测试");
                values.put("channelID", "5799759e15c450206960996d");
                mDb.insert(table, null, values);

                values.put("name", "低延迟测试");
                values.put("channelID", "575797d8581221c12e45f493");
                mDb.insert(table, null, values);

                /*
                values.put("name", "安徽卫视");
                values.put("channelID", "14496521645631186");
                mDb.insert(table, null, values);

                values.put("name", "cctv5");
                values.put("channelID", "14395321695743290");
                mDb.insert(table, null, values);
                */
            }else {
                return;
            }

            // listItemAdapter.notifyDataSetChanged();

        }
        // 插入数据
        public void insert(SQLiteDatabase mDb, String table,String name,String channel) {

            // 初始化插入3条数据
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("channelID", channel);
            mDb.insert(table, null, values);

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", name);
            map.put("channelID", channel);

            // listData.clear();
            //  getAllData(table);
            listData.add(map);
            listItemAdapter.notifyDataSetChanged();

        }

        // 查询所有数据
        public void getAllData(String table) {
            Cursor c = mDb.rawQuery("select * from " + table, null);
            int columnsSize = c.getCount();
            listData = new ArrayList<HashMap<String, Object>>();
            // 获取表的内容
            while (c.moveToNext()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < columnsSize; i++) {
                    map.put("id", c.getString(0));
                    map.put("name", c.getString(1));
                    map.put("channelID", c.getString(2));

                }
                listData.add(map);
            }

        }

        // 删除一条数据
        public boolean delete(SQLiteDatabase mDb, String table, int id) {
            String whereClause = "id=?";
            String[] whereArgs = new String[] { String.valueOf(id) };
            try {
                mDb.delete(table, whereClause, whereArgs);
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "删除数据库失败",
                        Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
    }
    // 长按事件响应
    View.OnCreateContextMenuListener listviewLongPress = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            // TODO Auto-generated method stub
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            new AlertDialog.Builder(LiveOnlineActivity.this)
                    /* 弹出窗口的最上头文字 */
                    .setTitle("删除当前数据")
                    /* 设置弹出窗口的图式 */
                    .setIcon(android.R.drawable.ic_dialog_info)
                    /* 设置弹出窗口的信息 */
                    .setMessage("确定删除当前记录")
                    .setPositiveButton("是",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    // 获取位置索引
                                    int mListPos = info.position;
                                    // 获取对应HashMap数据内容
                                    HashMap<String, Object> map = listData
                                            .get(mListPos);
                                    // 获取id
                                    int id = Integer.valueOf((map.get("id")
                                            .toString()));
                                    // 获取数组具体值后,可以对数据进行相关的操作,例如更新数据
                                    if (dao.delete(mDb, "channelInfo", id)) {
                                        // 移除listData的数据
                                        listData.remove(mListPos);
                                        listItemAdapter.notifyDataSetChanged();
                                    }
                                }
                            })
                    .setNegativeButton("否",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    // 什么也没做

                                }
                            }).show();
        }
    };

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        mDb.close();
    }
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LiveOnlineActivity.class);
        return intent;
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();
        return true;
    }
}
