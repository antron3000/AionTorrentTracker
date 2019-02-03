package avm;

import org.aion.avm.api.Address;

public class Submission {
    public Address submittingAddress;
    public String magnetLink;


    public Submission(Address address, String magnetLink) {
        this.submittingAddress = address;
        this.magnetLink = magnetLink;
    }
}
