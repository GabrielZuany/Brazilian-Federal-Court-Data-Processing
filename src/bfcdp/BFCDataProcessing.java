package bfcdp;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;

import bfcdp.candidate.Candidate;
import bfcdp.candidate.FederalCandidate;
import bfcdp.candidate.StateCandidate;
import bfcdp.electoralparty.ElectoralParty;
import bfcdp.enums.EnumApplication;
import bfcdp.enums.EnumCandidateType;
import bfcdp.enums.EnumGender;
import bfcdp.enums.EnumResult;
import bfcdp.enums.EnumVoteType;

public class BFCDataProcessing {
    private HashMap<String,Candidate> candidates = new HashMap<String,Candidate>();
    private List<ElectoralParty> electoralParties = new ArrayList<ElectoralParty>(); // switch to Set<>();
    int numEleitos = 0;
    

    public void addEleitos(){
        this.numEleitos++;
    }


    public void numeroEleitos(){
        System.out.println("Número de vagas: " + numEleitos);
    }

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


    public void LeCandidatos(FileInputStream  fileCandidates, EnumCandidateType candidateType){
        Scanner scanner = new Scanner(fileCandidates, "ISO-8859-1");
        boolean skip = true;

        Candidate candidate = null;
        String id = null;                      // NR_CANDIDATO
        String ballotBoxId = null;             // NM_URNA_CANDIDATO
        Date birthDate = null;                 // DT_NASCIMENTO
        EnumGender gender = null;              // CD_GENERO
        EnumResult result = null;              // CD_SIT_TOT_TURNO
        EnumApplication application = null;    // CD_SITUACAO_CANDIDATO_TOT
        EnumVoteType voteType = null;           // NM_TIPO_DESTINACAO_VOTOS
        ElectoralParty electoralParty = null;
        String electoralPartyId = null;        // NR_PARTIDO
        String electoralPartyAcronym = null;   // SG_PARTIDO
        String electoralPartyFederationId = null; // NR_FEDERACAO


        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            int idx = 0;
            line = line.replace("\"", "");
            String[] columns = line.split(";");
            
            for (String items : columns) {
                if(skip){
                    break;
                }
                if(idx == 13){
                    if(items.equals("6") && candidateType == EnumCandidateType.FEDERAL){
                        candidateType = EnumCandidateType.FEDERAL;
                    }
                    else if(items.equals("7") && candidateType == EnumCandidateType.STATE){
                        candidateType = EnumCandidateType.STATE;
                    }
                    else{
                        skip = true;
                        break;
                    }
                }
                if(idx == 16){
                    id = items;
                }
                if(idx == 18){
                    ballotBoxId = items;
                }
                if(idx == 27){
                    electoralPartyId = items;
                }
                if(idx == 28){
                    electoralPartyAcronym = items;
                }
                if(idx == 30){
                    electoralPartyFederationId = items;
                }
                if(idx == 42){
                    try{
                        birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(items);
                    }catch(Exception e){
                        e.printStackTrace();
                         System.out.println("Formato de data inválido!");
                    }
                }
                if(idx == 45){
                    gender = items.equals("2")? EnumGender.MALE : EnumGender.FEMALE;
                }
                if(idx == 56){
                    result = items.equals("2") | items.equals("3")? EnumResult.WIN : EnumResult.LOSE;
                }
                if(idx == 67){
                    voteType = items.equals("Válido (legenda)")? EnumVoteType.LEGENDA : EnumVoteType.NOMINAL;
                }
                if(idx == 68){
                    application = (items.equals("2") | items.equals("16"))? EnumApplication.APPROVED : EnumApplication.REJECTED;
                }                    
                idx++;
            }
            if(!skip){
                electoralParty = getElectoralPartyById(electoralPartyId);
                if(electoralParty == null){
                    electoralParty = new ElectoralParty(electoralPartyId, electoralPartyAcronym, electoralPartyFederationId);

                    addElectoralParty(electoralParty);
                }

                if(candidateType == EnumCandidateType.FEDERAL){
                    candidate = new FederalCandidate(id, ballotBoxId, birthDate, gender, result, application, voteType, electoralParty);
                }else{
                    candidate = new StateCandidate(id, ballotBoxId, birthDate, gender, result, application, voteType, electoralParty);
                }

                addCandidate(candidate);

                if(candidate.getResult() == EnumResult.WIN){
                    candidate.getElectoralParty().addCandidatesWin();
                    addEleitos();
                }
            }
            skip = false;
        }
    }

    public void LeVotos(FileInputStream fileVotos, EnumCandidateType candidateType){
        Scanner scanner = new Scanner(fileVotos, "ISO-8859-1");
        String votableId = null;    //NR_VOTAVEL
        int votes = 0;              //QT_VOTOS_NOMINAIS
        boolean header = true;
        boolean skip;
        while(scanner.hasNextLine()){
            skip = false;
            String line = scanner.nextLine();

            if(header){ header = false; continue; }
            line = line.replace("\"", "");
            String[] columns = line.split(";");
            
            int idx = 0;    
            for (String items : columns) {
                if(idx == 17){
                    if(items.equals("6") && candidateType == EnumCandidateType.FEDERAL){
                        candidateType = EnumCandidateType.FEDERAL;
                    }
                    else if(items.equals("7") && candidateType == EnumCandidateType.STATE){
                        candidateType = EnumCandidateType.STATE;
                    }
                    else{
                        skip = true;
                        break;
                    }
                }
                if(idx == 19){
                    votableId = items;
                    if(items.equals("95") ||items.equals("96") ||items.equals("97") ||items.equals("98")){
                        skip = true;
                        break;
                    }
                }
                if(idx == 21){
                    votes = Integer.parseInt(items);
                }
                idx++;
            }
            
            if(skip) continue;

            Candidate c = getCandidate(votableId);
            if(c != null){
                if((c.getVoteType() == EnumVoteType.NOMINAL) && (c.getApplication() == EnumApplication.APPROVED)){
                    c.addVotes(votes);
                    c.getElectoralParty().addVotesNominais(votes);
                }
                else if((c.getVoteType() == EnumVoteType.LEGENDA)){
                    c.getElectoralParty().addVotesLegenda(votes);
                }
            }else{
                ElectoralParty e = getElectoralPartyById(votableId); 
                if(e == null){
                    System.out.println("Partido não encontrado!");
                    break;
                }
                e.addVotesLegenda(votes);
            }
        } 
    }

}
