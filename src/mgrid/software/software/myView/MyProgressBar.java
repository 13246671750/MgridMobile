package mgrid.software.software.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class MyProgressBar extends ProgressBar {

	String text;
	Paint  mPaint;

	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		initText();

	}

	public MyProgressBar(Context context) {
		super(context);

		initText();
	}

	private void initText() {
		
		this.mPaint = new Paint();
		this.mPaint.setColor(Color.WHITE);

	}

	public void setText(String progress) {
		
		this.text = progress;
		
		
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
		Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX(); 
        int y = (getHeight() / 2) - rect.centerY(); 
        canvas.drawText(this.text, x, y, this.mPaint); 
	}
	

	
}
