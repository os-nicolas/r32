package cube.d.n.r42.r4;

import android.util.Log;
import android.view.MotionEvent;

import java.lang.Float;import java.lang.Math;import java.lang.System;import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import cube.d.n.r42.r4.Challenges.Challenge;

/**
 * Created by Colin_000 on 4/3/2015.
 */
public class Cube {

    public boolean[] lastScrollX;
    public boolean[] lastScrollY;
    public boolean[] scrollX;
    public boolean[] scrollY;
    public float xStartAt;
    public float yStartAt;
    public float height;
    public float width;
    public boolean startCenter = false;
    public Challenge myChallenge = null;
    Side[] sides = new Side[12];
    int size;
    float currentAlpha = 0;
    boolean spinning = false;
    ChallengeActivity myChallengeAct;
    private float offsetY;
    private float offsetX;
    //TODO scale by dpi
    private double moveDis = 20;
    private boolean touchIsDead = false;
    public boolean readyToMove;

    public Cube(int size) {
        this.size = size;

        scrollX = new boolean[size];
        scrollY = new boolean[size];

        lastScrollX = new boolean[size];
        lastScrollY = new boolean[size];

        sides[0] = new Side(0, Positions.CENTER, size, this);
        sides[1] = new Side(1, Positions.CENTER_LEFT_T, size, this);
        sides[2] = new Side(2, Positions.LEFT_T, size, this);
        sides[3] = new Side(3, Positions.CENTER_RIGHT_B, size, this);
        sides[4] = new Side(4, Positions.RIGHT_B, size, this);
        sides[5] = new Side(5, Positions.CENTER_TOP_L, size, this);
        sides[6] = new Side(6, Positions.TOP_L, size, this);
        sides[7] = new Side(7, Positions.CENTER_BOT_R, size, this);
        sides[8] = new Side(8, Positions.BOT_R, size, this);
        sides[9] = new Side(9, Positions.TOP_LEFT, size, this);
        sides[10] = new Side(10, Positions.BOT_RIGHT, size, this);
        sides[11] = new Side(11, Positions.OUTSIDE, size, this);

    }

    // what a bad name
    private static MyPoint avePoints(MyPoint a, MyPoint b, float p) {

        return new MyPoint(a.x * (1 - p) + b.x * p, a.y * (1 - p) + b.y * p);
    }

    public static boolean contains(Positions[] list, Positions lookingFor) {
        for (Positions p : list) {
            if (p == lookingFor) {
                return true;
            }
        }
        return false;
    }

    public void spin() {
        spinning = true;
        moveX(true);
    }

    public synchronized void draw(DrawInfo base) {


        if (spinning) {
            offsetX = ((System.currentTimeMillis() % 24000) / 4000f) % 6;
        }


        //first we draw the backgroud
        if (moveX() || moveY() || slideX() || slideY() || movingX() || movingY()) {

            currentAlpha = (9 * (currentAlpha) + 0x00) / 10f;
            drawBkg(base);
        } else {
            currentAlpha = (9 * (currentAlpha) + 0xff) / 10f;
            drawBkg(base);
        }

        if (moveX()) {
            //Log.i("moveX","1");
            draw(base, offsetX);
        } else if (moveY()) {
            //Log.i("moveY","1");
            draw(base, offsetY);
        } else if (slideX()) {
            //Log.i("slideX","1");
            slideX(base, offsetX);
        } else if (slideY()) {
            //Log.i("slideY","1");
            slideY(base, offsetY);
        } else if (movingX()) {
            offsetX *= 9f / 10f;
            if (move(lastScrollX)) {
                //Log.i("movingX - draw","1");
                draw(base, offsetX);
            } else {
                //Log.i("movingX - slide","1");
                slideX(base, offsetX, lastScrollX);
            }
            if (Math.abs(offsetX) < .02) {
                offsetX = 0;
            }
        } else if (movingY()) {
            offsetY *= 9f / 10f;
            if (move(lastScrollY)) {
                //Log.i("movingY - draw","1");
                draw(base, offsetY);
            } else {
                //Log.i("movingY - slide","1");
                slideY(base, offsetY, lastScrollY);
            }
            if (Math.abs(offsetY) < .02) {
                offsetY = 0;
            }
        } else {
            //Log.i("stadnd","1");
            draw(base, 0);
        }


        //outside.draw(getPoints(CENTER,CENTER));
    }

    public void setBounds(float left, float top, float right, float bot, DrawInfo base) {
        width = right - left;
        height = bot - top;
        for (Side s : sides) {
//            if (moveX()) {
//                draw(base, offsetX);
//            } else if (moveY()) {
//                draw(base, offsetY);
//            } else if (slideX()) {
//                slideX(base, offsetX);
//            } else if (slideY()) {
//                slideY(base, offsetY);
//            } else if (movingX()) {
//                offsetX *= 4f / 5f;
//                if (move(lastScrollX)) {
//                    draw(base, offsetX);
//                }else {
//                    slideX(base, offsetX,lastScrollX);
//                }
//                if (offsetX <.1){
//                    offsetX=0;
//                }
//            } else if (movingY()) {
//                if (move(lastScrollY)) {
//                    draw(base, offsetY);
//                }else {
//                    slideY(base, offsetY, lastScrollY);
//                }
//            } else {
            s.setBounds(base, 0, top, left, bot, right);
//            }

        }
    }

    private void drawBkg(DrawInfo base) {
        // we are going to draw some trangles
        Side outside = get(Positions.OUTSIDE);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                float flr = 0;//(int) Math.floor(size/2f);//
                float cel = size;//(int) Math.ceil(size/2f);//

                if (x >= 0) {
                    //draw on left
                    DrawInfo mine = new DrawInfo(base);
                    mine.topl = new MyPoint((float) ((x - flr) / cel * (1f / 27f)), 1f / 3f + y / (3f * size));
                    mine.topr = new MyPoint((float) ((x + 1 - flr) / cel * (1f / 27f)), 1f / 3f + y / (3f * size));
                    mine.botl = new MyPoint((float) ((x - flr) / cel * (1f / 27f)), 1f / 3f + (y + 1f) / (3f * size));
                    mine.botr = new MyPoint((float) ((x + 1 - flr) / cel * (1f / 27f)), 1f / 3f + (y + 1f) / (3f * size));
                    if (y == 0) {
                        mine.topl.y = 1f / 27f;
                        mine.topr.y = 1f / 27f;
                    }
                    if (y == size - 1) {
                        mine.botl.y = 26f / 27f;
                        mine.botr.y = 26f / 27f;
                    }
                    //mine.upScalePoints();

                    mine.alpha = (int) currentAlpha;

                    outside.draw(mine, outside.data[x][size - 1 - y]);
                }
                if (x <= size) {
                    //draw on right
                    DrawInfo mine = new DrawInfo(base);
                    mine.topl = new MyPoint(26f / 27f + (float) ((x) / cel * (1f / 27f)), 1f / 3f + y / (3f * size));
                    mine.topr = new MyPoint(26f / 27f + (float) ((x + 1) / cel * (1f / 27f)), 1f / 3f + y / (3f * size));
                    mine.botl = new MyPoint(26f / 27f + (float) ((x) / cel * (1f / 27f)), 1f / 3f + (y + 1f) / (3f * size));
                    mine.botr = new MyPoint(26f / 27f + (float) ((x + 1) / cel * (1f / 27f)), 1f / 3f + (y + 1f) / (3f * size));
                    if (y == 0) {
                        mine.topl.y = 1f / 27f;
                        mine.topr.y = 1f / 27f;
                    }
                    if (y == size - 1) {
                        mine.botl.y = 26f / 27f;
                        mine.botr.y = 26f / 27f;
                    }
                    //mine.upScalePoints();

                    mine.alpha = (int) currentAlpha;

                    outside.draw(mine, outside.data[x][size - 1 - y]);

                }
                if (y >= 0) {
                    //draw top
                    DrawInfo mine = new DrawInfo(base);
                    mine.topl = new MyPoint(1f / 3f + x / (3f * size), (float) ((y - flr) / cel * (1f / 27f)));
                    mine.topr = new MyPoint(1f / 3f + (x + 1f) / (3f * size), (float) ((y - flr) / cel * (1f / 27f)));
                    mine.botl = new MyPoint(1f / 3f + x / (3f * size), (float) ((y + 1 - flr) / cel * (1f / 27f)));
                    mine.botr = new MyPoint(1f / 3f + (x + 1f) / (3f * size), (float) ((y + 1 - flr) / cel * (1f / 27f)));
                    if (x == 0) {
                        mine.topl.x = 1f / 27f;
                        mine.botl.x = 1f / 27f;
                    }
                    if (x == size - 1) {
                        mine.topr.x = 26f / 27f;
                        mine.botr.x = 26f / 27f;
                    }
                    //mine.upScalePoints();

                    mine.alpha = (int) currentAlpha;

                    outside.draw(mine, outside.data[size - 1 - x][y]);
                }
                if (y <= size) {
                    //draw on bot
                    DrawInfo mine = new DrawInfo(base);
                    mine.topl = new MyPoint(1f / 3f + x / (3f * size), 26f / 27f + (float) ((y) / cel * (1f / 27f)));
                    mine.topr = new MyPoint(1f / 3f + (x + 1f) / (3f * size), 26f / 27f + (float) ((y) / cel * (1f / 27f)));
                    mine.botl = new MyPoint(1f / 3f + x / (3f * size), 26f / 27f + (float) ((y + 1) / cel * (1f / 27f)));
                    mine.botr = new MyPoint(1f / 3f + (x + 1f) / (3f * size), 26f / 27f + (float) ((y + 1) / cel * (1f / 27f)));
                    if (x == 0) {
                        mine.topl.x = 1f / 27f;
                        mine.botl.x = 1f / 27f;
                    }
                    if (x == size - 1) {
                        mine.topr.x = 26f / 27f;
                        mine.botr.x = 26f / 27f;
                    }
                    //mine.upScalePoints();

                    mine.alpha = (int) currentAlpha;

                    outside.draw(mine, outside.data[size - 1 - x][y]);
                }
            }
        }
    }

    public void draw(DrawInfo base, float offset) {
        for (Side s : sides) {
            s.draw(base, offset);
        }
//        sides[0].draw(base, offset);
    }

    private void slideX(DrawInfo base, float offset) {
        slideX(base, offset, scrollX);
    }

    private void slideX(DrawInfo base, float offset, boolean[] scroll) {
        for (Side s : sides) {
            if (inX(s.pos)
                    ) {
                s.drawX(base, offset, scroll);
            } else if (s.pos == Positions.TOP_L ||
                    s.pos == Positions.TOP_R ||
                    s.pos == Positions.CENTER_TOP_L ||
                    s.pos == Positions.CENTER_TOP_R ||
                    s.pos == Positions.TOP_LEFT ||
                    s.pos == Positions.TOP_RIGHT) {
                if (scroll[0]) {
                    s.draw(base, offset);
                } else {
                    s.draw(base, 0f);
                }
            } else if (s.pos == Positions.BOT_L ||
                    s.pos == Positions.BOT_R ||
                    s.pos == Positions.CENTER_BOT_L ||
                    s.pos == Positions.CENTER_BOT_R ||
                    s.pos == Positions.BOT_LEFT ||
                    s.pos == Positions.BOT_RIGHT) {
                if (scroll[size - 1]) {
                    s.draw(base, offset);
                } else {
                    s.draw(base, 0f);
                }
            }
        }
    }

    private void slideY(DrawInfo base, float offset) {
        slideY(base, offset, scrollY);
    }

    private void slideY(DrawInfo base, float offset, boolean[] scroll) {
        for (Side s : sides) {
            if (inY(s.pos)
                    ) {
                s.drawY(base, offset, scroll);
            } else if ((inLeft(s.pos) && scroll[0]) || (
                    (inRight(s.pos) && scroll[size - 1])
            )) {
                s.draw(base, offset);
            } else {
                s.draw(base, 0f);
            }

        }
    }

//    private DrawInfo flip(DrawInfo old) {
//        DrawInfo result = new DrawInfo(old);
//        result.topl= new MyPoint(old.botr);
//        result.topr= new MyPoint(old.botl);
//        result.botl= new MyPoint(old.topr);
//        result.botr= new MyPoint(old.topl);
//        return result;
//    }
//
//    private DrawInfo flipX(DrawInfo old) {
//        DrawInfo result = new DrawInfo(old);
//        result.topl= new MyPoint(old.topr);
//        result.topr= new MyPoint(old.topl);
//        result.botl= new MyPoint(old.botr);
//        result.botr= new MyPoint(old.botl);
//        return result;
//
//    }
//
//    private DrawInfo getSectionPointsInside(int xIndex, int yIndex, Positions pos, DrawInfo base) {
//        DrawInfo startWith = getPointsOutside(pos, base);
//        return getSectionPoints(xIndex,yIndex,startWith,pos);
//    }
//
//    private DrawInfo getSectionPointsOutside(int xIndex, int yIndex,  DrawInfo base) {
//        DrawInfo result = new DrawInfo(base);
//        if (xIndex==0 && yIndex==0){
//            result.topl = new MyPoint(1f/27f-2f/(27f *size),1f/27f-2f/(27f *size));
//            result.topr = new MyPoint((1f/3f) +1f/((float)size*3f),1f/27f);
//            result.botl = new MyPoint(1f/27f,(1f/3f) +1f/((float)size*3f));
//            result.botr = new MyPoint(1f/27f,1f/27f);
//        }else if (xIndex==size-1 && yIndex==0){
//            result.topl = new MyPoint((2f/3f) -1f/((float)size*3f),1f/27f);
//            result.topr = new MyPoint(26f/27f+1f/(27f *size/2f),1f/27f-1f/(27f *size/2f));
//            result.botl = new MyPoint(26f/27f,1f/27f);
//            result.botr = new MyPoint(26f/27f,(1f/3f) +1f/((float)size*3f));
//        }else if (xIndex==0 && yIndex==size-1){
//            result.topl = new MyPoint(1f/27f,2f/3f - 1f/((float)size*3f));
//            result.topr = new MyPoint(1f/27f,26f/27f);
//            result.botl = new MyPoint(1f/27f-1f/(27f *size/2f),26f/27f+1f/(27f *size/2f));
//            result.botr = new MyPoint((1f/3f) +1f/((float)size*3f),26f/27f);
//        }else if (xIndex==size-1 && yIndex==size-1){
//            result.topl = new MyPoint(26f/27f,26f/27f);
//            result.topr = new MyPoint(26f/27f,(2f/3f) -1f/((float)size*3f));
//            result.botl = new MyPoint((2f/3f) -1f/((float)size*3f),26f/27f);
//            result.botr = new MyPoint(26f/27f+1f/(27f *size/2f),26f/27f+1f/(27f *size/2f));
//        }
//
//        //result.upScalePoints();
//
//        return result;
//    }

    private boolean slideY() {
        for (int i = 0; i < scrollY.length; i++) {
            if (scrollY[i]) {
                return true;
            }
        }
        return false;
    }

    private boolean slideX() {
        for (int i = 0; i < scrollX.length; i++) {
            if (scrollX[i]) {
                return true;
            }
        }
        return false;
    }

    public DrawInfo getAve(Positions current, float offset, DrawInfo base, int xIndex, int yIndex) {
        int i = (int) Math.floor(offset);
        int j = (int) Math.ceil(offset);
        float p = 1 - (offset - i);

        boolean hasTopLeft = topLeft();

        Positions from;
        Positions to;
        if (movingX()) {
            from = getPosMoveX(current, i, hasTopLeft);
            to = getPosMoveX(current, j, hasTopLeft);
        } else if (movingY()) {
            from = getPosMoveY(current, i, hasTopLeft);
            to = getPosMoveY(current, j, hasTopLeft);

        } else {
            from = getPosMoveY(current, i, hasTopLeft);
            to = getPosMoveY(current, j, hasTopLeft);
        }

        //int rotations = getRotations(current,from);

        //int rotatedX = Side.rotateX(xIndex,yIndex,rotations,size);
        //int rotatedY = Side.rotateY(xIndex, yIndex, rotations, size);

        if (clockwise(from, to)) {
            return rCAve(
                    getSectionPoints(xIndex, yIndex, from, base),
                    getSectionPoints(yIndex, size - 1 - xIndex, to, base), p);

        } else if (counterClockwise(from, to)) {
            return rCCAve(
                    getSectionPoints(xIndex, yIndex, from, base),
                    getSectionPoints(size - 1 - yIndex, xIndex, to, base), p);

        } else if (from == Positions.OUTSIDE && to == Positions.OUTSIDE) {
            return getSectionPoints(xIndex, yIndex, Positions.OUTSIDE, base);
        } else if (from == Positions.OUTSIDE && to != Positions.OUTSIDE) {
            if (inX(to)) {
                return flipXAve(
                        getSectionPoints(xIndex, yIndex, from, base),
                        getSectionPoints(size - 1 - xIndex, yIndex, to, base), p);
            } else {
                return flipYAve(
                        getSectionPoints(xIndex, yIndex, from, base),
                        getSectionPoints(xIndex, size - 1 - yIndex, to, base), p);
            }
        } else if (from != Positions.OUTSIDE && to == Positions.OUTSIDE) {
            if (inX(from)) {
                return flipXAve(
                        getSectionPoints(xIndex, yIndex, from, base),
                        getSectionPoints(size - 1 - xIndex, yIndex, to, base), p);
            } else {
                return flipYAve(
                        getSectionPoints(xIndex, yIndex, from, base),
                        getSectionPoints(xIndex, size - 1 - yIndex, to, base), p);
            }
        } else {

            return ave(
                    getSectionPoints(xIndex, yIndex, from, base),
                    getSectionPoints(xIndex, yIndex, to, base), p);
        }
    }

    private boolean clockwise(Positions from, Positions to) {
        return (from == Positions.CENTER_TOP_R && to == Positions.CENTER_TOP_L) ||
                (from == Positions.TOP_RIGHT && to == Positions.TOP_L) ||
                (from == Positions.TOP_L && to == Positions.TOP_R) ||
                (from == Positions.TOP_R && to == Positions.TOP_LEFT) ||

                (from == Positions.CENTER_LEFT_T && to == Positions.CENTER_LEFT_B) ||
                (from == Positions.TOP_LEFT && to == Positions.LEFT_B) ||
                (from == Positions.LEFT_B && to == Positions.LEFT_T) ||
                (from == Positions.LEFT_T && to == Positions.BOT_LEFT) ||

                (from == Positions.CENTER_BOT_L && to == Positions.CENTER_BOT_R) ||
                (from == Positions.BOT_LEFT && to == Positions.BOT_R) ||
                (from == Positions.BOT_R && to == Positions.BOT_L) ||
                (from == Positions.BOT_L && to == Positions.BOT_RIGHT) ||

                (from == Positions.CENTER_RIGHT_B && to == Positions.CENTER_RIGHT_T) ||
                (from == Positions.BOT_RIGHT && to == Positions.RIGHT_T) ||
                (from == Positions.RIGHT_T && to == Positions.RIGHT_B) ||
                (from == Positions.RIGHT_B && to == Positions.TOP_RIGHT);
    }

    //TODO this is a bit depricated
    // as in a lot of it's paths will never be called
    // and share a lot of code wtih the other getAve
    public DrawInfo getAve(Positions center, float offset, DrawInfo base) {
        int i = (int) Math.floor(offset);
        int j = (int) Math.ceil(offset);
        float p = 1 - (offset - i);

        boolean hasTopLeft = topLeft();

        Positions from;
        Positions to;
        if (movingX()) {
            from = getPosMoveX(center, i, hasTopLeft);
            to = getPosMoveX(center, j, hasTopLeft);
        } else if (movingY()) {
            from = getPosMoveY(center, i, hasTopLeft);
            to = getPosMoveY(center, j, hasTopLeft);

        } else {
            from = getPosMoveY(center, i, hasTopLeft);
            to = getPosMoveY(center, j, hasTopLeft);
        }

        if (clockwise(from, to)
                ) {
            return rCAve(
                    getPoints(from, base),
                    getPoints(to, base), p);

        } else if (counterClockwise(from, to)
                ) {
            return rCCAve(
                    getPoints(from, base),
                    getPoints(to, base), p);

        } else if (from == Positions.OUTSIDE && to == Positions.OUTSIDE) {
            return getPoints(Positions.OUTSIDE, base);
        } else if (from == Positions.OUTSIDE && to != Positions.OUTSIDE) {
            if (inX(to)) {
                return flipXAve(
                        getPoints(from, base),
                        getPoints(to, base), p);
            } else {
                return flipYAve(
                        getPoints(from, base),
                        getPoints(to, base), p);
            }
        } else if (from != Positions.OUTSIDE && to == Positions.OUTSIDE) {
            if (inX(from)) {
                return flipXAve(
                        getPoints(from, base),
                        getPoints(to, base), p);
            } else {
                return flipYAve(
                        getPoints(from, base),
                        getPoints(to, base), p);
            }
        } else {
            return ave(
                    getPoints(from, base),
                    getPoints(to, base), p);
        }
    }

    private boolean counterClockwise(Positions from, Positions to) {
        return (from == Positions.CENTER_TOP_L) && to == Positions.CENTER_TOP_R ||
                (from == Positions.TOP_L && to == Positions.TOP_RIGHT) ||
                (from == Positions.TOP_R && to == Positions.TOP_L) ||
                (from == Positions.TOP_LEFT && to == Positions.TOP_R) ||

                (from == Positions.CENTER_LEFT_B && to == Positions.CENTER_LEFT_T) ||
                (from == Positions.LEFT_B && to == Positions.TOP_LEFT) ||
                (from == Positions.LEFT_T && to == Positions.LEFT_B) ||
                (from == Positions.BOT_LEFT && to == Positions.LEFT_T) ||

                (from == Positions.CENTER_BOT_R && to == Positions.CENTER_BOT_L) ||
                (from == Positions.BOT_R && to == Positions.BOT_LEFT) ||
                (from == Positions.BOT_L && to == Positions.BOT_R) ||
                (from == Positions.BOT_RIGHT && to == Positions.BOT_L) ||

                (from == Positions.CENTER_RIGHT_T && to == Positions.CENTER_RIGHT_B) ||
                (from == Positions.RIGHT_T && to == Positions.BOT_RIGHT) ||
                (from == Positions.RIGHT_B && to == Positions.RIGHT_T) ||
                (from == Positions.TOP_RIGHT && to == Positions.RIGHT_B);
    }

    public boolean movingX() {
        return offsetX != 0;
    }

    public boolean movingY() {
        return offsetY != 0;
    }

    private DrawInfo ave(DrawInfo a, DrawInfo b, float offset) {
        //TODOpoints
        DrawInfo result = new DrawInfo(a);
        result.topl = MyPoint.ave(a.topl, b.topl, offset);
        result.topr = MyPoint.ave(a.topr, b.topr, offset);
        result.botl = MyPoint.ave(a.botl, b.botl, offset);
        result.botr = MyPoint.ave(a.botr, b.botr, offset);
        result.alpha = aveAlpha(a, b, offset);

        return result;
    }

    private DrawInfo rCAve(DrawInfo a, DrawInfo b, float offset) {

        //TODOpoints
        DrawInfo result = new DrawInfo(a);
        result.topl = MyPoint.ave(a.topl, b.botl, offset);
        result.topr = MyPoint.ave(a.topr, b.topl, offset);
        result.botl = MyPoint.ave(a.botl, b.botr, offset);
        result.botr = MyPoint.ave(a.botr, b.topr, offset);
        result.alpha = aveAlpha(a, b, offset);

        return result;
    }

    private DrawInfo rCCAve(DrawInfo a, DrawInfo b, float offset) {
        DrawInfo result = new DrawInfo(a);
        result.topl = MyPoint.ave(a.topl, b.topr, offset);
        result.topr = MyPoint.ave(a.topr, b.botr, offset);
        result.botl = MyPoint.ave(a.botl, b.topl, offset);
        result.botr = MyPoint.ave(a.botr, b.botl, offset);
        result.alpha = aveAlpha(a, b, offset);

        return result;
    }

    private int aveAlpha(DrawInfo a, DrawInfo b, float o) {
        // we lean in to alpha zero
        // so by .5
        float c = 8;
        if (a.alpha == 0) {
            o = (o < 1f / c ? c * o : 1);
        } else if (b.alpha == 0) {
            o = (o < (c - 1f) / c ? 0 : (c * o) - (c - 1f));
        }
        return (int) (a.alpha * o + b.alpha * (1 - o));
    }

    private DrawInfo flipXAve(DrawInfo a, DrawInfo b, float offset) {
        DrawInfo result = new DrawInfo(a);
        result.topl = MyPoint.ave(a.topl, b.topr, offset);
        result.topr = MyPoint.ave(a.topr, b.topl, offset);
        result.botl = MyPoint.ave(a.botl, b.botr, offset);
        result.botr = MyPoint.ave(a.botr, b.botl, offset);
        result.alpha = aveAlpha(a, b, offset);

        return result;
    }

    private DrawInfo flipYAve(DrawInfo a, DrawInfo b, float offset) {
        DrawInfo result = new DrawInfo(a);
        result.topl = MyPoint.ave(a.topl, b.botl, offset);
        result.topr = MyPoint.ave(a.topr, b.botr, offset);
        result.botl = MyPoint.ave(a.botl, b.topl, offset);
        result.botr = MyPoint.ave(a.botr, b.topr, offset);
        result.alpha = aveAlpha(a, b, offset);

        return result;
    }

    public Positions getPosMoveX(Positions current, int offset, boolean hasTopLeft) {

//        while (offset <0){
//            offset += 6;
//        }

        ArrayList<Positions[]> lists = new ArrayList<Positions[]>();
        Positions[] centerList1 = {Positions.LEFT_T, Positions.CENTER_LEFT_B, Positions.CENTER, Positions.CENTER_RIGHT_T, Positions.RIGHT_B, Positions.OUTSIDE};
        lists.add(centerList1);
        Positions[] centerList2 = {Positions.LEFT_B, Positions.CENTER_LEFT_T, Positions.CENTER, Positions.CENTER_RIGHT_B, Positions.RIGHT_T, Positions.OUTSIDE};
        lists.add(centerList2);
        Positions[] topList = {Positions.TOP_R, Positions.TOP_LEFT, Positions.CENTER_TOP_R, Positions.CENTER_TOP_L, Positions.TOP_RIGHT, Positions.TOP_L};
        lists.add(topList);
        Positions[] botList = {Positions.BOT_R, Positions.BOT_LEFT, Positions.CENTER_BOT_R, Positions.CENTER_BOT_L, Positions.BOT_RIGHT, Positions.BOT_L};
        lists.add(botList);


        if (current == Positions.CENTER || current == Positions.OUTSIDE) {
            if (hasTopLeft == (current == Positions.CENTER)) {
                for (int i = 0; i < centerList1.length; i++) {
                    if (current == centerList1[i]) {
                        //we found it
                        //Log.i("this",offset+"");
                        return centerList1[(i + offset + centerList1.length * 3) % centerList1.length];
                    }
                }
            } else {
                for (int i = 0; i < centerList2.length; i++) {
                    if (current == centerList2[i]) {
                        //we found it
                        return centerList2[(i + offset + centerList2.length * 3) % centerList2.length];
                    }
                }
            }
        }

        for (Positions[] list : lists) {
            for (int i = 0; i < list.length; i++) {
                if (current == list[i]) {
                    //we found it
                    return list[(i + offset + list.length * 3) % list.length];
                }
            }
        }

        Log.e("getPosMoveX", "should return something!");

        return null;
    }

    public boolean topLeft() {
        for (int i = 0; i < sides.length; i++) {
            if (sides[i].pos == Positions.TOP_LEFT) {
                return true;
            }
        }
        return false;
    }

    public Positions getPosMoveY(Positions current, int offset, boolean hasTopLeft) {

//        while (offset <0){
//            offset += 6;
//        }

        ArrayList<Positions[]> lists = new ArrayList<Positions[]>();
        Positions[] centerList2 = {Positions.TOP_R, Positions.CENTER_TOP_L, Positions.CENTER, Positions.CENTER_BOT_R, Positions.BOT_L, Positions.OUTSIDE};
        lists.add(centerList2);
        Positions[] centerList1 = {Positions.TOP_L, Positions.CENTER_TOP_R, Positions.CENTER, Positions.CENTER_BOT_L, Positions.BOT_R, Positions.OUTSIDE};
        lists.add(centerList1);
        Positions[] leftList = {Positions.LEFT_B, Positions.TOP_LEFT, Positions.CENTER_LEFT_B, Positions.CENTER_LEFT_T, Positions.BOT_LEFT, Positions.LEFT_T};
        lists.add(leftList);
        Positions[] rightList = {Positions.RIGHT_B, Positions.TOP_RIGHT, Positions.CENTER_RIGHT_B, Positions.CENTER_RIGHT_T, Positions.BOT_RIGHT, Positions.RIGHT_T};
        lists.add(rightList);

        if (current == Positions.CENTER || current == Positions.OUTSIDE) {
            if (hasTopLeft == (current == Positions.CENTER)) {
                for (int i = 0; i < centerList1.length; i++) {
                    if (current == centerList1[i]) {
                        //we found it
                        return centerList1[(i + offset + centerList1.length * 3) % centerList1.length];
                    }
                }
            } else {
                for (int i = 0; i < centerList2.length; i++) {
                    if (current == centerList2[i]) {
                        //we found it
                        return centerList2[(i + offset + centerList2.length * 3) % centerList2.length];
                    }
                }
            }
        }

        for (Positions[] list : lists) {
            for (int i = 0; i < list.length; i++) {
                if (current == list[i]) {
                    //we found it
                    return list[(i + offset + list.length * 3) % list.length];
                }
            }
        }

        Log.e("getPosMoveX", "should return something!");

        return null;
    }

    private DrawInfo getSectionPoints(int xIndex, int yIndex, DrawInfo startWidth, Positions pos) {
        DrawInfo result = new DrawInfo(startWidth);
        result.alpha = startWidth.alpha;
        MyPoint leftPoint = avePoints(startWidth.topl, startWidth.botl, yIndex / ((float) size));
        MyPoint rightPoint = avePoints(startWidth.topr, startWidth.botr, yIndex / ((float) size));
        MyPoint topPoint = avePoints(startWidth.topl, startWidth.topr, xIndex / ((float) size));
        MyPoint botPoint = avePoints(startWidth.botl, startWidth.botr, xIndex / ((float) size));
        result.topl = intersect(leftPoint, rightPoint, topPoint, botPoint);
        leftPoint = avePoints(startWidth.topl, startWidth.botl, yIndex / ((float) size));
        rightPoint = avePoints(startWidth.topr, startWidth.botr, yIndex / ((float) size));
        topPoint = avePoints(startWidth.topl, startWidth.topr, (xIndex + 1) / ((float) size));
        botPoint = avePoints(startWidth.botl, startWidth.botr, (xIndex + 1) / ((float) size));
        result.topr = intersect(leftPoint, rightPoint, topPoint, botPoint);
        leftPoint = avePoints(startWidth.topl, startWidth.botl, (yIndex + 1) / ((float) size));
        rightPoint = avePoints(startWidth.topr, startWidth.botr, (yIndex + 1) / ((float) size));
        topPoint = avePoints(startWidth.topl, startWidth.topr, (xIndex) / ((float) size));
        botPoint = avePoints(startWidth.botl, startWidth.botr, (xIndex) / ((float) size));
        result.botl = intersect(leftPoint, rightPoint, topPoint, botPoint);
        leftPoint = avePoints(startWidth.topl, startWidth.botl, (yIndex + 1) / ((float) size));
        rightPoint = avePoints(startWidth.topr, startWidth.botr, (yIndex + 1) / ((float) size));
        topPoint = avePoints(startWidth.topl, startWidth.topr, (xIndex + 1) / ((float) size));
        botPoint = avePoints(startWidth.botl, startWidth.botr, (xIndex + 1) / ((float) size));
        result.botr = intersect(leftPoint, rightPoint, topPoint, botPoint);
        if (inX(pos)) {
            // if it is in the X row we over write some things
            if (yIndex != 0) {
                // adjust the top
                result.topl.y = 1f / 3f + (yIndex) / (3f * size);//result.toY();
                result.topr.y = 1f / 3f + (yIndex) / (3f * size);//result.toY();
            }
            if (yIndex + 1 != size) {
                //adjust the top
                result.botl.y = 1f / 3f + (yIndex + 1) / (3f * size);//result.toY();
                result.botr.y = 1f / 3f + (yIndex + 1) / (3f * size);//result.toY();
            }

        } else if (inY(pos)) {
            // likewise for the Y row
            if (xIndex != 0) {
                // adjust the left
                result.topl.x = 1f / 3f + (xIndex) / (3f * size);//result.toX();
                result.botl.x = 1f / 3f + (xIndex) / (3f * size);//result.toX();
            }
            if (xIndex + 1 != size) {
                //adjust the right
                result.topr.x = 1f / 3f + (xIndex + 1) / (3f * size);//result.toX();
                result.botr.x = 1f / 3f + (xIndex + 1) / (3f * size);//result.toX();
            }
        }
        return result;
    }

    private DrawInfo getSectionPoints(int xIndex, int yIndex, Positions pos, DrawInfo base) {
        DrawInfo startWidth = getPoints(pos, base);
        return getSectionPoints(xIndex, yIndex, startWidth, pos);
    }

    private MyPoint intersect(MyPoint leftPoint, MyPoint rightPoint, MyPoint topPoint, MyPoint botPoint) {
//        ((leftPoint.x - rightPoint.x)/(leftPoint.y - rightPoint.y))*(y - rightPoint.y) + rightPoint.x=x
//        ((topPoint.y - botPoint.y)/(topPoint.x -botPoint.x))*(x - botPoint.x) + botPoint.y =y;
//
//        ((leftPoint.x - rightPoint.x)/(leftPoint.y - rightPoint.y))*(((topPoint.y - botPoint.y)/(topPoint.x -botPoint.x))*(x - botPoint.x) + botPoint.y - rightPoint.y) + rightPoint.x=x


        float x;
        float y;
        if ((leftPoint.y - rightPoint.y) != 0 && (topPoint.x - botPoint.x) != 0) {
            float dxdy = (leftPoint.x - rightPoint.x) / (leftPoint.y - rightPoint.y);
            float dydx = (topPoint.y - botPoint.y) / (topPoint.x - botPoint.x);
            if (1 - dxdy * dydx != 0) {
                x = (-dxdy * dydx * botPoint.x + dxdy * botPoint.y - dxdy * rightPoint.y + rightPoint.x) / (1 - dxdy * dydx);
                y = (-dydx * dxdy * rightPoint.y + dydx * rightPoint.x - dydx * botPoint.x + botPoint.y) / (1 - dydx * dxdy);
            } else {
                Log.e("intersect", "these lines are parallel");
                x = 0;
                y = 0;
            }
        } else if (leftPoint.y - rightPoint.y == 0 && (topPoint.x - botPoint.x) != 0) {
            y = leftPoint.y;
            x = Side.getXOnLine(topPoint, botPoint, y);
        } else if ((topPoint.x - botPoint.x) == 0 && (leftPoint.y - rightPoint.y) != 0) {
            x = topPoint.x;
            y = Side.getYOnLine(leftPoint, rightPoint, x);
        } else {
            // they are all the same point?
            //Log.e("intersect","bad bad");
            y = leftPoint.y;
            x = topPoint.x;
        }
//        (dxdy*(dydx*(x - botPoint.x) + botPoint.y - rightPoint.y) + rightPoint.x=x
//        (dxdy*(dydx*x - dydx*botPoint.x + botPoint.y - rightPoint.y) + rightPoint.x=x
//        dxdy*dydx*x - dxdy*dydx*botPoint.x + dxdy*botPoint.y - dxdy*rightPoint.y + rightPoint.x=x
//                 - dxdy*dydx*botPoint.x + dxdy*botPoint.y - dxdy*rightPoint.y + rightPoint.x=x-dxdy*dydx*x
//                - dxdy*dydx*botPoint.x + dxdy*botPoint.y - dxdy*rightPoint.y + rightPoint.x=(1-dxdy*dydx)*x

//        ((leftPoint.x - rightPoint.x)/(leftPoint.y - rightPoint.y))*(y - rightPoint.y) + rightPoint.x=x
//        ((topPoint.y - botPoint.y)/(topPoint.x -botPoint.x))*(x - botPoint.x) + botPoint.y =y;
//        ((topPoint.y - botPoint.y)/(topPoint.x -botPoint.x))*(((leftPoint.x - rightPoint.x)/(leftPoint.y - rightPoint.y))*(y - rightPoint.y) + rightPoint.x - botPoint.x) + botPoint.y =y;
//        (dydx)*(dxdy*(y - rightPoint.y) + rightPoint.x - botPoint.x) + botPoint.y =y;
//        (dydx)*(dxdy*y - dxdy*rightPoint.y + rightPoint.x - botPoint.x) + botPoint.y =y;
//        dydx*dxdy*y - dydx*dxdy*rightPoint.y + dydx*rightPoint.x - dydx*botPoint.x + botPoint.y =y;
//         - dydx*dxdy*rightPoint.y + dydx*rightPoint.x - dydx*botPoint.x + botPoint.y =y-dydx*dxdy*y;
//        - dydx*dxdy*rightPoint.y + dydx*rightPoint.x - dydx*botPoint.x + botPoint.y =(1-dydx*dxdy)*y;
//        (- dydx*dxdy*rightPoint.y + dydx*rightPoint.x - dydx*botPoint.x + botPoint.y)/(1-dydx*dxdy) =y;
        //float y = (- dydx*dxdy*rightPoint.y + dydx*rightPoint.x - dydx*botPoint.x + botPoint.y)/(1-dydx*dxdy);
        if (Float.isNaN(x) || Float.isNaN(y)) {
            Log.wtf("super bad", "NaN");
        }

        return new MyPoint(x, y);
    }

    private boolean inX(Positions pos) {
        return pos == Positions.CENTER ||
                pos == Positions.LEFT_T ||
                pos == Positions.LEFT_B ||
                pos == Positions.CENTER_LEFT_T ||
                pos == Positions.CENTER_LEFT_B ||
                pos == Positions.RIGHT_T ||
                pos == Positions.RIGHT_B ||
                pos == Positions.CENTER_RIGHT_T ||
                pos == Positions.CENTER_RIGHT_B ||
                pos == Positions.OUTSIDE;
    }

    private boolean inY(Positions pos) {
        return pos == Positions.CENTER ||
                pos == Positions.BOT_R ||
                pos == Positions.BOT_L ||
                pos == Positions.CENTER_BOT_L ||
                pos == Positions.CENTER_BOT_R ||
                pos == Positions.TOP_R ||
                pos == Positions.TOP_L ||
                pos == Positions.CENTER_TOP_L ||
                pos == Positions.CENTER_TOP_R ||
                pos == Positions.OUTSIDE;
    }

    private DrawInfo getPoints(Positions pos, DrawInfo base) {
        DrawInfo result = new DrawInfo(base);

        if (pos == Positions.CENTER) {
            result.topl = new MyPoint(1f / 3f, 1f / 3f);
            result.topr = new MyPoint(2f / 3f, 1f / 3f);
            result.botl = new MyPoint(1f / 3f, 2f / 3f);
            result.botr = new MyPoint(2f / 3f, 2f / 3f);
        } else if (pos == Positions.CENTER_LEFT_T) {
            result.topl = new MyPoint(1f / 9f, 1f / 3f);
            result.topr = new MyPoint(1f / 3f, 1f / 3f);
            result.botl = new MyPoint(1f / 9f, 8f / 9f);
            result.botr = new MyPoint(1f / 3f, 2f / 3f);
        } else if (pos == Positions.CENTER_LEFT_B) {
            result.topl = new MyPoint(1f / 9f, 1f / 9f);
            result.topr = new MyPoint(1f / 3f, 1f / 3f);
            result.botl = new MyPoint(1f / 9f, 2f / 3f);
            result.botr = new MyPoint(1f / 3f, 2f / 3f);
        } else if (pos == Positions.LEFT_T) {
            result.topl = new MyPoint(1f / 27f, 1f / 27f);
            result.topr = new MyPoint(1f / 9f, 1f / 3f);
            result.botl = new MyPoint(1f / 27f, 26f / 27f);
            result.botr = new MyPoint(1f / 9f, 8f / 9f);
        } else if (pos == Positions.LEFT_B) {
            result.topl = new MyPoint(1f / 27f, 1f / 27f);
            result.topr = new MyPoint(1f / 9f, 1f / 9f);
            result.botl = new MyPoint(1f / 27f, 26f / 27f);
            result.botr = new MyPoint(1f / 9f, 2f / 3f);
        } else if (pos == Positions.CENTER_RIGHT_T) {
            result.topl = new MyPoint(2f / 3f, 1f / 3f);
            result.topr = new MyPoint(8f / 9f, 1f / 3f);
            result.botl = new MyPoint(2f / 3f, 2f / 3f);
            result.botr = new MyPoint(8f / 9f, 8f / 9f);
        } else if (pos == Positions.CENTER_RIGHT_B) {
            result.topl = new MyPoint(2f / 3f, 1f / 3f);
            result.topr = new MyPoint(8f / 9f, 1f / 9f);
            result.botl = new MyPoint(2f / 3f, 2f / 3f);
            result.botr = new MyPoint(8f / 9f, 2f / 3f);
        } else if (pos == Positions.RIGHT_T) {
            result.topl = new MyPoint(8f / 9f, 1f / 3f);
            result.topr = new MyPoint(26f / 27f, 1f / 27f);
            result.botl = new MyPoint(8f / 9f, 8f / 9f);
            result.botr = new MyPoint(26f / 27f, 26f / 27f);
        } else if (pos == Positions.RIGHT_B) {
            result.topl = new MyPoint(8f / 9f, 1f / 9f);
            result.topr = new MyPoint(26f / 27f, 1f / 27f);
            result.botl = new MyPoint(8f / 9f, 2f / 3f);
            result.botr = new MyPoint(26f / 27f, 26f / 27f);
        } else if (pos == Positions.TOP_L) {
            result.topl = new MyPoint(1f / 27f, 1f / 27f);
            result.topr = new MyPoint(26f / 27f, 1f / 27f);
            result.botl = new MyPoint(1f / 3f, 1f / 9f);
            result.botr = new MyPoint(8f / 9f, 1f / 9f);
        } else if (pos == Positions.TOP_R) {
            result.topl = new MyPoint(1f / 27f, 1f / 27f);
            result.topr = new MyPoint(26f / 27f, 1f / 27f);
            result.botl = new MyPoint(1f / 9f, 1f / 9f);
            result.botr = new MyPoint(2f / 3f, 1f / 9f);
        } else if (pos == Positions.CENTER_TOP_L) {
            result.topl = new MyPoint(1f / 3f, 1f / 9f);
            result.topr = new MyPoint(8f / 9f, 1f / 9f);
            result.botl = new MyPoint(1f / 3f, 1f / 3f);
            result.botr = new MyPoint(2f / 3f, 1f / 3f);
        } else if (pos == Positions.CENTER_TOP_R) {
            result.topl = new MyPoint(1f / 9f, 1f / 9f);
            result.topr = new MyPoint(2f / 3f, 1f / 9f);
            result.botl = new MyPoint(1f / 3f, 1f / 3f);
            result.botr = new MyPoint(2f / 3f, 1f / 3f);
        } else if (pos == Positions.CENTER_BOT_L) {
            result.topl = new MyPoint(1f / 3f, 2f / 3f);
            result.topr = new MyPoint(2f / 3f, 2f / 3f);
            result.botl = new MyPoint(1f / 3f, 8f / 9f);
            result.botr = new MyPoint(8f / 9f, 8f / 9f);
        } else if (pos == Positions.CENTER_BOT_R) {
            result.topl = new MyPoint(1f / 3f, 2f / 3f);
            result.topr = new MyPoint(2f / 3f, 2f / 3f);
            result.botl = new MyPoint(1f / 9f, 8f / 9f);
            result.botr = new MyPoint(2f / 3f, 8f / 9f);
        } else if (pos == Positions.BOT_L) {
            result.topl = new MyPoint(1f / 3f, 8f / 9f);
            result.topr = new MyPoint(8f / 9f, 8f / 9f);
            result.botl = new MyPoint(1f / 27f, 26f / 27f);
            result.botr = new MyPoint(26f / 27f, 26f / 27f);
        } else if (pos == Positions.BOT_R) {
            result.topl = new MyPoint(1f / 9f, 8f / 9f);
            result.topr = new MyPoint(2f / 3f, 8f / 9f);
            result.botl = new MyPoint(1f / 27f, 26f / 27f);
            result.botr = new MyPoint(26f / 27f, 26f / 27f);
        } else if (pos == Positions.TOP_LEFT) {
            result.topl = new MyPoint(1f / 27f, 1f / 27f);
            result.topr = new MyPoint(1f / 3f, 1f / 9f);
            result.botl = new MyPoint(1f / 9f, 1f / 3f);
            result.botr = new MyPoint(1f / 3f, 1f / 3f);
        } else if (pos == Positions.TOP_RIGHT) {
            result.topl = new MyPoint(2f / 3f, 1f / 9f);
            result.topr = new MyPoint(26f / 27f, 1f / 27f);
            result.botl = new MyPoint(2f / 3f, 1f / 3f);
            result.botr = new MyPoint(8f / 9f, 1f / 3f);
        } else if (pos == Positions.BOT_LEFT) {
            result.topl = new MyPoint(1f / 9f, 2f / 3f);
            result.topr = new MyPoint(1f / 3f, 2f / 3f);
            result.botl = new MyPoint(1f / 27f, 26f / 27f);
            result.botr = new MyPoint(1f / 3f, 8f / 9f);
        } else if (pos == Positions.BOT_RIGHT) {
            result.topl = new MyPoint(2f / 3f, 2f / 3f);
            result.topr = new MyPoint(8f / 9f, 2f / 3f);
            result.botl = new MyPoint(2f / 3f, 8f / 9f);
            result.botr = new MyPoint(26f / 27f, 26f / 27f);

        } else if (pos == Positions.OUTSIDE) {
            result.topl = new MyPoint(1f / 27f, 1f / 27f);
            result.topr = new MyPoint(26f / 27f, 1f / 27f);
            result.botl = new MyPoint(1f / 27f, 26f / 27f);
            result.botr = new MyPoint(26f / 27f, 26f / 27f);
            result.alpha = 0x00;
        }

        if (pos == Positions.BOT_R ||
                pos == Positions.CENTER_BOT_R) {
            result.botl.x += .005;
        }
        if (pos == Positions.CENTER_TOP_R ||
                pos == Positions.TOP_R) {
            result.topl.x += .005;
        }

        if (pos == Positions.BOT_L ||
                pos == Positions.CENTER_BOT_L) {
            result.botr.x -= .005;
        }
        if (pos == Positions.CENTER_TOP_L ||
                pos == Positions.TOP_L) {
            result.topr.y -= .005;
        }

        if (pos == Positions.LEFT_T ||
                pos == Positions.CENTER_LEFT_T) {
            result.botl.y -= .005;
        }
        if (pos == Positions.RIGHT_T ||
                pos == Positions.CENTER_RIGHT_T) {
            result.botr.y -= .005;
        }

        if (pos == Positions.LEFT_B ||
                pos == Positions.CENTER_LEFT_B) {
            result.topl.y += .005;
        }
        if (pos == Positions.RIGHT_B ||
                pos == Positions.CENTER_RIGHT_B) {
            result.topr.y += .005;
        }

        if (pos == Positions.TOP_LEFT) {
            result.topl.x += .005;
            result.topl.y += .005;
        } else if (pos == Positions.TOP_RIGHT) {
            result.topr.x -= .005;
            result.topr.y += .005;
        } else if (pos == Positions.BOT_LEFT) {
            result.botl.x += .005;
            result.botl.y -= .005;
        } else if (pos == Positions.BOT_RIGHT) {
            result.botr.x -= .005;
            result.botr.y -= .005;
        }

        result.topl.x += .005;
        result.topr.x -= .005;
        result.botl.x += .005;
        result.botr.x -= .005;
        result.topl.y += .005;
        result.topr.y += .005;
        result.botl.y -= .005;
        result.botr.y -= .005;
        return result;
    }

    public void onTouch(MotionEvent event) {
        if (event.getPointerCount() == 1 && (!touchIsDead || event.getAction() == MotionEvent.ACTION_DOWN)) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touchIsDead = false;
                Log.i("onTouch", "x: " + event.getX() + " y: " + event.getY());

                    if (get(Positions.CENTER).inThis(event)) {
                        get(Positions.CENTER).onTouchCenter(event);
                    } else {
                        readyToMove = true;
                        xStartAt = event.getX();

                        yStartAt = event.getY();
                    }
                for (int i = 0; i < size; i++) {
                    lastScrollX[i] = false;
                }
                for (int i = 0; i < size; i++) {
                    lastScrollY[i] = false;
                }
                offsetX = 0;
                offsetY = 0;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                float dx = Math.abs(xStartAt - event.getX());
                float dy = Math.abs(yStartAt - event.getY());
                if (startCenter) {
                    if (dx > moveDis && dx > dy) {
                        // we need to figure out what row they are rotating
                        Log.i("scroll X", " ");
                        int row = (int) Math.floor((yStartAt - (height / 3f)) / (height / (size * 3f)));
                        scrollX[row] = true;
                        startCenter = false;
                    } else if (dy > moveDis && dy > dx) {
                        Log.i("scroll Y", " ");
                        int row = (int) Math.floor((xStartAt - (width / 3f)) / (width / (size * 3f)));
                        scrollY[row] = true;
                        startCenter = false;
                    }
                }else if (readyToMove){
                    if (dx > moveDis && dx > dy) {
                        // we need to figure out what row they are rotating
                        Log.i("rotate X", " ");
                        moveX(true);
                        readyToMove = false;
                    } else if (dy > moveDis && dy > dx) {
                        Log.i("rotate Y", " ");
                        moveY(true);
                        readyToMove = false;
                    }
                }

                if (moveX()) {
                    //calculate offset
                    offsetX = stickyOffset(((event.getX() - xStartAt) / width) * 6);
                } else if (moveY()) {
                    //calculate offset
                    offsetY = stickyOffset(((event.getY() - yStartAt) / height) * 6);
                } else if (slideX()) {
                    //calculate offset
                    offsetX = stickyOffset2(((event.getX() - xStartAt) / width) * 6);
                } else if (slideY()) {
                    //calculate offset
                    offsetY = stickyOffset2(((event.getY() - yStartAt) / height) * 6);
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                actionUp(event);
            }
        } else {
            touchIsDead = true;
            actionUp(event);
        }
    }

    private void actionUp(MotionEvent event) {
        for (int i = 0; i < size; i++) {
            lastScrollX[i] = scrollX[i];
            lastScrollY[i] = scrollY[i];
        }
        if (moveX()) {
            //calculate offset
            moveX(false);

            offsetX = stickyOffset(((event.getX() - xStartAt) / width) * 6);

            lookAtX(offsetX);


            offsetX = (float) (offsetX - Math.round(offsetX));
        } else if (moveY()) {
            //calculate offset
            moveY(false);

            offsetY = stickyOffset(((event.getY() - yStartAt) / height) * 6);

            lookAtY(offsetY);

            offsetY = (float) (offsetY - Math.round(offsetY));
        } else if (startCenter) {
            startCenter = false;
        } else if (slideX()) {


            offsetX = stickyOffset2(((event.getX() - xStartAt) / width) * 6);
            int targetOffset = Math.round(offsetX / 2f) * 2;
            int myScrollX = getScrollX();

            rotateX(targetOffset, myScrollX);

            offsetX = (float) (offsetX - targetOffset);
            moveX(false);

            Log.i("offset at release", " " + offsetX);
        } else if (slideY()) {


            offsetY = stickyOffset2(((event.getY() - yStartAt) / height) * 6);
            int targetOffset = Math.round(offsetY / 2f) * 2;
            int myScrollY = getScrollY();

            rotateY(targetOffset, myScrollY);

            offsetY = (float) (offsetY - targetOffset);
            moveY(false);

            Log.i("offset at release", " " + offsetY);
        }

        checkSolved();
    }

    private void checkSolved() {
        if (myChallenge != null && myChallenge.isPassed(this)) {
            myChallenge.solved();
        }
    }

    public void lookAtY(Positions pos) {
        //TODO if i need this
        if (pos == Positions.BOT_R || pos == Positions.BOT_L) {
            lookAtY(-2);
        }
    }

    public void lookAtY(float myOffset) {
        boolean hasTopLeft = topLeft();
        for (Side s : sides) {
            Positions startedAt = s.pos;
            Positions currentAt = getPosMoveY(s.pos, Math.round(myOffset), hasTopLeft);
            // handle rotations
            int rotations = getRotationsY(startedAt, currentAt);
            s.rotate(-rotations);

            if ((currentAt == Positions.OUTSIDE) != (startedAt == Positions.OUTSIDE)) {
                s.flipX();
            }

            s.pos = currentAt;
        }
    }

    public boolean isSolved() {
        for (Side s : sides) {
            if (!s.isSolved()) {
                return false;
            }
        }
        return true;

    }

    public void lookAtX(float myOffset) {
        boolean hasTopLeft = topLeft();
        for (Side s : sides) {
            Positions startedAt = s.pos;
            Positions currentAt = getPosMoveX(s.pos, Math.round(myOffset), hasTopLeft);

            // handle rotations
            int rotations = getRotationsX(startedAt, currentAt);
            s.rotate(-rotations);

            if ((currentAt == Positions.OUTSIDE) != (startedAt == Positions.OUTSIDE)) {
                s.flipY();
            }

            s.pos = currentAt;
        }
    }

    public void rotateX(int targetOffset, int myScrollX) {
        boolean hasTopLeft = topLeft();
        HashMap<Side, int[]> newData = new HashMap<Side, int[]>();

        for (Side s : sides) {
            if (inX(s.pos)) {
                int[] myNewData = new int[size];
                Positions currentAt = getPosMoveX(s.pos, -targetOffset, hasTopLeft);
                Side takeFrom = get(currentAt);
                for (int x = 0; x < size; x++) {
                    if (currentAt == Positions.OUTSIDE) {
                        myNewData[x] = takeFrom.data[x][size - 1 - myScrollX];
                    } else {
                        myNewData[x] = takeFrom.data[x][myScrollX];
                    }

                }
                newData.put(s, myNewData);
            } else if ((inTop(s.pos) && myScrollX == 0) ||
                    (inBot(s.pos) && myScrollX == size - 1)) {
                Positions startedAt = s.pos;
                Positions currentAt = getPosMoveX(s.pos, targetOffset, hasTopLeft);
                int rotations = getRotationsX(startedAt, currentAt);
                s.rotate(-rotations);
                s.pos = currentAt;
            }
        }
        for (Side s : sides) {
            if (inX(s.pos)) {
                int[] myNewData = newData.get(s);
                for (int x = 0; x < size; x++) {
                    if (s.pos == Positions.OUTSIDE) {
                        s.data[x][size - 1 - myScrollX] = myNewData[x];
                    } else {
                        s.data[x][myScrollX] = myNewData[x];
                    }
                }
            }
        }
    }

    public void rotateY(int targetOffset, int myScrollY) {
        HashMap<Side, int[]> newData = new HashMap<Side, int[]>();
        boolean hasTopLeft = topLeft();

        for (Side s : sides) {
            if (inY(s.pos)) {
                int[] myNewData = new int[size];
                Positions currentAt = getPosMoveY(s.pos, -targetOffset, hasTopLeft);
                Side takeFrom = get(currentAt);
                for (int y = 0; y < size; y++) {
                    if (currentAt == Positions.OUTSIDE) {
                        myNewData[y] = takeFrom.data[size - 1 - myScrollY][y];
                    } else {
                        myNewData[y] = takeFrom.data[myScrollY][y];
                    }
                }
                newData.put(s, myNewData);
            } else if ((inLeft(s.pos) && myScrollY == 0)
                    || (inRight(s.pos) && myScrollY == size - 1)) {
                Positions startedAt = s.pos;
                Positions currentAt = getPosMoveY(s.pos, targetOffset, hasTopLeft);
                int rotations = getRotationsY(startedAt, currentAt);
                s.rotate(-rotations);
                s.pos = currentAt;
            }
        }
        for (Side s : sides) {
            if (inY(s.pos)) {
                int[] myNewData = newData.get(s);
                for (int y = 0; y < size; y++) {
                    if (s.pos == Positions.OUTSIDE) {
                        s.data[size - 1 - myScrollY][y] = myNewData[y];
                    } else {
                        s.data[myScrollY][y] = myNewData[y];
                    }
                }
            }
        }
    }

    private boolean inRight(Positions pos) {
        return pos == Positions.RIGHT_T ||
                pos == Positions.RIGHT_B ||
                pos == Positions.CENTER_RIGHT_T ||
                pos == Positions.CENTER_RIGHT_B ||
                pos == Positions.BOT_RIGHT ||
                pos == Positions.TOP_RIGHT;
    }

    private boolean inLeft(Positions pos) {
        return pos == Positions.LEFT_T ||
                pos == Positions.LEFT_B ||
                pos == Positions.CENTER_LEFT_T ||
                pos == Positions.CENTER_LEFT_B ||
                pos == Positions.BOT_LEFT ||
                pos == Positions.TOP_LEFT;
    }

    private boolean inBot(Positions pos) {
        return pos == Positions.BOT_L ||
                pos == Positions.BOT_R ||
                pos == Positions.CENTER_BOT_L ||
                pos == Positions.CENTER_BOT_R ||
                pos == Positions.BOT_LEFT ||
                pos == Positions.BOT_RIGHT;
    }

    private boolean inTop(Positions pos) {
        return pos == Positions.TOP_L ||
                pos == Positions.TOP_R ||
                pos == Positions.CENTER_TOP_L ||
                pos == Positions.CENTER_TOP_R ||
                pos == Positions.TOP_LEFT ||
                pos == Positions.TOP_RIGHT;
    }

    private int getScrollX() {
        for (int i = 0; i < size; i++) {
            if (scrollX[i]) {
                return i;
            }
        }
        Log.i("getScrollX", "bad bad");
        return -1;
    }

    private int getScrollY() {
        for (int i = 0; i < size; i++) {
            if (scrollY[i]) {
                return i;
            }
        }
        Log.i("getScrollY", "bad bad");
        return -1;
    }

    private Side get(Positions currentAt) {
        for (Side s : sides) {
            if (s.pos == currentAt) {
                return s;
            }
        }
        return null;
    }

    public int getRotationsX(Positions startedAt, Positions endedAt) {
        ArrayList<Positions[]> lists = new ArrayList<Positions[]>();
        Positions[] topList = {Positions.TOP_R, Positions.TOP_LEFT, Positions.CENTER_TOP_R, Positions.CENTER_TOP_L, Positions.TOP_RIGHT, Positions.TOP_L};
        lists.add(topList);
        Positions[] botList = {Positions.BOT_R, Positions.BOT_LEFT, Positions.CENTER_BOT_R, Positions.CENTER_BOT_L, Positions.BOT_RIGHT, Positions.BOT_L};
        lists.add(botList);

        return getRotations(lists, startedAt, endedAt);
    }

    public int getRotationsY(Positions startedAt, Positions endedAt) {
        ArrayList<Positions[]> lists = new ArrayList<Positions[]>();

        Positions[] leftList = {Positions.LEFT_B, Positions.TOP_LEFT, Positions.CENTER_LEFT_B, Positions.CENTER_LEFT_T, Positions.BOT_LEFT, Positions.LEFT_T};
        lists.add(leftList);
        Positions[] rightList = {Positions.RIGHT_B, Positions.TOP_RIGHT, Positions.CENTER_RIGHT_B, Positions.CENTER_RIGHT_T, Positions.BOT_RIGHT, Positions.RIGHT_T};
        lists.add(rightList);

        return getRotations(lists, startedAt, endedAt);
    }

    public int getRotations(ArrayList<Positions[]> lists, Positions startedAt, Positions endedAt) {

        if (startedAt == endedAt) {
            return 0;
        }

        Positions[] array = null;

        for (Positions[] list : lists) {
            if (contains(list, startedAt) && contains(list, endedAt)) {
                array = list;
                break;
            }
        }

        if (array == null) {
            return 0;
        }

        Positions current = startedAt;
        int result = 0;
        while (current != endedAt) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == current) {
                    Positions next = array[(i + 1) % array.length];
                    if (clockwise(current, next)) {
                        result++;
                    }
                    if (counterClockwise(current, next)) {
                        result--;
                    }
                    current = next;
                    if (current == endedAt) {
                        break;
                    }
                }
            }
        }
        return result;

    }

    private boolean moveX() {
        return move(scrollX);
    }

    private boolean moveY() {
        return move(scrollY);
    }

    private boolean move(boolean[] myScroll) {
        for (int i = 0; i < myScroll.length; i++) {
            if (!myScroll[i]) {
                return false;
            }
        }
        return true;
    }


    public void moveX(boolean b) {
        Log.i("moveX", "" + b);
        for (int i = 0; i < scrollX.length; i++) {
            scrollX[i] = b;
        }
        if (b) {
            offsetY = 0;
            offsetX = 0;
        }
    }

    public void moveY(boolean b) {
        Log.i("moveY", "" + b);
        for (int i = 0; i < scrollY.length; i++) {
            scrollY[i] = b;
        }
        if (b) {
            offsetY = 0;
            offsetX = 0;
        }
    }

    public synchronized void scramble() {
        //TODO
        Random r = new Random();
        for (int i = 0; i < 30; i++) {
            lookAtX(r.nextInt(6));
            lookAtY(r.nextInt(6));
            if (r.nextBoolean()) {
                rotateX((r.nextInt(3) + 1) * 2, r.nextInt(size));
            } else {
                rotateY((r.nextInt(3) + 1) * 2, r.nextInt(size));
            }
        }


    }

    public float stickyOffset(float myOffset) {
        float flr = (float) Math.floor(myOffset);
        float rem = myOffset - flr;
        return (float) (flr + (Math.sin((rem - .5) * Math.PI) / Math.abs(Math.sin((rem - .5) * Math.PI))) * Math.pow(Math.abs(Math.sin((rem - .5) * Math.PI)), .6) / 2 + .5);
    }

    public float stickyOffset2(float myOffset) {
        float flr = (float) Math.floor(myOffset / 2f) * 2f;
        float rem = myOffset - flr;
        return (float) (flr + (Math.sin((rem - 1) * Math.PI / 2) / Math.abs(Math.sin((rem - 1) * Math.PI / 2))) * Math.pow(Math.abs(Math.sin((rem - 1) * Math.PI / 2)), .8) + 1);
    }

    public void reset() {
        for (Side s : sides) {
            s.reset();
        }
    }

    public boolean isSpinningX(int y) {
        return scrollX[y];
    }

    public boolean isSpinningY(int x) {
        return scrollY[x];
    }

    private float wrapProgress(float progress) {
        return ((progress + 6f) % 6f) - 3f;
    }


    public enum Positions {
        CENTER,
        CENTER_LEFT_T, CENTER_LEFT_B,
        LEFT_T, LEFT_B,
        CENTER_RIGHT_T, CENTER_RIGHT_B,
        RIGHT_T, RIGHT_B,
        TOP_L, TOP_R,
        CENTER_TOP_L, CENTER_TOP_R,
        CENTER_BOT_L, CENTER_BOT_R,
        BOT_L, BOT_R,
        TOP_LEFT,
        TOP_RIGHT,
        BOT_LEFT,
        BOT_RIGHT,
        OUTSIDE
    }

}