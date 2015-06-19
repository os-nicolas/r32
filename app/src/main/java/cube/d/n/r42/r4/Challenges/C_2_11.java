package cube.d.n.r42.r4.Challenges;

import java.lang.Override;import java.lang.String;import cube.d.n.r42.r4.Cube;

/**
 * Created by Colin_000 on 4/15/2015.
 */
public class C_2_11 extends Challenge {
    @Override
    public String getSp_key() {
        return "r3_2_11";
    }

    @Override
    protected Cube privateInitChallange() {
        Cube result = new Cube(3);
        result.rotateX(1, 0);
        result.rotateY(1, 0);
        result.rotateX(1, 1);
        result.rotateY(1, 1);
        result.rotateX(1, 2);
        result.rotateY(1, 2);
        return result;
    }
}
