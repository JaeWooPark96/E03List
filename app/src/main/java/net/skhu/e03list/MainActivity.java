package net.skhu.e03list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnListView_clicked(View view) {
        //해당 Activity 로 넘어감
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    public void btnRecyclerView1_clicked(View view) {
        //해당 Activity 로 넘어감
        Intent intent = new Intent(this, RecyclerView1Activity.class);
        startActivity(intent);
    }

    public void btnRecyclerView2_clicked(View view) {
        //해당 Activity 로 넘어감
        Intent intent = new Intent(this, RecyclerView2Activity.class);
        startActivity(intent);
    }

    public void btnRecyclerView3_clicked(View view) {
        //해당 Activity 로 넘어감
        Intent intent = new Intent(this, RecyclerView3Activity.class);
        startActivity(intent);
    }

}
