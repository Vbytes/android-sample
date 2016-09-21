package tv.danmaku.ijk.media.sample.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.vbyte.android.sample.R;

import cn.vbyte.p2p.VbyteP2PModule;

public class LauncherMainActivity extends AppCompatActivity {
    final Activity activity = this;

    // 初始化VbyteP2PModule的相关变量
    final String APP_ID = "577cdcabe0edd1325444c91f";
    final String APP_KEY = "G9vjcbxMYZ5ybgxy";
    final String APP_SECRET = "xdAEKlyF9XIjDnd9IwMw2b45b4Fq9Nq9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化P2P模块
        try {
            VbyteP2PModule.create(this.getBaseContext(), APP_ID, APP_KEY, APP_SECRET);
            VbyteP2PModule.disableDebug();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_launcher_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView titleText=(TextView) findViewById(R.id.toolbarTitleText);
        titleText.setText("首页");
        setSupportActionBar(toolbar);

        Button button_live=(Button)findViewById(R.id.button_live);
        button_live.setOnClickListener(new ButtonListener());

        Button button_online=(Button)findViewById(R.id.button_online);
        button_online.setOnClickListener(new  ButtonListener());

        Button button_native=(Button)findViewById(R.id.button_native);
        button_native.setOnClickListener(new  ButtonListener());
    }
    class ButtonListener implements View.OnClickListener
    {

        public void onClick(View v)
        {
            switch(v.getId()) {
                case R.id.button_live:
                    LiveOnlineActivity.intentTo(activity);
                    break;
                case R.id.button_online:
                    SampleMediaActivity.intentTo(activity);
                    break;
                case R.id.button_native:
                    FileExplorerActivity.intentTo(activity);
                    break;
                default:
                    break;
            }
        }


    }
}

