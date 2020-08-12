package com.example.data;

import android.Manifest;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.drm.DrmStore;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.example.myapplication.R;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.bluetooth.BluetoothAdapter.ACTION_SCAN_MODE_CHANGED;

public class BlueTeethActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private ListView list;
    private List<String> listData = new ArrayList<>();
    private BluetoothAdapter bluetoothAdapter;
    private BroadcastReceiver broadcastReceiver;
    private BroadcastReceiver deviceFoundReceiver;
    private ArrayAdapter<String> arrayAdapter;
    private  BluetoothLeScanner scanner ;
    List<BleDevice> devicelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_teeth);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BleManager.getInstance().init(getApplication());  //控件的初始化
        BleManager.getInstance()
                .enableLog(false)   //关闭日志
                .setReConnectCount(1, 5000)
                .setSplitWriteNum(20)
                .setConnectOverTime(10000)
                .setOperateTimeout(5000);           //设置配置
        //可以设置一些相应的扫描规则，此时我们就不设置了

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        list = findViewById(R.id.list);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,listData);
        list.setAdapter(arrayAdapter);
        //设置监听连接数据
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BleManager.getInstance().connect(devicelist.get(position), new BleGattCallback() {
                    @Override
                    public void onStartConnect() {
                        Toast.makeText(BlueTeethActivity.this, "开始连接", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onConnectFail(BleDevice bleDevice, BleException exception) {
                        Toast.makeText(BlueTeethActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                        Toast.makeText(BlueTeethActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
                        Toast.makeText(BlueTeethActivity.this, "连接中断", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 1);
                switch (state){
                    case BluetoothAdapter.STATE_OFF:
                        Toast.makeText(BlueTeethActivity.this, "蓝牙已关闭", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Toast.makeText(BlueTeethActivity.this, "蓝牙已打开", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Toast.makeText(BlueTeethActivity.this, "正在打开蓝牙", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Toast.makeText(BlueTeethActivity.this, "正在关闭蓝牙", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(BlueTeethActivity.this, "未知状态", Toast.LENGTH_SHORT).show();
                }
            }
        };
        deviceFoundReceiver  = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                //可见性
                if(action.equals(ACTION_SCAN_MODE_CHANGED)){
                    int mode  =  intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,1);
                    switch (mode){
                        case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                            Toast.makeText(BlueTeethActivity.this, "现在是可发现模式", Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                            Toast.makeText(BlueTeethActivity.this, "现在不是可发现模式，但是可以连接", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                else if(action.equals(BluetoothDevice.ACTION_FOUND)){
                    BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    listData.add(bluetoothDevice.getName()+"\n"+ bluetoothDevice.getAddress());
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                ifSupportBlue();
                break;
            case R.id.btn2:
                ifOpenBlue();
                break;
            case R.id.btn3:
                checkAndSure();
                break;
            case R.id.btn4:
                closeBlue();
                break;
            case R.id.btn5:
                getBondedDevices();
                break;
            case R.id.btn6:
                searchDevices();
                break;
            case R.id.btn7:
                canBeFoundedDevices();
                break;
            case R.id.btn8:
                whetherSupportBle();
                break;
            case R.id.btn9:
                scanBleDevice();
                break;

        }
    }

    private void scanBleDevice() {
       BleManager.getInstance().scan(new BleScanCallback() {
           @Override
           public void onScanFinished(List<BleDevice> scanResultList) {
               //扫描结束后，我们进行连接
               listData.clear();
               arrayAdapter.notifyDataSetChanged();
               devicelist = scanResultList;
               for(BleDevice bleDevice: scanResultList){
                   listData.add(bleDevice.getName()+"\n"+bleDevice.getMac());
                   arrayAdapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onScanStarted(boolean success) {

           }

           @Override
           public void onScanning(BleDevice bleDevice) {

           }
       });
    }

    private void whetherSupportBle() {
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
            Toast.makeText(this,"支持BLE设备",Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this,"硬件不支持BLE设备",Toast.LENGTH_SHORT).show();;
    }

    /*
    定义设备可被发现搜索
    才需要启用可发现性，因为远程设备在跟你的设备连接之前必须能够发现它。
     */
    private void canBeFoundedDevices() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //定义持续时间
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }
    /*
    如果更在扫描，则停止，然后进行连接
    连接的时候要先判断蓝牙是否在扫描，如果在扫描就停止扫描，并且没有连接才进行连接，代码如下：
    mBluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(BltContant.SPP_UUID);
    if (bluetoothadapter.isDiscovering()) {
    bluetoothadapter.cancelDiscovery();
    }
    if (!mBluetoothSocket.isConnected()) {
        mBluetoothSocket.connect();
    }
     */

    private void searchDevices() {
        if(bluetoothAdapter!=null && bluetoothAdapter.isEnabled()){
            bluetoothAdapter.startDiscovery();
            listData.clear();
            arrayAdapter.notifyDataSetChanged();
        }
    }

    private void getBondedDevices() {
        if(bluetoothAdapter ==null | !bluetoothAdapter.isEnabled())
            return;
        Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice bluetoothDevice : deviceSet){
            listData.add(bluetoothDevice.getName()+"\n"+ bluetoothDevice.getAddress());
        }
        arrayAdapter.notifyDataSetChanged();

        Class<BluetoothAdapter> bluetoothAdapterClass = BluetoothAdapter.class;//得到BluetoothAdapter的Class对象
        try {//得到连接状态的方法
            Method method = bluetoothAdapterClass.getDeclaredMethod("getConnectionState", (Class[]) null);
            //打开权限
            method.setAccessible(true);
            int state = (int) method.invoke(bluetoothAdapter, (Object[]) null);

            if(state == BluetoothAdapter.STATE_CONNECTED){
                Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                for(BluetoothDevice device : devices){
                    Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);
                    method.setAccessible(true);
                    boolean isConnected = (boolean) isConnectedMethod.invoke(device, (Object[]) null);
                    if(isConnected){
                        Log.e("device",device.getAddress()+"名字"+ device.getName());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeBlue() {
       if(bluetoothAdapter!=null && bluetoothAdapter.isEnabled())
           bluetoothAdapter.disable();
    }

    private void ifOpenBlue() {
        if(bluetoothAdapter.isEnabled()){
            Toast.makeText(BlueTeethActivity.this,"蓝牙已经打开",Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(BlueTeethActivity.this,"蓝牙未打开",Toast.LENGTH_SHORT).show();
    }

    private void ifSupportBlue() {
        if(bluetoothAdapter==null){
            Toast.makeText(BlueTeethActivity.this,"不支持蓝牙",Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(BlueTeethActivity.this,"支持蓝牙",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==111){
            Toast.makeText(BlueTeethActivity.this,"定位已经手动打开",Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,222);
                }
            },1500);

        }
        else if(requestCode == 222){
            if(resultCode == RESULT_OK){
                Toast.makeText(BlueTeethActivity.this,"蓝牙开启",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(deviceFoundReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(broadcastReceiver,intentFilter);
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter1.addAction(ACTION_SCAN_MODE_CHANGED);  //可被发现扫描出来
        registerReceiver(deviceFoundReceiver,intentFilter1);
    }

    private void checkAndSure() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER )| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                Toast.makeText(BlueTeethActivity.this,"定位开启",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,222);
            }else {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent,111);
            }

        }
    }
}
