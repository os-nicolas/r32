package cube.d.n.r42.r4.Challenges;

import cube.d.n.r42.r4.Cube;

/**
 * Created by Colin_000 on 4/15/2015.
 */
public class C_2_12 extends Challenge {
    @Override
    public String getSp_key() {
        return "c_2_12";
    }

    @Override
    protected Cube privateInitChallange() {
        Cube result = new Cube(3);
        result.scramble();
        return result;
    }
}