package net.skhu.e03list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerView1Adapter extends RecyclerView.Adapter<RecyclerView1Adapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder  {//ViewHolder 클래스를 사용하는 이유는 리스너 때문입니다. 각 아이템마다 리스너를 다르게 주거나 하는 것입니다.
        TextView textView;//item 안의 TextView 객체를 미리 저장하기 위해서 이렇게 해주었습니다.

        public ViewHolder(View view) {//findViewById를 ViewHolder 생성자(Constructor)에서 모두하는 것이 효율에 좋습니다.
            super(view);
            textView = view.findViewById(R.id.textView);//해당 item의 view의 textView 객체를 찾아주었습니다.
        }
    }

    //여러번 써야 되서 멤버 변수에 넣었습니다.
    LayoutInflater layoutInflater;
    ArrayList<String> arrayList;

    public RecyclerView1Adapter(Context context, ArrayList<String> arrayList) {//RecyclerView1Adapter 생성자(Constructor)
        this.layoutInflater = LayoutInflater.from(context);//layout inflater
        this.arrayList = arrayList;//목록의 arrayList
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }//크기 반환

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {//ViewHolder 만들 때의 호출되는 함수입니다.
        View view = layoutInflater.inflate(R.layout.item1, viewGroup, false);//R.layout.item1 대로 그려주었습니다.
        //viewGroup은 RecyclerView입니다.
        //View가 만들어져서, rootView가 만들어진 것입니다.

        return new ViewHolder(view);//ViewHolder만들어서 리턴, 위의 ViewHolder 생성자(Constructor)가 호출된 것. 확인하고 싶으면, ctrl+클릭
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int index) {//데이터를 할당하는 함수, 데이터 세팅하는 함수
        viewHolder.textView.setText(arrayList.get(index));//전달된 viewHolder의 textView를 arrayList.get(index)로 설정해주었습니다.
        //지금 이건 ViewHolder가 1개여서 가능한 겁니다. 원래는 type별로 다르게 해야합니다.
    }
}
