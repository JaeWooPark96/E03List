package net.skhu.e03list;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//Serializable는 구현할 수 없습니다. 안에 함수가 없는 것이 맞는 표현입니다.
public class Memo implements Serializable {//Serializable interface 는 marking interface 입니다. 이거를 붙이거나, 이것의 자식 class에 한해서, java virtual machine 이 직렬화, 역직렬화 해줍니다.
    //같은 앱이라도 android에서 서로 주고 받는 data는 직렬화 되야 합니다.
    final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//이 포멧으로 날짜출력
    String title;//제목
    String body;//내용
    Date date;//날짜를 저장합니다. 여기에는 말 그대로 날짜가 저장됩니다.//그래서 휴대폰 설정에 따라, 한국 시간하고 미국 시간하고 다르게 나옵니다.
    boolean checked;//체크되었는지 여부

    public Memo(String title, Date date) {//Memo 객체 생성,
        this.title = title;//제목 저장
        this.date = date;//date 에는 해당 시간이 저장됩니다.
    }

    public Memo(String title, String body, Date date) {
        this.title = title;//제목 저장
        this.body = body;//내용 저장
        this.date = date;//date 에는 해당 시간이 저장됩니다.
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateFormatted() {//date에 해당하는 시각을 위의 SimpleDateFormat("yyyy-MM-dd HH:mm:ss") 포멧대로 문자열로 return합니다.
        return format.format(date);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
