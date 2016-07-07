package tv.danmaku.ijk.media.sample.activities;

import com.vbyte.p2p.P2PHandler;

import android.app.Activity;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class StarP2PHandler extends P2PHandler {
	
	public final static String TAG = "vbyte";
	private Activity activity;
	private Toast toast;

	public StarP2PHandler(Activity activity) {
		this.activity = activity;
		toast = Toast.makeText(activity.getApplicationContext(), " ", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}
	
	@Override
	public void handleMessage(Message msg) {
		switch(msg.what) {
		case p2p_ChannelInfoSuccess:
			// TODO something
			// activity.doSomething();
			break;
		case p2p_FirstDataSuccess:
			break;

			case  cdn_DownLoadFail:
				toast.setText("P2P Event: " + msg.obj);
				toast.show();
				break;
		default:
			super.handleMessage(msg);	
		}
		
		Log.i(TAG, "App handleMessage: " + msg.what);
		
		toast.setText("P2P Event: " + msg.what);
		toast.show();

	}
}
