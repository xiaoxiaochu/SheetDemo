package com.go.sheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ActionSheet.OnSheetItemClickListener{
    private Button btn1;
    private ActionSheet actionSheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSheet=new ActionSheet(MainActivity.this)
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("发送给好友", ActionSheet.SheetItemColor.Blue,
                                MainActivity.this).setTitle("通知栏");
//                        .addSheetItem("转载到空间相册", ActionSheet.SheetItemColor.Blue,
//                                MainActivity.this)
//                        .addSheetItem("上传到群相册", ActionSheet.SheetItemColor.Blue,
//                                MainActivity.this)
//                        .addSheetItem("保存到手机", ActionSheet.SheetItemColor.Blue,
//                        MainActivity.this)
//                        .addSheetItem("收藏", ActionSheet.SheetItemColor.Blue,
//                                MainActivity.this)
//                        .addSheetItem("查看聊天图片", ActionSheet.SheetItemColor.Blue,
//                                MainActivity.this)
//                        .addSheetItem("保存到手机", ActionSheet.SheetItemColor.Blue,
//                                MainActivity.this)
//                        .addSheetItem("收藏", ActionSheet.SheetItemColor.Blue,
//                                MainActivity.this)
//                        .addSheetItem("查看聊天图片", ActionSheet.SheetItemColor.Blue,
//                                MainActivity.this)
//                        .addSheetItem("保存到手机", ActionSheet.SheetItemColor.Blue,
//                                MainActivity.this)
//                        .addSheetItem("收藏", ActionSheet.SheetItemColor.Blue,
//                                MainActivity.this)
//                        .addSheetItem("查看聊天图片", ActionSheet.SheetItemColor.Blue,
//                                MainActivity.this);
                actionSheet.show();
            }
        });
    }
    @Override
    public void onClick(int which) {
        Toast.makeText(MainActivity.this,"我被点击了"+which, 1).show();
    }
}
