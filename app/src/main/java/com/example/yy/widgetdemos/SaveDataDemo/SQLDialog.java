package com.example.yy.widgetdemos.SaveDataDemo;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yy.widgetdemos.R;

public class SQLDialog extends AlertDialog implements View.OnClickListener {

    private static final String TAG = "dialog";

    private TextView titleView;
    private String title;
    private EditText sqlText;
    private Button cancelBtn;
    private Button confirmBtn;
    private ButtonOnClickListener cancelBtnListener;
    private ButtonOnClickListener confirmBtnListener;

    public SQLDialog(Context context) {
        super(context,R.style.SQLDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sql);

        sqlText = findViewById(R.id.sql_text);
        //使AlertDialog可以弹出软键盘
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);


        cancelBtn = findViewById(R.id.cancel_btn);
        confirmBtn = findViewById(R.id.confirm_btn);
        titleView = findViewById(R.id.title_view);

        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);

        titleView.setText(title);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_btn:
                if (cancelBtnListener != null)
                    cancelBtnListener.buttonOnClick();
                Log.d(TAG,"cancel pushed");
                break;
            case R.id.confirm_btn:
                if (confirmBtnListener != null)
                    confirmBtnListener.buttonOnClick();
                break;
        }
    }


    public void setTitle(String title){
        this.title = title;
    }

    public void execSQL(SQLiteDatabase db){
        String sql = sqlText.getText().toString();
        db.execSQL(sql);
    }



    public interface ButtonOnClickListener{
        void buttonOnClick();
    }

    public void setCancelButtonListener(ButtonOnClickListener buttonListener){
        cancelBtnListener = buttonListener;
    }

    public void setConfirmButtonListener(ButtonOnClickListener buttonListener){
        confirmBtnListener = buttonListener;
    }
}
