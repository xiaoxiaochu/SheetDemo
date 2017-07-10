package com.go.sheet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by go on 2017/7/8.
 */
public class ActionSheet {


    private Context context;

    private Dialog dialog;

    private TextView txt_title;//标题

    private TextView txt_cancel;//取消按钮

    private LinearLayout lLayout_content;

    private ScrollView sLayout_content;

    private boolean showTitle = false;

    private List<SheetItem> sheetItems;

    private  DisplayMetrics dm;


    public ActionSheet(Context context) {
        this.context = context;
        dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
       windowManager.getDefaultDisplay().getMetrics(dm);


    }



    public ActionSheet builder(){

        View view = LayoutInflater.from(context).inflate(R.layout.view_actionsheet,null);

        view.setMinimumWidth(dm.widthPixels);

        sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
        lLayout_content = (LinearLayout) view.findViewById(R.id.lLayout_content);

        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);


        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog = new Dialog(context,R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);


        Window dialogWindow =dialog.getWindow();

        dialogWindow.setGravity(Gravity.LEFT|Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();



        lp.x = 0;
        lp.y = 0;

        dialogWindow.setAttributes(lp);

        return this;
    }


    public ActionSheet setTitle(String title){
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(title);
        return this;
    }



    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColor color;

        public SheetItem(String name, SheetItemColor color,
                         OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }

    public ActionSheet setCancelable(boolean cancel){
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheet setCanceledOnTouchOutside(boolean cancel){
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }



    public ActionSheet addSheetItem(String strItem,SheetItemColor sheetItemColor,OnSheetItemClickListener sheetItemClickListener){
        if (sheetItems == null){
            sheetItems = new ArrayList<SheetItem>();
        }

        sheetItems.add(new SheetItem(strItem,sheetItemColor,sheetItemClickListener));
        return this;
    }


    private void setSheetItems(){
        if (sheetItems == null || sheetItems.size() <= 0){
            return;
        }

        int size = sheetItems.size();
        if (size > 8){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sLayout_content.getLayoutParams();
            params.height = dm.heightPixels * 3 /5;
            sLayout_content.setLayoutParams(params);
        }

        for (int i = 1 ;i <= size;i++){
            final int index = i;
            SheetItem sheetItem = sheetItems.get(i -1);
            String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            final  OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

            TextView textView = new TextView(context);
            textView.setText(strItem);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);

            if (size == 1) {
                if (showTitle) {
                    textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.actionsheet_single_selector);
                }
            } else {
                if (showTitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                }
            }



            // 字体颜色
            if (color == null) {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue
                        .getName()));
            } else {
                textView.setTextColor(Color.parseColor(color.getName()));
            }


            // 高度
            float scale = context.getResources().getDisplayMetrics().density;//获取屏幕密度
            int height = (int) (45 * scale + 0.5f);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, height));//textView设置宽高


            // 点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    dialog.dismiss();
                }
            });

            lLayout_content.addView(textView);
        }
    }


    public void show(){

        setSheetItems();
        dialog.show();
    }


    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E");
        private String name;

        private SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
