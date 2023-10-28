package bfcdp.electoralparty;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ElectoralParty {
    private String id;                                               // equals to NR_PARTIDO
    private String acronym;                                          // equals to SG_PARTIDO
    private String federationId;                                     // equals to NR_FEDERACAO
    private int votesNominais;
    private int votesLegenda;
    private int candidatesWin;
    private List<String> candidatesId = new ArrayList<String>();     // save the id of the candidates (MAP KEY)      
    
    public ElectoralParty(String id, String acronym, String federationId) {
        this.id = id;
        this.acronym = acronym;
        this.federationId = federationId;
    }

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

    public int getVotesLegenda() {
        return this.votesLegenda;
    }

    public int getVotesNominais(){
        return this.votesNominais;
    }
    
    public int getVotes(){
        return this.votesLegenda + this.votesNominais;
    }

    public void addVotesLegenda(int votes) {
        this.votesLegenda += votes;
    }

    public void addVotesNominais(int votes){
        this.votesNominais += votes;
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

    public void addCandidatesWin(){
        this.candidatesWin++;
    }

    @Override
    public String toString(){
        Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nfBr = NumberFormat.getNumberInstance(brLocale);
        nfBr.setGroupingUsed(true);
		nfBr.setMaximumFractionDigits(2);

        String voto;
        String nominal;
        if((votesNominais + votesLegenda) < 2)
            voto = " voto";
        else
            voto = " votos";
        
        if(votesNominais < 2){
            nominal = " nominal e ";
        }
        else{
            nominal = " nominais e ";
        }

        String string = acronym + " - " + id + ", " + nfBr.format(votesNominais + votesLegenda) + voto + " (" + nfBr.format(votesNominais) + nominal + nfBr.format(votesLegenda) + " de legenda), " + candidatesWin;
        if (candidatesWin > 1)
            return string + " candidatos eleitos";
        return string + " candidato eleito";
    }
}