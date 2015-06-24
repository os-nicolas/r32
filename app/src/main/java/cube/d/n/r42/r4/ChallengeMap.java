package cube.d.n.r42.r4;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;import java.lang.String;

/**
 * Created by Colin_000 on 4/9/2015.
 */
@DynamoDBTable(tableName = "Challenges")
public class ChallengeMap {
    private int passes = 0;
    private int trys = 0;
    private String challenge = "";

    public ChallengeMap(String s) {
        this.challenge = s;
    }

    public ChallengeMap() {
    }

    @DynamoDBHashKey(attributeName = "challenge")
    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    @DynamoDBAttribute(attributeName = "pass")
    public int getPasses() {
        return passes;
    }

    public void setPasses(int passes) {
        this.passes = passes;
    }

    @DynamoDBAttribute(attributeName = "try")
    public int getTrys() {
        return trys;
    }

    public void setTrys(int trys) {
        this.trys = trys;
    }

}
