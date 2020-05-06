package net.skhu.e03list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.ListIterator;

//RecyclerView3Adapter 가 다른 class 를 참조하지 않도록 고쳐야 한다.
public class RecyclerView3Adapter extends RecyclerView.Adapter<RecyclerView3Adapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder//평범한 class로 리스너 구현 가능
            implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {//리스너 ViewHolder에서 구현//data 항목 1개에 대한 리스너는 ViewHolder에 몰아서 구현
        TextView textView1, textView2;
        CheckBox checkBox;

        //데이터가 항목마다 다르기 때문에,  ViewHolder 필요, 어떤 data에 있는 textView에 대해 findViewById 합니다.
        public ViewHolder(View view) {//ViewHolder 사용하는 이유는 findViewById등을 하기 위함입니다. 그리고 각 리스너도 등록합니다.
            super(view);

            //참고로 여기서 view 는 해당 항목의 view입니다. 그래서 다른 항목의 view하고는 전혀 다른 겁니다.
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            checkBox = view.findViewById(R.id.checkBox);
            textView1.setOnClickListener(this);//여기서 리스너로 자기 자신을 등록하는 이유는, implements View.OnClickListener, CompoundButton.OnCheckedChangeListener 했기 때문입니다.
            //View의 개수와 ViewHolder의 개수 같아야 됩니다. listener 개수도 다 같아야 합니다. listener 객체와 ViewHolder 의 개수를 갖게하는 방법, 이 객체가 ViewHolder이자 listener 이면 됩니다.
            //listener 개수 == ViewHolder 개수 하려면, 이 객체가 ViewHolder이자 listener 이면 됩니다.
            checkBox.setOnCheckedChangeListener(this);
        }

        public void setData() {//데이터를 채우는 함수입니다.
            Memo memo = arrayList.get(getAdapterPosition());//arrayList로부터 해당 요소 데이터 가져옵니다.

            //각 view를 데이터에 맞게 채워줍니다.
            textView1.setText(memo.getTitle());
            textView2.setText(memo.getDateFormatted());
            checkBox.setChecked(memo.isChecked());
        }

        @Override
        public void onClick(View view) {//해당 항목이 클릭되었을 때의 리스너입니다.

            selectedIndex = super.getAdapterPosition();
            //listener.onMemoClicked(index);//Lambda Expressions 으로 구현된 함수에 인자전달 해주었습니다.
            onMemoClickListener.onMemoClicked(arrayList.get(selectedIndex));//Lambda Expressions 으로 구현된 함수에 인자전달 해주었습니다.
            //이렇게 해서 RecyclerView3Adapter 는 다른 class 를 참조하지 않는다.
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//체크 상태가 변할 때의 리스너
            Memo memo = arrayList.get(super.getAdapterPosition());//클릭된 항목의 데이터를 arrayList로부터 가져왔습니다.
            memo.setChecked(isChecked);//memo의 현제 체크 상태를 체크 박스의 체크상태와 맞추어 주었습니다.
            if (isChecked) ++checkedCount;//현제 체크가 된 거면, 체크 상테인 항목이 한개 증가한 것이여서  ++checkedCount 해주었습니다.
            else --checkedCount;//현제 체크가 해제된 거면, 체크 상테인 항목이 한개 감소한 것이여서  --checkedCount 해주었습니다.

            if (checkedCount < 0){//전체 삭제하면, -1되서, 0으로 다시 맞추어 주었습니다.
                checkedCount = 0;
            }

            onCheckCountChangeListener.onCheckCountChanged(checkedCount);//Lambda Expressions 으로 구현된 함수에 인자전달 해주었습니다.
            //이렇게 해서 RecyclerView3Adapter 는 다른 class 를 참조하지 않는다.

            Log.v("pjw", "onCheckedChanged! checkedCount: "+checkedCount);
        }
    }

    LayoutInflater layoutInflater;
    ArrayList<Memo> arrayList;
    int checkedCount = 0;//체크된 항목 개수 저장
    int selectedIndex;
    OnMemoClickListener onMemoClickListener;//Lambda Expressions 으로 구현된 class 를 가리킬 레퍼런스입니다.
    OnCheckCountChangeListener onCheckCountChangeListener;//Lambda Expressions 으로 구현된 class 를 가리킬 레퍼런스입니다.

    public void setCheckedCount(int checkedCount) {
        this.checkedCount = checkedCount;
    }

    public int getCheckedCount() {
        return checkedCount;
    }

    public RecyclerView3Adapter(Context context,
                                OnMemoClickListener onMemoClickListener,
                                OnCheckCountChangeListener onCheckCountChangeListener) {//RecyclerView1Adapter 생성자(Constructor)
        this.layoutInflater = LayoutInflater.from(context);//layout inflate
        //this.arrayList = arrayList;//목록의 arrayList
        this.arrayList = new ArrayList<Memo>();//arrayList를 RecyclerView3Adapter 안에 있도록 했습니다.
        //이렇게 하는 이유는 다른 class 의 멤버에 자꾸 접근하는 것을 방지하기 위함입니다.
        //서로 독립적이게 움직이도록 해주어야 합니다.//참조하는 클래스를 최소화하기 위함입니다.
        //메모 목록 관리를 RecyclerView3Adapter 에 전부 몰아주었습니다.//목록 관리하는 코드를 한 클래스에 몰아서 구현하는 것이 좋다.

        this.onMemoClickListener = onMemoClickListener;//Lambda Expressions 으로 구현된 class 를 업케스팅 해주었습니다.
        this.onCheckCountChangeListener = onCheckCountChangeListener;//Lambda Expressions 으로 구현된 class 를 업케스팅 해주었습니다.
    }

    @Override
    public int getItemCount() {//크기 반환
        return arrayList.size();
    }

    @Override
    public RecyclerView3Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {//ViewHolder 만들 때의 호출되는 함수입니다.
        View view = layoutInflater.inflate(R.layout.memo2, viewGroup, false);//R.layout.memo2 대로 그려주었습니다.
        //viewGroup은 RecyclerView입니다.
        //View가 만들어져서, rootView가 만들어진 것입니다.

        return new RecyclerView3Adapter.ViewHolder(view);//ViewHolder만들어서 리턴, 위의 ViewHolder 생성자(Constructor)가 호출된 것. 확인하고 싶으면, ctrl+클릭
        //만들어진 View객체를 ViewHolder 에 넣어야 하니까 이렇게 return 한 것입니다.
    }

    @Override
    public void onBindViewHolder(final RecyclerView3Adapter.ViewHolder viewHolder, final int index) {//데이터를 할당하는 함수, 데이터 세팅하는 함수
        viewHolder.setData();//해당 viewHolder의 데이터를 채워주었습니다.
    }


    //목록 관리 함수
    public void add(Memo memo) {//추가할 때, RecyclerView3Adapter 멤버 변수에 추가합니다.
        arrayList.add(memo);
        notifyItemInserted(arrayList.size() - 1);
    }

    //목록 관리 함수
    public void update(Memo memo) {//업데이트할 때, RecyclerView3Adapter 멤버 변수에 업데이트 합니다.
        arrayList.set(selectedIndex, memo);
        notifyItemChanged(selectedIndex);
    }

    //목록 관리 함수
    public void removeCheckedMemo() {//제거할 때, RecyclerView3Adapter 멤버 변수에서 제거합니다.
        ListIterator<Memo> iterator = arrayList.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().isChecked()) {
                iterator.remove();
            }
        }
        notifyDataSetChanged();

        checkedCount = 0;
        onCheckCountChangeListener.onCheckCountChanged(checkedCount);//Lambda Expressions 으로 구현된 함수에 인자전달 해주었습니다.
    }

}
