package cube.d.n.r42.r4;

import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.Math;import java.util.Random;

/**
 * Created by Colin_000 on 4/2/2015.
 */
public class Side {
    static int[] colors = {
            0xffff0000, 0xffff7286,
            0xffff7700, 0xffffff00,
            0xff008000, 0xff00ff00,
            0xff0000ff, 0xff72d9ff,
            0xff9900cc, 0xffcc66ff,
            0xffffffff, 0xff323232
    };
    public final int size;
    // I think this is like 1-12
    public final int id;
    public final Cube owner;
    public Cube.Positions pos;
    /**
     * data[x][y]
     */
    public int[][] data;
    public MyPoint topl = new MyPoint();
    public MyPoint topr = new MyPoint();
    public MyPoint botl = new MyPoint();
    public MyPoint botr = new MyPoint();
    float line30 = 0;
    float line25 = 1f / (27f * 2);


//    static int[] colors = {
//            0xff220000,0xff880000,0xffdd0000,
//            0xff2200ff,0xff8800ff, 0xffdd00ff,
//            0xff22ff00,0xff88ff00,0xffddff00,
//            0xff22ffff,0xff88ffff,0xffddffff,
//    };


//    static int[] colors = {
//            0xff000000,0xff880000,0xffff0000,
//            0xff0000ff,0xff8800ff, 0xffff00ff,
//            0xff00ff00,0xff88ff00,0xffffff00,
//            0xff00ffff,0xff88ffff,0xffffffff,
//    };

//    static int[] colors = {0xff880000,0xffff8888,
//            0xff008800,0xff88ff88,
//            0xff000088, 0xff8888ff,
//            0xff888800,0xffffff88,
//            0xff880088,0xffff88ff,
//            0xff008888,0xff88ffff,
//    };

//    static int[] colors = {0xff880000,0xff008800,0x000088,
//            0xffff8800, 0xffff0088,
//            0xff88ff00,0xff00ff88,
//            0xff8800ff,0xff0088ff,
//            0xff88ffff,0xffff88ff,0xffffff88
//    };

//    static int[] colors = {0xffff3333,0xff33ff33,0xff3333ff,
//            0xff66ffff, 0xffff66ff,0xffffff66,
//            0xffff9999,0xff99ff99,0xff9999ff,
//            0xffccffff,0xffffccff,0xffffffcc
//    };

//    static int[] colors = {0xffff0000,0xff00ff00,0xff0000ff,
//            0xffff88, 0xffff0088,0xff88ff00,0xff00ff88,0xff0088ff,0xff8800ff,
//            0xffffff00,0xffff00ff,0xff00ffff
//    };
    float line15 = 1f / 9f;
//
//    }
    float line5 = 1f / 3f;


//    public void draw(DrawInfo drawInfo) {
//        float toplx = 0;
//        float toply = 0;
//        float toprx = 0;
//        float topry = 0;
//        float botlx = 0;
//        float botly = 0;
//        float botrx = 0;
//        float botry = 0;
//
//        if (drawInfo.myAction == DrawInfo.Action.LOOK_X) {
//            if (drawInfo.myZone == DrawInfo.Zone.MID) {
//                toplx = drawInfo.toX(getPosX(drawInfo.progress + .5f));
//                toply = drawInfo.toY(getPosY(drawInfo.progress + .5f));
//                toprx = drawInfo.toX(getPosX(drawInfo.progress - .5f));
//                topry = drawInfo.toY(getPosY(drawInfo.progress - .5f));
//                botlx = drawInfo.toX(getPosX(drawInfo.progress + .5f));
//                botly = drawInfo.toY(1 - getPosY(drawInfo.progress + .5f));
//                botrx = drawInfo.toX(getPosX(drawInfo.progress - .5f));
//                botry = drawInfo.toY(1 - getPosY(drawInfo.progress - .5f));
//            } else if (drawInfo.myZone == DrawInfo.Zone.TOP) {
//
//            }
//        }
//        Path path = new Path();
//        path.setFillType(Path.FillType.EVEN_ODD);
//        Paint paint = new Paint();
//
//        path.moveTo(toplx, toply);
////        paint.setColor(0xff2222ff);
////        drawInfo.canvas.drawCircle(toplx,toply,5,paint);
//        path.lineTo(toprx, topry);
////        paint.setColor(0xff22ff22);
////        drawInfo.canvas.drawCircle(toprx,topry,5,paint);
//        path.lineTo(botrx, botry);
////        paint.setColor(0xff22ffff);
////        drawInfo.canvas.drawCircle(botrx,botry,5,paint);
//        path.lineTo(botlx, botly);
////        paint.setColor(0xffff2222);
////        drawInfo.canvas.drawCircle(botlx,botly,5,paint);
//        path.lineTo(toplx, toply);
//        paint.setColor(getSideColor(id));
//
//
//        drawInfo.canvas.drawPath(path, paint);
//    }
    float line0 = 1f / 3.5f;
    private boolean solved;
    public Side(int id, Cube.Positions pos, int size, Cube owner) {
        data = new int[size][size];
        this.owner = owner;
        this.pos = pos;
        this.size = size;
        this.id = id;
        Random r = new Random();

        reset();
    }

    /**
     * returns the color
     */
    public static int getSideColor(int sideId) {

        //return (sideId*0xff/12)*0x010000 + (sideId*0xff/12)*0x0100 + (sideId*0xff/12)*0x01;
        return colors[sideId];
        //return 0xff000000 + (sideId * 0xffffff / 12);

    }

    public static float getXOnLine(MyPoint a, MyPoint b, float y) {
        return ((a.x - b.x) / (a.y - b.y)) * (y - b.y) + b.x;
    }

    public static float getYOnLine(MyPoint a, MyPoint b, float x) {
        return ((a.y - b.y) / (a.x - b.x)) * (x - b.x) + b.y;
    }

    public static int rotateY(int x, int y, int rotations, int size) {
        if ((rotations + 4) % 4 == 0) {
            return y;
        } else if ((rotations + 4) % 4 == 1) {
            return x;
        } else if ((rotations + 4) % 4 == 2) {
            return (size - 1) - y;
        } else {
            return (size - 1) - x;
        }
    }

    public static int rotateX(int x, int y, int rotations, int size) {
        if ((rotations + 4) % 4 == 0) {
            return x;
        } else if ((rotations + 4) % 4 == 1) {
            return (size - 1) - y;
        } else if ((rotations + 4) % 4 == 2) {
            return (size - 1) - x;
        } else {
            return y;
        }
    }

//    public void draw(DrawInfo points, int colorID,int alpha) {
////        Log.d("drawing-tl", points.topl.x+ "," + points.topl.y);
////        Log.d("drawing-tr", points.topr.x+ "," + points.topr.y);
////        Log.d("drawing-bl", points.botl.x+ "," + points.botl.y);
////        Log.d("drawing-br", points.botr.x+ "," + points.botr.y);
//
//        Path path = new Path();
//        path.setFillType(Path.FillType.EVEN_ODD);
//
//        Paint inside = new Paint();
//        inside.setAntiAlias(true);
//        //paint.setStrokeWidth(4);
//        inside.setColor(getSideColor(colorID));
//        inside.setAlpha(alpha);
//
//        Paint outside = new Paint();
//        outside.setAntiAlias(true);
//        //paint.setStrokeWidth(4);
//        outside.setColor(Color.BLACK);
//        outside.setAlpha(alpha);
//
//        path.moveTo((float)Math.floor(points.topl.x), (float)Math.floor(points.topl.y));
////        paint.setColor(0xff2222ff);
////        drawInfo.canvas.drawCircle(toplx,toply,5,paint);
//        path.lineTo((float)Math.ceil(points.topr.x), (float)Math.floor(points.topr.y));
////        paint.setColor(0xff22ff22);
////        drawInfo.canvas.drawCircle(toprx,topry,5,paint);
//        path.lineTo((float) Math.ceil(points.botr.x), (float) Math.ceil(points.botr.y));
////        paint.setColor(0xff22ffff);
////        drawInfo.canvas.drawCircle(botrx,botry,5,paint);
//        path.lineTo((float) Math.floor(points.botl.x), (float) Math.ceil(points.botl.y));
////        paint.setColor(0xffff2222);
////        drawInfo.canvas.drawCircle(botlx,botly,5,paint);
//        path.lineTo((float) Math.floor(points.topl.x), (float) Math.floor(points.topl.y));
//
//
//        points.canvas.drawPath(path, inside);
//
//        points.canvas.drawLine((float)Math.floor(points.topl.x), (float)Math.floor(points.topl.y),(float)Math.ceil(points.topr.x), (float)Math.floor(points.topr.y),outside);
//        points.canvas.drawLine((float)Math.ceil(points.topr.x), (float)Math.floor(points.topr.y),(float) Math.ceil(points.botr.x), (float) Math.ceil(points.botr.y),outside);
//        points.canvas.drawLine((float) Math.ceil(points.botr.x), (float) Math.ceil(points.botr.y),(float) Math.floor(points.botl.x), (float) Math.ceil(points.botl.y),outside);
//        points.canvas.drawLine((float) Math.floor(points.botl.x), (float) Math.ceil(points.botl.y),(float)Math.floor(points.topl.x), (float)Math.floor(points.topl.y),outside);
//    }

    public int get(int x, int y) {
        return data[x][y];
    }

    public float getPosX(float offset) {
        if (offset < -2.5) {
            float p = Math.abs(2 * (offset + 2.5f));
            return line30 * p + line25 * (1 - p);
        } else if (offset < -1.5) {
            float p = Math.abs(offset + 1.5f);
            return line25 * p + line15 * (1 - p);
        } else if (offset < -.5) {
            float p = Math.abs(offset + .5f);
            return line15 * p + line5 * (1 - p);
        } else if (offset < .5) {
            float p = Math.abs(offset - .5f);
            return line5 * p + (1 - line5) * (1 - p);
        } else if (offset < 1.5) {
            float p = Math.abs(offset - 1.5f);
            return (1 - line5) * p + (1 - line15) * (1 - p);
        } else if (offset < 2.5) {
            float p = Math.abs(offset - 2.5f);
            return (1 - line15) * p + (1 - line25) * (1 - p);
        } else {
            float p = Math.abs(2 * (offset - 2.5f));
            return (1 - line25) * p + (1 - line30) * (1 - p);
        }
    }

    public float getPosY(float offset) {
        if (offset < -2.5) {
            float p = Math.abs(2 * (offset + 2.5f));
            return line30 * p + line25 * (1 - p);
        } else if (offset < -1.5) {
            float p = Math.abs(offset + 1.5f);
            return line25 * p + line15 * (1 - p);
        } else if (offset < -.5) {
            float p = Math.abs(offset + .5f);
            return line15 * p + line5 * (1 - p);
        } else if (offset < .5) {
            float p = Math.abs(offset - .5f);
            return line5 * p + line5 * (1 - p);
        } else if (offset < 1.5) {
            float p = Math.abs(offset - 1.5f);
            return line5 * p + line15 * (1 - p);
        } else if (offset < 2.5) {
            float p = Math.abs(offset - 2.5f);
            return line15 * p + line25 * (1 - p);
        } else {
            float p = Math.abs(2 * (offset - 2.5f));
            return line25 * p + line30 * (1 - p);
        }
    }

    public void draw(DrawInfo points, int colorID, int alpha) {
        int color = getSideColor(colorID);
        color = Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        points.toDraw.quads.add(new Quads(points.topl, points.topr, points.botl, points.botr, color));
    }

    public void draw(DrawInfo points, int colorID) {
        draw(points, colorID, points.alpha);
    }

    public void onTouchCenter(MotionEvent event) {
        if (inThis(event)) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.i("touched center", id + "");
                owner.xStartAt = event.getX();
                owner.yStartAt = event.getY();
                owner.startCenter = true;
            }
        }
    }

    public boolean inThis(MotionEvent event) {
        // are we out the left side?
        if (event.getX() < getXOnLine(topl, botl, event.getY())) {
            return false;
        }
        // are we out the right side?
        if (getXOnLine(topr, botr, event.getY()) < event.getX()) {
            return false;
        }
        // are we out the top side?
        if (event.getY() < getYOnLine(topl, topr, event.getX())) {
            return false;
        }
        // are we out the bot side?
        if (getYOnLine(botl, botr, event.getX()) < event.getY()) {
            return false;
        }
        return true;
    }




    public void drawX(DrawInfo base, float offset, boolean[] scroll) {

        int i = (int) Math.floor(offset);
        int j = (int) Math.ceil(offset);



        Cube.Positions from = owner.getPosMoveX(pos, i);
        Cube.Positions to = owner.getPosMoveX(pos, j);

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int myX = x;
                if (owner.isSpinningX(y)) {
                    if (from == Cube.Positions.LEFT && (to == Cube.Positions.OUTSIDE)) {
                        myX = size - 1 - myX;
                    }
                    if (to == Cube.Positions.LEFT && (from == Cube.Positions.OUTSIDE)) {
                        myX = size - 1 - myX;
                    }
                }
                if (scroll[y]) {
                    if (pos == Cube.Positions.OUTSIDE) {
                        draw(owner.getAve(pos, offset, base, x, y), data[myX][size - 1 - y]);
                    } else {
                        draw(owner.getAve(pos, offset, base, x, y), data[myX][y]);
                    }
                } else {
                    draw(owner.getAve(pos, 0, base, x, y), data[myX][y]);
                }
            }
        }
    }

    public void drawY(DrawInfo base, float offset, boolean[] scroll) {

        int i = (int) Math.floor(offset);
        int j = (int) Math.ceil(offset);

        Cube.Positions from = owner.getPosMoveY(pos, i);
        Cube.Positions to = owner.getPosMoveY(pos, j);

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int myY = y;
                if (owner.isSpinningY(x)) {
                    if (from == Cube.Positions.TOP &&  to == Cube.Positions.OUTSIDE) {
                        myY = size - 1 - y;
                    }
                    if (to == Cube.Positions.TOP && from == Cube.Positions.OUTSIDE) {
                        myY = size - 1 - y;
                    }
                }
                if (scroll[x]) {
                    if (pos == Cube.Positions.OUTSIDE) {
                        draw(owner.getAve(pos, offset, base, x, y), data[size - 1 - x][myY]);
                    } else {

                        draw(owner.getAve(pos, offset, base, x, y), data[x][myY]);
                    }
                } else {
                    draw(owner.getAve(pos, 0, base, x, y), data[x][myY]);
                }
            }
        }
    }

    public void draw(DrawInfo base, float offset) {


        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {

                int cc = getColorCode(pos, offset, x, y);

                draw(owner.getAve(pos, offset, base, x, y), cc);
            }
        }
    }

    public void setBounds(DrawInfo base, float offset, float top, float left, float bot, float right) {
        DrawInfo bounds = owner.getAve(pos, offset, base);

        this.topl.x = left + (bounds.topl.x * (right - left));
        this.topl.y = top + (bounds.topl.y * (bot - top));
        this.topr.x = left + (bounds.topr.x * (right - left));
        this.topr.y = top + (bounds.topr.y * (bot - top));
        this.botl.x = left + (bounds.botl.x * (right - left));
        this.botl.y = top + (bounds.botl.y * (bot - top));
        this.botr.x = left + (bounds.botr.x * (right - left));
        this.botr.y = top + (bounds.botr.y * (bot - top));

        if (topl.y > botl.y) {
            Log.i("setBounds", "bad");
        }
        if (topr.y > botr.y) {
            Log.i("setBounds", "bad");
        }
    }

    /**
     * @param rotations number of of 90 degree clockwise rotations
     */
    public void rotate(int rotations) {
        int[][] next = new int[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                next[rotateX(x, y, rotations, size)][rotateY(x, y, rotations, size)] = data[x][y];
            }
        }

        data = next;
    }

    public int getColorCode(Cube.Positions current, float offset, int xIndex, int yIndex) {

        while (offset < 0) {
            offset += 6;
        }

        int i = (int) Math.floor(offset);
        int j = (int) Math.ceil(offset);


        Cube.Positions from;
        Cube.Positions to;
        int rotatedX = xIndex;
        int rotatedY = yIndex;
        int rotations;
        if (owner.movingX()) {
            from = owner.getPosMoveX(current, i);
            to = owner.getPosMoveX(current, j);
            rotations = owner.getRotationsX(current, from);
            rotatedX = Side.rotateX(xIndex, yIndex, rotations, size);
            rotatedY = Side.rotateY(xIndex, yIndex, rotations, size);
            if ((current == Cube.Positions.OUTSIDE)) {
                rotatedX = xIndex;
                rotatedY = size - 1 - yIndex;
            }

            if((from == Cube.Positions.LEFT && to == Cube.Positions.OUTSIDE)
            ||(to == Cube.Positions.LEFT && from == Cube.Positions.OUTSIDE)){
                rotatedX = size - 1 - rotatedX;
            }


        } else if (owner.movingY()) {
            from = owner.getPosMoveY(current, i);
            to = owner.getPosMoveY(current, j);
            rotations = owner.getRotationsY(current, (int)Math.floor(offset));
            rotatedX = Side.rotateX(xIndex, yIndex, rotations, size);
            rotatedY = Side.rotateY(xIndex, yIndex, rotations, size);
            if ((current == Cube.Positions.OUTSIDE)) {
                rotatedX = size - 1 - xIndex;
                rotatedY = yIndex;
            }

            if((from == Cube.Positions.TOP && to == Cube.Positions.OUTSIDE)
                    ||(to == Cube.Positions.TOP && from == Cube.Positions.OUTSIDE)){
                rotatedY = size - 1 - rotatedY;
            }


        }


        return data[rotatedX][rotatedY];
    }

    public boolean isSolved() {
        int last = data[0][0];
        for (int[] dat : data) {
            for (int da : dat) {
                if (last != da) {
                    return false;
                }
            }
        }
        return true;

    }

    public void reset() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i][j] = id;//r.nextInt(12);//
            }
        }
    }

    public void flipY() {
        int[][] oldData = new int[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                oldData[x][y] = data[x][y];
            }
        }
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                data[x][y] = oldData[x][size - 1 - y];
            }
        }

    }

    public void flipX() {
        int[][] oldData = new int[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                oldData[x][y] = data[x][y];
            }
        }
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                data[x][y] = oldData[size - 1 - x][y];
            }
        }

    }
}
