package bfcdp.electoralparty;

import java.util.ArrayList;
import java.util.List;

public class ElectoralParty {
    private String id;                                               // equals to NR_PARTIDO
    private String acronym;                                          // equals to SG_PARTIDO
    private String federationId;                                     // equals to NR_FEDERACAO
    private List<String> candidatesId = new ArrayList<String>();     // save the id of the candidates (MAP KEY)                              

    public ElectoralParty(String id, String acronym, String federationId) {
        this.id = id;
        this.acronym = acronym;
        this.federationId = federationId;
    }

    public ElectoralParty(String id, String acronym, String federationId, List<String> candidatesId) {
        this.id = id;
        this.acronym = acronym;
        this.federationId = federationId;
        this.candidatesId = candidatesId;
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

    public List<String> getCandidatesId() {
        return candidatesId;
    }
    
    public void addCandidate(String candidateId) {
        if(!candidatesId.contains(candidateId)){
            candidatesId.add(candidateId);
        }
    }

    public void removeCandidate(String candidateId) {
        if(candidatesId.contains(candidateId)){
            candidatesId.remove(candidateId);
        }
    }
}
