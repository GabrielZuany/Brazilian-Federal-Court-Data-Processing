package bfcdp.candidate;

import java.util.Date;

import bfcdp.electoralparty.ElectoralParty;
import bfcdp.enums.EnumApplication;
import bfcdp.enums.EnumGender;
import bfcdp.enums.EnumResult;


public class StateCandidate extends Candidate{

    public StateCandidate(String id, String ballotBoxId, Date birthDate, EnumGender gender, EnumResult result,
            EnumApplication application, ElectoralParty electoralParty, int votes) {
        super(id, ballotBoxId, birthDate, gender, result, application, electoralParty, votes);
    }

    public StateCandidate(String id, String ballotBoxId, Date birthDate, EnumGender gender, EnumResult result,
            EnumApplication application, ElectoralParty electoralParty) {
        super(id, ballotBoxId, birthDate, gender, result, application, electoralParty);
    }

    @Override
    public String toString(){
        return "StateCandidate: " + super.toString();
    }
    
}
