package bfcdp;

import java.util.HashMap;
import java.util.List;

import bfcdp.candidate.Candidate;
import bfcdp.electoralparty.ElectoralParty;

public class BFCDataProcessing {
    private HashMap<String,Candidate> candidates = new HashMap<String,Candidate>();
    private List<ElectoralParty> electoralParties;
    
    public List<ElectoralParty> getElectoralParties() {
        return electoralParties;
    }

    public ElectoralParty getElectoralParty(String candidateId){
        Candidate c = candidates.get(candidateId);
        return c.getElectoralParty();
    }

    public ElectoralParty getElectoralParty(Candidate candidate){
        return candidate.getElectoralParty();
    }

    public HashMap<String, Candidate> getCandidates() {
        return candidates;
    }
    
    public List<Candidate> getCandidates(ElectoralParty electoralParty) {
        List<String> candidatesId = electoralParty.getCandidatesId();
        List<Candidate> candidates = this.candidates
            .values()
            .stream()
            .filter(candidate -> candidatesId.contains(candidate.getId()))
            .toList();
        return candidates;
    }
}
