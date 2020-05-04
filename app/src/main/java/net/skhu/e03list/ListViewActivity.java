package net.skhu.e03list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    ArrayList<String> arrayList;//보통 자주 사용할 것 같으면, 이렇게 class 의 멤버 필드로 선언합니다.
    ArrayAdapter<String> adapter;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        arrayList = new ArrayList<String>();//ArrayList 생성
        arrayList.add("One");
        arrayList.add("Two");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);//ArrayAdapter 생성
        ListView listView = (ListView)findViewById(R.id.listView);//ListView 객체를 찾아주었습니다.
        listView.setAdapter(adapter);//ListView에 adapter등록

        editText = (EditText) findViewById(R.id.editText);

        Button b = (Button)findViewById(R.id.btnAdd);//ok버튼의 리스너
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CharSequence s = editText.getText();//editText 내용 가져와 줍니다.
                editText.setText("");//editText 에 아무것도 없는 상태로 바꿉니다.
                arrayList.add(s.toString());//editText에서 가져온 내용 arrayList에 추가
                adapter.notifyDataSetChanged();//adapter를 사용해서 ListView를 다시 그려주었습니다.
            }
        });
    }
}
