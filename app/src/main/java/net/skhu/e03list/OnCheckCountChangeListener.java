package net.skhu.e03list;

public interface OnCheckCountChangeListener {//인터페이스로써 구현해야할 함수들의 리스트를 정해주었습니다.
    void onCheckCountChanged(int count);//어떤 class 를 참조하지 않으면서 그 클래스의 메소드를 호출하기 위해서, 인터페이스를 사용해주었습니다.

    //메모 항목이 체크되었을 때, 실행될 메소드를 구현하기 위한 인터페이스이다.

    //메모 항목이 체크된 것을 파악하는 것은 RecyclerView3Adapter 내부에서 한다.

    //메모 항목이 체크되었을 때 해야할 작업은 RecyclerView3Activity 에서 해야 한다.
}
