package com.lovejjfg.arsenal.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 2017/2/9.
 * Email lovejjfg@gmail.com
 */

public class ShakeHelper implements SensorEventListener, DialogInterface.OnDismissListener {

    private AlertDialog dialog;
    private StringBuilder sb;
    private Context context;
    private SensorManager mSensorManager;
    private FragmentManager manager;
    //    private FragmentsUtil fragmentsUtil;

    private ShakeHelper(Context context) {
        this.context = context;
        dialog = new AlertDialog.Builder(context).create();
        dialog.setOnDismissListener(this);
        sb = new StringBuilder();
//        fragmentsUtil = new FragmentsUtil(((AppCompatActivity) context).getSupportFragmentManager());
        if (context instanceof FragmentActivity) {
            manager = ((FragmentActivity) context).getSupportFragmentManager();
        }


    }

    public static ShakeHelper initShakeHelper(Context context) {
        return new ShakeHelper(context);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        sb.delete(0, sb.length());
    }

    public void onStart() {
        //获取 SensorManager 负责管理传感器
        mSensorManager = ((SensorManager) context.getSystemService(Context.SENSOR_SERVICE));
        if (mSensorManager != null) {
            //获取加速度传感器
            Sensor mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    public void onPause() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17) && !dialog.isShowing()) {
                List<Fragment> topFragments = getTopFragments();
                if (topFragments == null) {
                    sb.append(context.getClass().getSimpleName());
                    dialog.setMessage(sb.toString());
                    dialog.show();
                    return;
                }
                //从最top的Fragment回溯parent，到了root的时候结束。
                ArrayList<Fragment> names;
                for (Fragment topFragment : topFragments) {
                    //Glide 使用Fragment来控制相关的request，不再考虑的范围内
                    if ("com.bumptech.glide.manager.SupportRequestManagerFragment".equals(topFragment.getClass().getName())) {
                        continue;
                    }
                    sb.append(context.getClass().getSimpleName());
                    names = new ArrayList<>();
                    while (topFragment != null) {
                        names.add(0, topFragment);
                        topFragment = topFragment.getParentFragment();
                    }
                    int length = names.size();
                    for (int i = 0; i < length; i++) {
                        Fragment name = names.get(i);
                        sb.append("\n");
                        for (int j = 0; j <= i; j++) {
                            sb.append("    ");
                        }
                        sb.append(name.getClass().getSimpleName());
                    }
                    sb.append("\n\n");

                }
                if (sb.length() == 0) {
                    sb.append(context.getClass().getSimpleName());
                }
                dialog.setMessage(sb.toString());
                dialog.show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Nullable
    private List<Fragment> getTopFragments() {
        if (manager == null) {
            return null;
        }
        List<Fragment> fragments = manager.getFragments();
        List<Fragment> topFragments = new ArrayList<>();
        if (fragments == null) {
            return null;
        }
        int size = fragments.size();
        for (int i = size - 1; i >= 0; i--) {
            Fragment f = fragments.get(i);
            if (!f.isHidden()) {
                Fragment t = getTopFragment(f.getChildFragmentManager());//递归
                if (t != null) {
                    topFragments.add(t);
                } else {
                    topFragments.add(f);
                }
            }

        }
        return topFragments;
    }

    @Nullable
    private Fragment getTopFragment(FragmentManager manager) {
        List<Fragment> fragments = manager.getFragments();
        if (fragments == null) {
            return null;
        }
        int size = fragments.size();
        for (int i = size - 1; i >= 0; i--) {
            Fragment f = fragments.get(i);
            if (!f.isHidden()) {
                Fragment tTopFragment = getTopFragment(f.getChildFragmentManager());
                return tTopFragment == null ? f : tTopFragment;
            }
        }
        return null;
    }

}
