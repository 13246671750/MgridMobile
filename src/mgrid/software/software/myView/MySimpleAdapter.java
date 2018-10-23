package mgrid.software.software.myView;

import java.util.List;
import java.util.Map;

import mgrid.software.software.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MySimpleAdapter extends SimpleAdapter{

	private List<? extends Map<String,?>> mData;
	
	private int mResuorce;
	
	private LayoutInflater mInflater;
	
	private String[] mFrom;
	
	private int[] mTo;
	
	private ViewBinder mViewBinder;
	
	private Context context;
	
	private ImageView image;
	
	private TextView text;
	
	private Button btnOpen,btnClose;
	public MySimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		mData=data;
		mResuorce=resource;
		mFrom=from;
		mTo=to;
		mInflater=LayoutInflater.from(context);
		this.context=context;
	}
	
	@Override
	public int getCount() {
		
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		
		return mData.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return creatViewFromResrouce(position, convertView, parent, mResuorce);
	}

	private View creatViewFromResrouce(int position, View convertView,
			ViewGroup parent,int resource) {
		
		convertView=mInflater.inflate(resource, null);
		image=(ImageView) convertView.findViewById(R.id.image);
		text=(TextView) convertView.findViewById(R.id.equipment_list_Name);
	//	btnOpen=(Button) convertView.findViewById(R.id.btnOpen);
//		btnClose=(Button) convertView.findViewById(R.id.btnClose);
		image.setImageResource((Integer) mData.get(position).get("image"));
		text.setText((String)mData.get(position).get("name"));
		btnOpen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Toast.makeText(context, "¿ª", 1000).show();
			}
		});
		btnClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Toast.makeText(context, "¹Ø", 1000).show();
			}
		});
	    
		return convertView;
	}


	
	
}
