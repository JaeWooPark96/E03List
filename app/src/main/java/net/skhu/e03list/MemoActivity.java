package net.skhu.e03list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class MemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        //findViewById는 onCreate에서 1번 해주는 것이 좋습니다. findViewById는 생각보다 느립니다.
        final EditText editText_title = (EditText) findViewById(R.id.editText_title);
        final EditText editText_content = (EditText) findViewById(R.id.editText_content);

        //Memo를 데이터 전달 받았습니다. 객체를 전달 받는 것이기 때문에, getSerializableExtra를 사용합니다.
        Memo memo = (Memo)getIntent().getSerializableExtra("MEMO");
        if (memo != null) {//memo의 내용대로 EditText 들을 채웁니다.
            //memo가 null일 수도 있기 때문에, if문을 주었습니다.(생성일 때)
            editText_title.setText(memo.getTitle());
            editText_content.setText(memo.getBody());
        }

        Button button = (Button) findViewById(R.id.btnSave);
        View.OnClickListener listener = new View.OnClickListener() {//저장 버튼의 리스너입니다.
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();//editText_title의 내용을 저장합니다.
                if (isEmptyOrWhiteSpace(title)) {//해당 String이 비어있는지 검사해주었습니다.
                    editText_title.setError("제목을 입력하세요");
                    return;
                }

                String body = editText_content.getText().toString();//editText_content의 내용을 저장합니다.
                if (isEmptyOrWhiteSpace(body)) {
                    editText_content.setError("내용을 입력하세요");
                    return;
                }

                Memo memo = new Memo(title, body, new Date());
                Intent intent = new Intent();//Activity 호출, 반환할 때 intent 사용합니다.
                intent.putExtra("MEMO", memo);//memo 객체를 반환합니다. 여기서 중요한 것은 해당 memo객체의 class 에서 Serializable 가 implements 되었는지 입니다.
                setResult(RESULT_OK, intent);//RESULT_OK로 리턴합니다. 보통 리턴 성공일 때, RESULT_OK를 사용합니다.
                finish();//현제 화면 종료, 이전화면으로 되돌아 옵니다.
            }
        };
        button.setOnClickListener(listener);//리스너 등록입니다.
    }

    static boolean isEmptyOrWhiteSpace(String s) {//해당 String이 비어있는지 검사해주는 함수입니다.
        if (s == null) return true;
        return s.toString().trim().length() == 0;
    }
}
