package bfcdp.electoralparty;

import java.util.ArrayList;
import java.util.List;

public class ElectoralParty {
    private String id;                                               // equals to NR_PARTIDO
    private String acronym;                                          // equals to SG_PARTIDO
    private String federationId;                                     // equals to NR_FEDERACAO
    private int votesNominais;
    private int votesLegenda;
    private int candidatesWin;
    private List<String> candidatesId = new ArrayList<String>();     // save the id of the candidates (MAP KEY)                              

    public ElectoralParty(String id, String acronym, String federationId, int votesNominais, int votesLegenda, int candidatesWin) {
        this.id = id;
        this.acronym = acronym;
        this.federationId = federationId;
        this.votesNominais = votesNominais;
        this.candidatesWin = candidatesWin;
        this.votesLegenda = votesLegenda;
    }

    public ElectoralParty(String id, String acronym, String federationId, int votesNominais, int votesLegenda, int candidatesWin ,List<String> candidatesId) {
        this.id = id;
        this.acronym = acronym;
        this.federationId = federationId;
        this.votesNominais = votesNominais;
        this.votesLegenda = votesLegenda;
        this.candidatesWin = candidatesWin;
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

    public int getVotesNominais() {
        return votesNominais;
    }
    public int getVotesLegenda() {
        return votesLegenda;
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

    @Override
    public String toString(){
        String string = "ElectoralParty: " + acronym + " - " + id + ", " + (votesNominais + votesLegenda) + " votos" + " (" + votesNominais + " nominais e " + votesLegenda + " de legenda), " + candidatesWin;
        if (candidatesWin > 1)
            return string + " candidatos eleitos\n";
        return string + " candidato eleito\n";
    }
}
