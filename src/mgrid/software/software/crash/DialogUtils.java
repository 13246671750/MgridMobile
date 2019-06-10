package mgrid.software.software.crash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;

public class DialogUtils {

	private DialogUtils() {

	}

	private static DialogUtils dialogUtils = new DialogUtils();

	public static DialogUtils getDialog() {
		return dialogUtils;
	}

	public void showDialog(Context context, String title, String content) {
		new AlertDialog.Builder(context).setTitle(title).setMessage(content).show();
	}

	public void showOnClickDialog(Context context, String title, String content,String postText,OnClickListener postlis,String negaText,OnClickListener negalis)
	{
		new AlertDialog.Builder(context).setTitle(title).setMessage(content).setPositiveButton(postText,postlis).setNegativeButton(negaText, negalis).create().show();
	}
	
	public View showLayoutDialog(Context context,String title,int layout,String postText,OnClickListener postlis,String negaText,OnClickListener negalis,int style)
	{
		LayoutInflater inflater=LayoutInflater.from(context);
		View view=inflater.inflate(layout, null);
		
//		Dialog mMainWindow=new Dialog(context,style);
//		mMainWindow.setTitle("”√ªß");
//		mMainWindow.setContentView(view);  
//		mMainWindow.setCancelable(true);
//		mMainWindow.show();
//		Window dialogWin = mMainWindow.getWindow();
//		WindowManager.LayoutParams lp = dialogWin.getAttributes();
//		lp.width = LayoutParams.WRAP_CONTENT;	
//		lp.height = LayoutParams.WRAP_CONTENT;
//		dialogWin.setAttributes(lp);
	
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		
		builder.setTitle(title).setView(view).setPositiveButton(postText,postlis).setNegativeButton(negaText, negalis);
		builder.show();	
		
		return view;
		
	}
	
	
}
