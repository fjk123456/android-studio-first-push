package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.view.ViewGroup.LayoutParams;
import android.support.v7.widget.Toolbar;

import com.example.data.BlueTeethActivity;
import com.example.data.LoginActivity;
import com.example.util.ToastUtil;
import com.example.util.dilogFragment;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements dilogFragment.afterPhotoComplete ,dilogFragment.aftarSoundComplete ,EasyPermissions.PermissionCallbacks{
    private TextView textView;
    private Button button;
    private Spinner spinner;
    private EditText editText;
    private EditText popedit;
    private ImageView imageview;
    private ArrayAdapter arrayAdapter;
    private List<String> arraylist= new ArrayList<>();
    private int toumingdu;

    private DropEditText dropEditText;
    private List<String> choose_result=new ArrayList<>();
    private boolean isfirst=true;
    private ListPopupWindow listPopupWindow;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Toolbar toolbar;
    private Button more_btn;
    private Button gps_;
    private Button fangyang;
    private Button cavas;
    private Button edit_btn;
    private dilogFragment fragment;

    private EditText photoedit;
    private EditText soundedit;


    private  String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#000000"));
        requestPermission();
        init();
    }
    private void init() {
        soundedit = findViewById(R.id.soundedit);
        photoedit = findViewById(R.id.photoedit);
        fangyang = findViewById(R.id.fangyang);
        textView=(TextView)findViewById(R.id.test1);
        button=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5= (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        button7 = (Button)findViewById(R.id.btn7);
        button8 = (Button)findViewById(R.id.btn8);
        button9 = (Button)findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BlueTeethActivity.class);
                startActivity(intent);
            }
        });
        edit_btn = (Button)findViewById(R.id.edit_btn);
        fangyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestActvity.class);
                startActivity(intent);
            }
        });
        final ArrayList<String> list123 = new ArrayList<>();
        list123.add("拍照");
        list123.add("录音");
        list123.add("视频");
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int width = v.getWidth();
                fragment = dilogFragment.getInstance(list123,width);
                fragment.show(getSupportFragmentManager(),"dialog");
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showSuccessToast(MainActivity.this,"保存成功");
            }
        });

        cavas = (Button)findViewById(R.id.cavas);
        cavas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CavasActivity.class);
                startActivity(intent);
            }
        });
        gps_ = (Button)findViewById(R.id.gps_);
        gps_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GpsActivity.class);
                startActivity(intent);
            }
        });
        more_btn=findViewById(R.id.more_btn);
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BottomActivity.class);
                startActivity(intent);
            }
        });
        toolbar =(Toolbar)findViewById(R.id.toolbar1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"点击了返回按钮",Toast.LENGTH_SHORT).show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ExpanddlistActivity.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TablayoutActivity.class);
                startActivity(intent);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UitiaoshiActivity.class);
                startActivity(intent);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditTextActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BottomActivity.class);
                startActivity(intent);
               // startActivityForResult(intent,1);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("ways","register");
                startActivity(intent);
                /*
                PackageInfo pack = getAllApps(MainActivity.this,"Calculator","calculator");
                if(pack!=null){
                    Intent intent = new Intent();
                    intent = MainActivity.this.getPackageManager().getLaunchIntentForPackage(pack.packageName);
                    startActivityForResult(intent,123);
                }else {
                    Toast.makeText(MainActivity.this,"不存在计算器",Toast.LENGTH_SHORT).show();
                }
                */
            }
        });
        /*
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraylist.add("adadafa");
                arraylist.add("adajhdajh");
                arraylist.add("dansv8962");
                arraylist.add("lfmm,khsioo");
                arraylist.add("fseregb");
                arraylist.add("dansv5555");
            }
        });*/
        arraylist.add("adadafa");
        arraylist.add("adajhdajh");
        arraylist.add("dansv8962");
        arraylist.add("lfmm,khsioo");
        arraylist.add("fseregb");
        arraylist.add("dansv5555");
        popedit=(EditText)findViewById(R.id.popedixt);
        dropEditText=(DropEditText)findViewById(R.id.drop_edit_text);
        dropEditText.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arraylist));
        dropEditText.setFocusable(false);
        dropEditText.setCursorVisible(false);
        popedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showPopwindow();
                }

            }
        });
        spinner=(Spinner)findViewById(R.id.spinner);
        setadapter();
        editText=(EditText)findViewById(R.id.edit);
        imageview=(ImageView)findViewById(R.id.imageview);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View view= layoutInflater.inflate(R.layout.alert_title,null);
                SeekBar seekBar = view.findViewById(R.id.seekbar);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        Toast.makeText(MainActivity.this,"当前进度为："+seekBar.getProgress(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        toumingdu=seekBar.getProgress();
                        arraylist.add(toumingdu+"");
                    }
                });
                final String [] array = {"1455","adadad","adaccv","eehtt","fsccsc","scf5h1110"};
                boolean [] booleans_arraay=new boolean [array.length];
                for (int i =0;i<array.length;i++){
                    booleans_arraay[i]=false;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCustomTitle(view);
                builder.setMultiChoiceItems(array, booleans_arraay, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            choose_result.add(String.valueOf(which));
                            Toast.makeText(MainActivity.this,"您选择了"+array[which],Toast.LENGTH_SHORT).show();
                            Log.e("Mainactivity",which+"");
                        }else {
                            choose_result.remove(String.valueOf(which));
                            Toast.makeText(MainActivity.this,"您取消了"+array[which],Toast.LENGTH_SHORT).show();
                            Log.e("Mainactivity",which+"");
                        }

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"123456"+choose_result.toString(),Toast.LENGTH_SHORT).show();
                        Log.e("Mainactivity",choose_result.toString());
                        choose_result.clear();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog= builder.create();
                dialog.show();
                Button positiveButton= dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.GREEN);
                positiveButton.setTextSize(30);
                LinearLayout.LayoutParams params1= (LinearLayout.LayoutParams)positiveButton.getLayoutParams();
                params1.weight=1;
                params1.gravity= Gravity.CENTER;
                positiveButton.setLayoutParams(params1);
                Button negtiveButton= dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                LinearLayout.LayoutParams params2= (LinearLayout.LayoutParams)negtiveButton.getLayoutParams();
                params2.weight=1;
                params2.gravity= Gravity.CENTER;
                positiveButton.setLayoutParams(params2);
            }
        });

    }

    private PackageInfo getAllApps(MainActivity mainActivity, String cal1, String cal2) {
        PackageManager pm = mainActivity.getPackageManager();
        List<PackageInfo> packlist = pm.getInstalledPackages(0);
        for(PackageInfo packageInfo : packlist){

            if(packageInfo.packageName.contains(cal1) || packageInfo.packageName.contains(cal2)){
                Log.e("infol",packageInfo.packageName+ packageInfo.versionName);
                return packageInfo;
            }
        }
        return null;
    }

    private void showPopwindow(){

        listPopupWindow= new ListPopupWindow(this);
        listPopupWindow.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arraylist));//用android内置布局，或设计自己的样式

        listPopupWindow.setAnchorView(popedit);//以哪个控件为基准，在该处以mEditText为基准
        listPopupWindow.setModal(true);
        listPopupWindow.setWidth(LayoutParams.WRAP_CONTENT);
        listPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        listPopupWindow.setVerticalOffset(20);

        //listPopupWindow.setDropDownGravity(Gravity.START);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置项点击监听
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popedit.setText(arraylist.get(i));//把选择的选项内容展示在EditText上
                listPopupWindow.dismiss();//如果已经选择了，隐藏起来
            }
        });
        listPopupWindow.show();//把ListPopWindow展示出来

    }

    private void setadapter() {
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arraylist);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        //spinner.setSelection(2,true);
        spinner.setPrompt("ni hao a ");
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                editText.setText(arraylist.get(position));
                Toast.makeText(MainActivity.this,"选择了"+editText.getText().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (!checkPermission(PERMISSIONS)) {
            EasyPermissions.requestPermissions(
                    this,"为了应用的正常使用，请允许以下权限。",
                    0,PERMISSIONS);
       }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private boolean checkPermission(String [] pppp) {
        return EasyPermissions.hasPermissions(this, pppp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(fragment!=null){
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void getPhotoPath(String path) {
        photoedit.setText(path);
    }

    @Override
    public void getSoundPath(String path) {
        soundedit.setText(path);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @SuppressLint("Range")
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.e("denied",perms.toString());
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog
                    .Builder(this)
                    .setRationale("此功能需要这些权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show();
        }
        else if(perms.size()>1){
            String [] deniedPer = new String[perms.size()];
            for(int i= 0;i<perms.size();i++){
                deniedPer[i] = perms.get(i);
            }
            EasyPermissions.requestPermissions(this,"需要的权限",1,deniedPer);
        }
    }
}
