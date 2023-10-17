package bfcdp.electoralparty;

import java.util.ArrayList;
import java.util.List;
import bfcdp.candidate.Candidate;

public class ElectoralParty {
    private String id;                                               // equals to NR_PARTIDO
    private String acronym;                                          // equals to SG_PARTIDO
    private String federationId;                                     // equals to NR_FEDERACAO
    private List<Candidate> candidates = new ArrayList<Candidate>(); // equals to SQ_CANDIDATO
    private int allVotes = -1;                                       

    public ElectoralParty(String id, String acronym, String federationId) {
        this.id = id;
        this.acronym = acronym;
        this.federationId = federationId;
    }

    public ElectoralParty(String id, String acronym, String federationId, List<Candidate> candidates) {
        this.id = id;
        this.acronym = acronym;
        this.federationId = federationId;
        this.candidates = candidates;
    }

    public String getId() {
        return id;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getFederationId() {
        return federationId;
    }

    public List<Candidate> getCandidates() {
        return new ArrayList<Candidate>(candidates);
    }
    
    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public void removeCandidate(Candidate candidate) {
        candidates.remove(candidate);
    }

    private int calculateAllVotes() {
        int allVotes = 0;
        for (Candidate candidate : candidates) {
            allVotes += candidate.getVotes();
        }
        return allVotes;
    }

    public int getAllVotes() {
        return (this.allVotes == -1)? this.allVotes = calculateAllVotes() : this.allVotes;
    }

}
