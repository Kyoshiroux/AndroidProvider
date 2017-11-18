package com.example.ptj.androidprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity 
        implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);
        //add listerner
        findViewById(R.id.btQuery).setOnClickListener(this);
        findViewById(R.id.btInsert).setOnClickListener(this);
        findViewById(R.id.btFind).setOnClickListener(this);
        findViewById(R.id.btUpdate).setOnClickListener(this);
        findViewById(R.id.btDelete).setOnClickListener(this);
        findViewById(R.id.btList).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        EditText etStudentCode=(EditText)findViewById(R.id.editText);
        EditText etStudentName=(EditText)findViewById(R.id.editText2);
        EditText etAddress=(EditText)findViewById(R.id.editText3);
        EditText etAge=(EditText)findViewById(R.id.editText4);
        Uri uri=Uri.parse("content://db1.abc.com");


        switch (view.getId())
        {
            case  R.id.btList:
                Cursor cAll=getContentResolver().query(uri,
                        null, //project=>columns
                        null, //selection=>where
                        null, //selectionArgs=>where data
                        null); //order
                SimpleCursorAdapter adapter=
                        new SimpleCursorAdapter(
                                this,
                                R.layout.list_item_layout,
                                cAll,
                                new String[]{"studentCode",
                                "studentName",
                                "age"},
                                new int[]{
                                        R.id.textView,
                                        R.id.textView2,
                                        R.id.textView3
                                },
                                1
                        );
                ListView lv=(ListView)findViewById(R.id.listView);
                lv.setAdapter(adapter);
            case R.id.btDelete:
                ContentValues values2=new ContentValues();
                values2.put("studentName",etStudentName.getText().toString());
                values2.put("address",etAddress.getText().toString());
                values2.put("age",etAge.getText().toString());
                int dlt=getContentResolver().delete(
                        uri,
                        "studentCode=?",
                        new String[]{etStudentCode.getText().toString()}
                );
                Toast.makeText(this,""+dlt+" delete",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.btUpdate:
                ContentValues values=new ContentValues();
                values.put("studentName",etStudentName.getText().toString());
                values.put("address",etAddress.getText().toString());
                values.put("age",etAge.getText().toString());
                int rowcount=getContentResolver().update(
                        uri,
                        values,
                        "studentCode=?",
                        new String[]{etStudentCode.getText().toString()}
                );
                Toast.makeText(this,""+rowcount+" upadate",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.btFind:
                Cursor cFind=getContentResolver().query(
                        uri,
                        null, //projection
                        "studentName like ? COLLATE NOCASE",//selection
                        new String[]{etStudentName.getText().toString()+"%"},
                        null
                );
                if (cFind.getCount()>0)
                {
                    cFind.moveToNext();
                    etStudentCode.setText(cFind.getString(1));
                    etAddress.setText(cFind.getString(3));
                    etAge.setText(""+cFind.getInt(4));
                }
                break;

            case R.id.btInsert:
                ContentValues values1=new ContentValues();
                values1.put("studentCode",etStudentCode.getText().toString());
                values1.put("studentName",etStudentName.getText().toString());
                values1.put("address",etAddress.getText().toString());
                values1.put("age",etAge.getText().toString());
                Uri result=getContentResolver().insert(uri,values1);
                Toast.makeText(this,result.toString(),
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.btQuery:
                //provider location
                uri = Uri.parse("content://db1.abc.com");
                //connect to provider
                Cursor c=getContentResolver().query(uri,
                        null, //project=>columns
                        null, //selection=>where
                        null, //selectionArgs=>where data
                        null); //order
                while (c.moveToNext())
                {
                    Toast.makeText(this,
                            c.getString(2),
                            Toast.LENGTH_LONG).show();
                }
        }
    }
}
