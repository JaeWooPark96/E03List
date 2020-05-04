package net.skhu.e03list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

public class RecyclerView2Activity extends AppCompatActivity {

    RecyclerView2Adapter recyclerView2Adapter;
    ArrayList<Memo> arrayList;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view2);



        arrayList = new ArrayList<Memo>();//RecyclerView에 사용할 ArrayList를 생성해주었습니다.
        arrayList.add(new Memo("one", new Date()));
        arrayList.add(new Memo("two", new Date()));

        recyclerView2Adapter = new RecyclerView2Adapter(this, arrayList);//RecyclerView2Adapter 생성, 우리가 만들어준 Adapter를 생성한 것입니다.

        //RecyclerView 초기 설정
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);//RecyclerView 객체를 찾아주었습니다.
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerView2Adapter);//recyclerView에 recyclerView2Adapter를 등록해주었습니다.

        //ok 버튼 (추가버튼) 을 찾아서, 리스너를 등록해주었습니다.
        //버튼이 눌리면, EditText의 내용을 arrayList에 저장하고 recyclerView2Adapter로 recyclerView를 다시 그려주도록 했습니다.
        Button b = (Button)findViewById(R.id.btnAdd);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String s = editText.getText().toString();//editText의 내용을 가져왔습니다.
                editText.setText("");
                arrayList.add(new Memo(s, new Date()));//arrayList에 해당 내용을 추가해주었습니다.
                //여기서 editText의 내용, new Date()를 추가해주었습니다.(현재 시간으로 추가해주었습니다.)
                recyclerView2Adapter.notifyDataSetChanged();//recyclerView2Adapter로 recyclerView를 다시 그려주도록 했습니다.
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//메뉴를 생성할 때, 호출됩니다.
        getMenuInflater().inflate(R.menu.menu_recycler_view2, menu);//R.menu.menu_recycler_view2 대로 메뉴를 그려줍니다.

        //체크된 항목 있어야, 삭제 메뉴 보이도록 해주었습니다.
        MenuItem menuItem = menu.findItem(R.id.action_remove);//삭제 메뉴 객체를 찾아줍니다.
        menuItem.setVisible(recyclerView2Adapter.checkedCount > 0);//현제 recyclerView2Adapter로, RecyclerView에 체크된 항목이 없으면, 삭제 메뉴가 안보이도록 해주었습니다.
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {//메뉴가 클릭될 때의 리스너입니다.
        int id = item.getItemId();
        if (id == R.id.action_remove) {//삭제 버튼 눌릴 때
            deleteItems();//체크된 항목을 삭제하도록 해주었습니다.
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteItems() {//체크된 항목을 삭제하도록 하는 함수입니다.
        //dialog로 삭제여부를 물어봤습니다.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.doYouWantToDelete);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {//예 버튼을 누를 때 listener
            @Override
            public void onClick(DialogInterface dialog, int index) {
                ListIterator<Memo> iterator = arrayList.listIterator();//arrayList로부터 iterator를 얻어주었습니다.
                //iterator는 포인터라고 보시면 됩니다. 해당 요소 주소를 저장합니다.
                while (iterator.hasNext()) {//다음 iterator가 없을 때까지 반복합니다.
                    if (iterator.next().isChecked()) {//미리 다음 iterator로 넘어가기 때문에, 문제없이 작동합니다. iterator를 사용하기 때문에, arrayList요소의 동시 접근을 막을 수 있다.
                        // arrayList.remove()를 사용해서 for문 돌리면, 잘못하면, 한 요소에 동시접근할 수 있다.
                        iterator.remove();//iterator 객체 삭제
                    }
                }

                //삭제 완료후 RecyclerView 다시 그려주었습니다.
                recyclerView2Adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.no, null);//아니오 버튼
        AlertDialog dialog = builder.create();//AlertDialog 생성
        dialog.show();//AlertDialog를 보여주었습니다.
    }
}
