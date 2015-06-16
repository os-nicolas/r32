package cube.d.n.r42.r4.Challenges;

import cube.d.n.r42.r4.Cube;

/**
 * Created by Colin_000 on 4/15/2015.
 */
public class C_2_5 extends Challenge {
    @Override
    public String getSp_key() {
        return "c_2_5";
    }

    @Override
    protected Cube privateInitChallange() {
        Cube result = new Cube(3);
        result.rotateY(2, 2);
        result.lookAtX(1);
        result.rotateX(-2, 1);
        result.lookAtX(-1);
        result.rotateY(-2, 2);
        return result;
    }
}
