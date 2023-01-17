package hardware.buttons.find.flutter.hardware_buttons_find_flutter;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;

import android.os.Handler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.content.IntentFilter;


import android.os.Bundle;

import java.util.Objects;

import io.flutter.embedding.android.FlutterActivity;

/**
 * HardwareButtonsFindFlutterPlugin
 */
public class HardwareButtonsFindFlutterPlugin extends FlutterActivity implements FlutterPlugin, EventChannel.StreamHandler {

    private static final String STREAM = "hardware_buttons_find_flutter";
    private EventChannel channel;
    private EventChannel.EventSink attachEvent;

    Context context;

    private Handler handler;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        context = flutterPluginBinding.getApplicationContext();
        channel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), STREAM);
        channel.setStreamHandler((EventChannel.StreamHandler) this);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setStreamHandler(null);
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        attachEvent = events;
      
        if (context != null) {
   
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.rfid.FUN_KEY");
            context.registerReceiver(keyReceiver, filter);
        }
       
    }

    @Override
    public void onCancel(Object arguments) {
        attachEvent = null;
         
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new EventChannel(Objects.requireNonNull(getFlutterEngine()).getDartExecutor(), STREAM).setStreamHandler(
                new EventChannel.StreamHandler() {
                    @Override
                    public void onListen(Object args, final EventChannel.EventSink events) {
                        
                        attachEvent = events;
                    }

                    @Override
                    public void onCancel(Object args) {
                        attachEvent = null;
                       
                    }
                }
        );
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        handler = null;
        attachEvent = null;
    }

    private long startTime = 0;
    private boolean keyUpFalg = true;

    private final BroadcastReceiver keyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int keyCode = intent.getIntExtra("keyCode", 0);
            if (keyCode == 0) {
                keyCode = intent.getIntExtra("keycode", 0);
            }
            boolean keyDown = intent.getBooleanExtra("keydown", false);
            if (keyUpFalg && keyDown && System.currentTimeMillis() - startTime > 500) {
                keyUpFalg = false;
                startTime = System.currentTimeMillis();
                System.out.println(keyCode);
                if ((keyCode == KeyEvent.KEYCODE_F3 || keyCode == KeyEvent.KEYCODE_F4 || keyCode == KeyEvent.KEYCODE_F5)) {
                    System.out.println(keyCode);
                    attachEvent.success(keyCode);
                }
            } else if (keyDown) {
                startTime = System.currentTimeMillis();
            } else {
                keyUpFalg = true;
            }
        }
    };
}
