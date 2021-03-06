package cube.d.n.r42.r4.Challenges;


import cube.d.n.r42.r4.Cube;

/**
 * Created by Colin_000 on 4/15/2015.
 */
public class C_1_4 extends Challenge {

    @Override
    public String getSp_key() {
        return "r3_1_4";
    }

    @Override
    protected Cube privateInitChallange() {
        Cube result = new Cube(2);
        result.rotateY(1, 0);
        result.lookAtX(1);
        result.lookAtY(1);
        result.rotateX(2, 0);
        result.lookAtY(1);
        return result;
    }

}