package net.skhu.e03list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class RecyclerView1Activity extends AppCompatActivity {

    RecyclerView1Adapter recyclerView1Adapter;
    ArrayList<String> arrayList;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view1);

        editText = (EditText) findViewById(R.id.editText);

        arrayList = new ArrayList<String>();//RecyclerView에 사용할 ArrayList를 생성해주었습니다.
        arrayList.add("one");//추가
        arrayList.add("two");

        recyclerView1Adapter = new RecyclerView1Adapter(this, arrayList);//RecyclerView1Adapter 생성, 우리가 만들어준 Adapter를 생성한 것입니다.

        //RecyclerView 초기 설정
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);//RecyclerView 객체를 찾아주었습니다.
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerView1Adapter);//recyclerView에 recyclerView1Adapter를 등록해주었습니다.

        //ok 버튼 (추가버튼) 을 찾아서, 리스너를 등록해주었습니다.
        //버튼이 눌리면, EditText의 내용을 arrayList에 저장하고 recyclerView1Adapter로 recyclerView를 다시 그려주도록 했습니다.
        Button b = (Button)findViewById(R.id.btnAdd);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String s = editText.getText().toString();//editText의 내용을 가져왔습니다.
                editText.setText("");//editText 의 내용을 비워주었습니다.
                arrayList.add(s);//arrayList에 해당 내용을 추가해주었습니다.
                recyclerView1Adapter.notifyDataSetChanged();//recyclerView1Adapter로 recyclerView를 다시 그려주도록 했습니다.
            }
        });

    }

}
