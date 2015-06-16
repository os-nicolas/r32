package cube.d.n.r42.r4.Challenges;


import cube.d.n.r42.r4.Cube;

/**
 * Created by Colin_000 on 4/15/2015.
 */
public class C_1_2 extends Challenge {

    @Override
    public String getSp_key() {
        return "c_1_2";
    }

    @Override
    protected Cube privateInitChallange() {
        Cube result = new Cube(2);
        result.rotateY(2, 0);
        result.lookAtX(1);
        result.lookAtY(1);
        return result;
    }

}