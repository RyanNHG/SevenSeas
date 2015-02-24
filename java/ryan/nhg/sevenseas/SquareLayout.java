package ryan.nhg.sevenseas;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by ryan on 10/29/14.
 */
public class SquareLayout extends LinearLayout
{

    public SquareLayout(Context context)
    {
        super(context);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int min = Math.min(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(min, min);
    }

}