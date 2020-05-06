package net.skhu.e03list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

public class RecyclerView3Activity extends AppCompatActivity {
    public static final int REQUEST_CREATE = 0;
    public static final int REQUEST_EDIT = 1;

    int memoIndex;
    RecyclerView3Adapter recyclerView3Adapter;
    //ArrayList<Memo> arrayList;

    //EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view3);

        //editText = (EditText) findViewById(R.id.editText);

        /*recyclerView3Adapter = new RecyclerView3Adapter(this, arrayList, new OnMemoClickListener() {
            @Override
            public void onMemoClicked(int index) {
                memoIndex = index;
                startMemoActivityForResult(REQUEST_EDIT, arrayList.get(index));
            }
        });//RecyclerView3Adapter 생성, 우리가 만들어준 Adapter를 생성한 것입니다.

         */

        //RecyclerView3Adapter 생성, 우리가 만들어준 Adapter를 생성한 것입니다.
        recyclerView3Adapter = new RecyclerView3Adapter(this,
                (memo) -> startMemoActivityForResult(REQUEST_EDIT, memo),//Lambda Expressions 함수 주의 사항은,  여기선  우리가 정의한 Interface 와 매개변수등 맞추어서 코딩해야 한다는 점입니다.
                (count) -> { if (count <= 1) invalidateOptionsMenu(); });//Lambda Expressions 함수 주의 사항은,  여기선  우리가 정의한 Interface 와 매개변수등 맞추어서 코딩해야 한다는 점입니다.
        //Lambda Expressions 에서, ->이후는 함수의 몸체입니다.//매개변수 인자전달은 RecyclerView3Adapter 에서 합니다.
        //Lambda Expressions 함수는 익명 함수와 비슷하다고 보면 됩니다.//Lambda Expressions 함수는 이미 정의된 해당 Interface 로만 정의되어야 한다.

        //RecyclerView 초기 설정
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);//RecyclerView 객체를 찾아주었습니다.
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerView3Adapter);//recyclerView에 recyclerView3Adapter를 등록해주었습니다.

        /*//ok 버튼 (추가버튼) 을 찾아서, 리스너를 등록해주었습니다.
        //버튼이 눌리면, EditText의 내용을 arrayList에 저장하고 recyclerView2Adapter로 recyclerView를 다시 그려주도록 했습니다.
        Button b = (Button)findViewById(R.id.btnAdd);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String s = editText.getText().toString();//editText의 내용을 가져왔습니다.
                editText.setText("");
                arrayList.add(new Memo(s, new Date()));//arrayList에 해당 내용을 추가해주었습니다.
                //여기서 editText의 내용, new Date()를 추가해주었습니다.(현재 시간으로 추가해주었습니다.)
                recyclerView3Adapter.notifyDataSetChanged();//recyclerView2Adapter로 recyclerView를 다시 그려주도록 했습니다.
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//메뉴를 생성할 때, 호출됩니다.
        getMenuInflater().inflate(R.menu.menu_recycler_view3, menu);//R.menu.menu_recycler_view3 대로 메뉴를 그려줍니다.

        //체크된 항목 있어야, 삭제 메뉴 보이도록 해주었습니다.
        MenuItem menuItem = menu.findItem(R.id.action_remove);//삭제 메뉴 객체를 찾아줍니다.
        menuItem.setVisible(recyclerView3Adapter.getCheckedCount() > 0);//현제 recyclerView2Adapter로, RecyclerView에 체크된 항목이 없으면, 삭제 메뉴가 안보이도록 해주었습니다.
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {//메뉴가 클릭될 때의 리스너입니다.
        int id = item.getItemId();
        if (id == R.id.action_create) {//생성 버튼 눌릴 때
            //MemoActivity으로 넘어가도록 해주었습니다.
            //Intent intent = new Intent(this, MemoActivity.class);
            //startActivityForResult(intent, REQUEST_CREATE);//리턴 받을 때, 구분을 위해서 startActivityForResult로 REQUEST_CREATE 전달 했습니다.
            startMemoActivityForResult(REQUEST_CREATE, null);
            return true;
        } else if (id == R.id.action_remove) {//삭제 버튼 눌릴 때
            //deleteItems();//체크된 항목을 삭제하도록 해주었습니다.

            //대화상자로 삭제할 것인지 여부를 물어주었습니다.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.confirm);
            builder.setMessage(R.string.doYouWantToDelete);
            builder.setPositiveButton(R.string.yes, (dialog, index) -> recyclerView3Adapter.removeCheckedMemo());//Lambda Expressions 은 이미 있는 리스너로 정의되어야 한다.
            //(dialog, index) 는 dialog 의  onClick 리스너에 인자전달하는 것이다.
            //Lambda Expressions 함수 주의 사항은, 여기선 이미 정의된 함수의 원형을 맞추어 짜야 합니다.
            builder.setNegativeButton(R.string.no, null);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);//모르는 메뉴 눌렸을 때 호출
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {//자식 Activity 끝나면 여기로 옵니다.
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {//정상적으로 반환 되었을 때
            Memo memo = (Memo)intent.getSerializableExtra("MEMO");//메모 객체를 반환 받았습니다.
            //보통 위에 처럼 Memo 객체 리턴 받습니다.
            if (requestCode == REQUEST_CREATE)//생성 목적 intent 에서 반환 받을 때
                recyclerView3Adapter.add(memo);//추가
            else if (requestCode == REQUEST_EDIT)//클릭해서 수정 목적 intent 에서 반환 받을 때
                recyclerView3Adapter.update(memo);//수정//memoIndex 는 수정 항목의 index 입니다. memoIndex 가 있어야 수정이 가능합니다.

            recyclerView3Adapter.notifyDataSetChanged();//RecyclerView를 다시그려주었습니다.
        }
    }

   /* private void deleteItems() {//체크된 항목을 삭제하도록 하는 함수입니다.
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
                recyclerView3Adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.no, null);//아니오 버튼
        AlertDialog dialog = builder.create();//AlertDialog 생성
        dialog.show();//AlertDialog를 보여주었습니다.
    }*/

    //MemoActivity 를 호출하는 함수를, RecyclerView3Activity 한 곳에 모으기 위해서 이렇게 했습니다.//다른 Activity 를 호출하는 코드는 액티비티 내부에 구현//RecyclerView3Adapter 는 Activity 를 참조하지 않는 게 좋다.
    private void startMemoActivityForResult(int requestCode, Memo memo) {//requestCode와 Memo 객체로 intent하는 함수입니다.
        Intent intent = new Intent(this, MemoActivity.class);
        intent.putExtra("MEMO", memo);
        startActivityForResult(intent, requestCode);
    }

}
