package bfcdp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bfcdp.candidate.Candidate;
import bfcdp.electoralparty.ElectoralParty;
import bfcdp.enums.EnumResult;

public class BFCDataProcessing {
    private HashMap<String,Candidate> candidates = new HashMap<String,Candidate>();
    private List<ElectoralParty> electoralParties = new ArrayList<ElectoralParty>(); // switch to Set<>();
    
    public List<ElectoralParty> getElectoralParties() {
        return new ArrayList<ElectoralParty>(electoralParties);
    }

    public ElectoralParty getElectoralPartyById(String electoralPartyId){
        for (ElectoralParty electoralParty : electoralParties) {
            if(electoralParty.getId() == null){
                return null;
            }
            if(electoralParty.getId().equals(electoralPartyId)){
                return electoralParty;
            }
        }
        return null;
    }

    public ElectoralParty getElectoralParty(String candidateId){
        Candidate c = candidates.get(candidateId);
        return c.getElectoralParty();
    }

    public ElectoralParty getElectoralParty(Candidate candidate){
        return candidate.getElectoralParty();
    }

    public List<Candidate> getCandidates() {
        return new ArrayList<Candidate>(candidates.values());
    }
    
    public List<Candidate> getCandidates(ElectoralParty electoralParty) {
        List<String> candidatesId = electoralParty.getCandidatesId();
        List<Candidate> candidate_list = new ArrayList<Candidate>();
        for (String candidateId : candidatesId) {
            candidate_list.add(candidates.get(candidateId));
        }
        return candidate_list;
    }

    public void addCandidate(Candidate candidate) {
        if(!candidates.containsKey(candidate.getId())){
            candidates.put(candidate.getId(), candidate);
            candidate.getElectoralParty().addCandidate(candidate.getId());
            if(!electoralParties.contains(candidate.getElectoralParty())){
                electoralParties.add(candidate.getElectoralParty());
            }
        }
    }

    public void removeCandidate(Candidate candidate) {
        if(candidates.containsKey(candidate.getId())){
            candidates.remove(candidate.getId());
        }
    }

    public Candidate getCandidate(String candidateId) {
        return candidates.get(candidateId);
    }

    public void addElectoralParty(ElectoralParty electoralParty) {
        if(!electoralParties.contains(electoralParty)){
            electoralParties.add(electoralParty);
        }
    }

    public void removeElectoralParty(ElectoralParty electoralParty) {
        if(electoralParties.contains(electoralParty) && electoralParty.getCandidatesId().isEmpty()){
            electoralParties.remove(electoralParty);
        }
    }

    // item 6 da especificacao
    public void partiesVoteRelatory(){
        int partyVotes = 0;
        int totalWinners = 0;
        for (ElectoralParty electoralParty : electoralParties) {
            List<Candidate> candidateList = getCandidates(electoralParty);
            for (Candidate candidate : candidateList) {
                partyVotes+=candidate.getVotes();
                if(candidate.getResult() == EnumResult.WIN){
                    totalWinners++;
                } 
            }
            System.out.println("Total de votos do partido " + electoralParty.getAcronym() + ": " + partyVotes);
            partyVotes = 0;
        }
        System.out.println("Total de candidatos eleitos: " + totalWinners);
    }

    public void mostVotedCandidates(){
        List<Candidate> candidateList = getCandidates();
        candidateList.sort((c1, c2) -> c2.getVotes() - c1.getVotes());
        for (int i = 0; i < 10; i++) {
            System.out.println("Candidato " + candidateList.get(i).getBallotBoxId() + " - " + candidateList.get(i).getElectoralParty().getAcronym() + " - " + candidateList.get(i).getVotes() + " votos");
        }   
    }

    public void mostVotedParties(){
        List<ElectoralParty> partidos = getElectoralParties();
        partidos.sort((c1, c2) -> c2.getVotes() - c1.getVotes());
        for (int i = 0; i < 10; i++) {
            System.out.print(partidos.get(i).toString());
        }
    }

    public int getQtdCandidatosEleitos(ElectoralParty p){
        int count = 0;
        for(String id : p.getCandidatesId()){
            Candidate u = getCandidate(id);
            if(u.getResult() == EnumResult.WIN){
                count++;
            }
        }
        return count;
    }


}
