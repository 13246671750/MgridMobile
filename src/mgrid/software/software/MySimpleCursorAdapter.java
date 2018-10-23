package mgrid.software.software;

/*author:conowen 
 * date:2012.4.2 
 * MySimpleCursorAdapter 
 */  
 
  
import android.content.Context;  
import android.database.Cursor;  
import android.graphics.Color;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.SimpleCursorAdapter;  
import mgrid.software.software.R;
  
public class MySimpleCursorAdapter extends SimpleCursorAdapter {  
  
    public MySimpleCursorAdapter(Context context, int layout, Cursor c,  
            String[] from, int[] to) {  
        super(context, layout, c, from, to);  
        // TODO Auto-generated constructor stub  
  
    }  
  
    @Override  
    public View getView(final int position, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  
        // listviewÿ�εõ�һ��item����Ҫviewȥ���ƣ�ͨ��getView�����õ�view  
        // positionΪitem�����  
        View view = null;  
        if (convertView != null) {  
            view = convertView;  
            // ʹ�û����view,��Լ�ڴ�  
            // ��listview��item����ʱ���϶�����סһ����item������ס��item��view����convertView�����š�  
            // ���������ص�֮ǰ����ס��itemʱ��ֱ��ʹ��convertView����������ȥnew view()  
  
        } else {  
            view = super.getView(position, convertView, parent);  
  
        }  
  
        int[] colors = { Color.BLACK, Color.BLACK };//RGB��ɫ  
  
        view.setBackgroundColor(colors[position % 2]);// ÿ��item֮����ɫ��ͬ  
  
        return super.getView(position, view, parent);  
    }  
  
}  
