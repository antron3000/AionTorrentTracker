package avm;

import org.aion.avm.userlib.AionList;

public class Bounty {
    String name;
    org.aion.avm.shadow.java.math.BigInteger amount;
    AionList<Submission> submissions;

    public Bounty(String name, org.aion.avm.shadow.java.math.BigInteger amount) {
        this.name = name;
        this.amount = amount;
        this.submissions = new AionList<>();
    }

    public void addSubmission(Submission s) {
        this.submissions.add(s);
    }

}
