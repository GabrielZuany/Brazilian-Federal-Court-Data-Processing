package bfcdp.candidate;

import java.util.Date;

import bfcdp.electoralparty.ElectoralParty;
import bfcdp.enums.EnumApplication;
import bfcdp.enums.EnumCandidateType;
import bfcdp.enums.EnumGender;
import bfcdp.enums.EnumResult;
import bfcdp.enums.EnumVoteType;

public class FederalCandidate extends Candidate{

    public FederalCandidate(String id, String ballotBoxId, Date birthDate, EnumGender gender, EnumResult result,
            EnumApplication application, EnumVoteType voteType, ElectoralParty electoralParty, int votes) {
        super(id, ballotBoxId, birthDate, gender, result, application, voteType ,electoralParty, votes);
    }

    public FederalCandidate(String id, String ballotBoxId, Date birthDate, EnumGender gender, EnumResult result,
            EnumApplication application, EnumVoteType voteType, ElectoralParty electoralParty) {
        super(id, ballotBoxId, birthDate, gender, result, application, voteType, electoralParty);
    }

    @Override
    public EnumCandidateType getCandidateType(){
        return EnumCandidateType.FEDERAL;
    }

    @Override
    public String toString(){
        return super.toString();
    }
}