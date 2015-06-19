package cube.d.n.r42.r4.Challenges;


import cube.d.n.r42.r4.Cube;

/**
 * Created by Colin_000 on 4/15/2015.
 */
public class C_2_2 extends Challenge {
    @Override
    public String getSp_key() {
        return "r3_2_2";
    }

    @Override
    protected Cube privateInitChallange() {
        Cube result = new Cube(3);
        result.rotateY(1, 0);
        result.lookAtY(1);
        result.lookAtX(1);
        result.rotateX(1, 2);
        return result;
    }
}
