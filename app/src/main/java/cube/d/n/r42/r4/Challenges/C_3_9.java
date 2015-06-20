package cube.d.n.r42.r4.Challenges;

import cube.d.n.r42.r4.Cube;
import cube.d.n.r42.r4.Side;

/**
 * Created by Colin_000 on 6/19/2015.
 */
public class C_3_9 extends Challenge {
    @Override
    public String getSp_key() {
        return "r3_3_9";
    }

    @Override
    protected Cube privateInitChallange() {
        Cube result = new Cube(3);
        result.grey();
        result.get(Cube.Positions.LEFT).data[1][1] = Side.sideToColor(Cube.Positions.LEFT);
        result.get(Cube.Positions.LEFT).data[2][0] = Side.sideToColor(Cube.Positions.LEFT);
        result.get(Cube.Positions.LEFT).data[2][1] = Side.sideToColor(Cube.Positions.LEFT);
        result.get(Cube.Positions.LEFT).data[2][2] = Side.sideToColor(Cube.Positions.LEFT);
        result.get(Cube.Positions.TOP).data[1][1] = Side.sideToColor(Cube.Positions.TOP);
        result.get(Cube.Positions.TOP).data[0][2] = Side.sideToColor(Cube.Positions.TOP);
        result.get(Cube.Positions.TOP).data[1][2] = Side.sideToColor(Cube.Positions.TOP);
        result.get(Cube.Positions.TOP).data[2][2] = Side.sideToColor(Cube.Positions.TOP);
        result.get(Cube.Positions.TOP).data[2][1] = Side.sideToColor(Cube.Positions.TOP);
        result.get(Cube.Positions.RIGHT).data[0][0] = Side.sideToColor(Cube.Positions.RIGHT);
        result.get(Cube.Positions.RIGHT).data[0][1] = Side.sideToColor(Cube.Positions.RIGHT);
        result.get(Cube.Positions.RIGHT).data[0][2] = Side.sideToColor(Cube.Positions.RIGHT);
        result.get(Cube.Positions.RIGHT).data[1][0] = Side.sideToColor(Cube.Positions.RIGHT);
        result.get(Cube.Positions.RIGHT).data[1][1] = Side.sideToColor(Cube.Positions.RIGHT);
        result.get(Cube.Positions.RIGHT).data[1][2] = Side.sideToColor(Cube.Positions.RIGHT);
        result.get(Cube.Positions.BOT).data[1][1] = Side.sideToColor(Cube.Positions.BOT);
        result.get(Cube.Positions.BOT).data[2][1] = Side.sideToColor(Cube.Positions.BOT);
        result.get(Cube.Positions.BOT).data[0][0] = Side.sideToColor(Cube.Positions.BOT);
        result.get(Cube.Positions.BOT).data[1][0] = Side.sideToColor(Cube.Positions.BOT);
        result.get(Cube.Positions.BOT).data[2][0] = Side.sideToColor(Cube.Positions.BOT);

        result.get(Cube.Positions.CENTER).data[0][0] = Side.sideToColor(Cube.Positions.CENTER);
        result.get(Cube.Positions.CENTER).data[0][1] = Side.sideToColor(Cube.Positions.CENTER);
        result.get(Cube.Positions.CENTER).data[0][2] = Side.sideToColor(Cube.Positions.CENTER);
        result.get(Cube.Positions.CENTER).data[1][0] = Side.sideToColor(Cube.Positions.CENTER);
        result.get(Cube.Positions.CENTER).data[1][1] = Side.sideToColor(Cube.Positions.CENTER);
        result.get(Cube.Positions.CENTER).data[1][2] = Side.sideToColor(Cube.Positions.CENTER);
        result.get(Cube.Positions.CENTER).data[2][0] = Side.sideToColor(Cube.Positions.CENTER);
        result.get(Cube.Positions.CENTER).data[2][1] = Side.sideToColor(Cube.Positions.CENTER);
        result.get(Cube.Positions.CENTER).data[2][2] = Side.sideToColor(Cube.Positions.CENTER);

        result.get(Cube.Positions.RIGHT).data[2][1] = Side.sideToColor(Cube.Positions.LEFT);
        result.get(Cube.Positions.OUTSIDE).data[0][1] = Side.sideToColor(Cube.Positions.TOP);

        return result;
    }
}
