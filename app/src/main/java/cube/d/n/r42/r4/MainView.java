package cube.d.n.r42.r4;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;import java.lang.Override;

/**
 * Created by Colin_000 on 4/3/2015.
 */
public class MainView extends GLSurfaceView implements View.OnTouchListener {

    private Cube cube;

    private MyGLRenderer mRenderer;

    public MainView(Context context) {
        super(context);
        //init();

        init();
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Cube getCube() {
        return cube;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }

    private void init() {
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8 , 8, 8, 8, 16, 0);

        mRenderer = new MyGLRenderer(this);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }

    public void activate() {
        setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Log.i("stop touching me!", event.getAction()+"");

        int[] yo = new int[2];
        getLocationOnScreen(yo);
        DrawInfo base = new DrawInfo(new ToDraw());//,0,canvas.getHeight(),0,canvas.getWidth()
        cube.setBounds(0, 0, getWidth(), getHeight(), base);

        cube.onTouch(event);
        return true;
    }
}
