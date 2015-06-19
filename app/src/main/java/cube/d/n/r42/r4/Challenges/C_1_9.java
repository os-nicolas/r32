package cube.d.n.r42.r4.Challenges;

import cube.d.n.r42.r4.Cube;

/**
 * Created by Colin_000 on 4/15/2015.
 */
public class C_1_9 extends Challenge {

    @Override
    public String getSp_key() {
        return "r3_1_9";
    }

    @Override
    protected Cube privateInitChallange() {
        Cube result = new Cube(2);
        result.rotateY(1, 0);
        result.rotateX(1, 0);
        result.rotateY(1, 0);
        result.rotateX(1, 0);
        result.rotateY(1, 0);
        return result;
    }
}