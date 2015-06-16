package cube.d.n.r42.r4.Challenges;

import java.lang.Override;import java.lang.String;

import cube.d.n.r42.r4.Cube;

/**
 * Created by Colin_000 on 4/15/2015.
 */
public class C_1_11 extends Challenge {

    @Override
    public String getSp_key() {
        return "c_1_11";
    }

    @Override
    protected Cube privateInitChallange() {
        Cube result = new Cube(2);
        result.rotateX(-2, 0);
        result.lookAtY(4);
        result.lookAtX(1);
        result.rotateX(-2, 1);
        result.lookAtY(1);
        result.rotateY(2, 0);
        result.lookAtX(1);
        result.rotateX(-2, 1);
        result.lookAtY(1);
        result.rotateY(2, 0);
        result.lookAtX(1);
        result.rotateY(2, 0);
        return result;
    }
}