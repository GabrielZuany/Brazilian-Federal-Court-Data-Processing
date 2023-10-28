package bfcdp;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Collections;

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
    private HashMap<String,ElectoralParty> electoralParties = new HashMap<String,ElectoralParty>();
    int countElectedCandidates = 0;
    Date date;
    
    public BFCDataProcessing(Date date){
        this.date = date;
    }

    public void sumElectedCandidates(){
        this.countElectedCandidates++;
    }

    public void countElectedCandidates(){
        System.out.println("Número de vagas: " + countElectedCandidates);
    }

    public List<ElectoralParty> getElectoralParties() {
        return new LinkedList<ElectoralParty>(electoralParties.values());
    }

    public ElectoralParty getElectoralPartyById(String electoralPartyId){
        return electoralParties.get(electoralPartyId);
    }

    public ElectoralParty getElectoralParty(String candidateId){
        Candidate c = candidates.get(candidateId);
        return c.getElectoralParty();
    }

    public ElectoralParty getElectoralParty(Candidate candidate){
        return candidate.getElectoralParty();
    }

    public List<Candidate> getCandidates() {
        return new LinkedList<Candidate>(candidates.values());
    }
    
    public List<Candidate> getCandidates(ElectoralParty electoralParty) {
        List<Candidate> candidate_list = new LinkedList<Candidate>();
        List<String> candidatesId = electoralParty.getCandidatesId();
        for (String candidateId : candidatesId) {
            candidate_list.add(candidates.get(candidateId));
        }
        return candidate_list;
    }

    public void addCandidate(Candidate candidate) {
        if(!candidates.containsKey(candidate.getId())){
            candidates.put(candidate.getId(), candidate);
            candidate.getElectoralParty().addCandidate(candidate.getId());
            if(!electoralParties.values().contains(candidate.getElectoralParty())){
                electoralParties.put(candidate.getElectoralParty().getId(), candidate.getElectoralParty());
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
        if(!electoralParties.values().contains(electoralParty)){
            electoralParties.put(electoralParty.getId(), electoralParty);
        }
    }

    public void partiesVoteRelatory(){
        int partyVotes = 0;
        int totalWinners = 0;
        for (ElectoralParty electoralParty : electoralParties.values()) {
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

    public static enum EnumComparatorVotosPartido implements Comparator<ElectoralParty>{
        INSTANCE;
        @Override
        public int compare(ElectoralParty p1, ElectoralParty p2){
            Locale brLocale = Locale.forLanguageTag("pt-BR");
			NumberFormat nf = NumberFormat.getInstance(brLocale);

            if(p1.getVotes() == p2.getVotes()){
                try{
                    int id1 = nf.parse(p1.getId()).intValue();
                    int id2 = nf.parse(p2.getId()).intValue();
                    return id1 - id2;
                }catch (ParseException e) {
			        System.out.println("Formato Inválido!");
		        }
            }
            return p2.getVotes() - p1.getVotes();
        }
    }

    public static enum EnumComparatorVotosCandidato implements Comparator<Candidate>{
        INSTANCE;
        @Override
        public int compare(Candidate c1, Candidate c2){
            if(c1.getVotes() == c2.getVotes()){
                return c1.getBirthDate().compareTo(c2.getBirthDate());
            }
            return c2.getVotes() - c1.getVotes();
        }
    }


    public void mostVotedParties(){
        List<ElectoralParty> electoralParties = getElectoralParties();
        Collections.sort(electoralParties, EnumComparatorVotosPartido.INSTANCE);

        System.out.println("\nVotação dos partidos e número de candidatos eleitos:");
        int count = 1;
        for (ElectoralParty electoralParty : electoralParties) {
            System.out.println(count + " - " + electoralParty.toString());
            count++;
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

    public void printCouldBeElectedCandidates(){
        List<Candidate> candidatos = getCandidates();
        Collections.sort(candidatos, EnumComparatorVotosCandidato.INSTANCE);
        int count = 0;

        System.out.println("\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)");
        for(Candidate c : candidatos){
            count++;
            if(c.getResult() == EnumResult.LOSE){
                System.out.println(count + " - " + c.toString());
                
            }
            if(count == this.countElectedCandidates) break;
        }
    }

    public void EleitosBeneficiados(){
        List<Candidate> candidatos = getCandidates();
        Collections.sort(candidatos, EnumComparatorVotosCandidato.INSTANCE);
        int count = 0;
        int maior = 0;

        System.out.println("\nEleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)");
        for(Candidate c : candidatos){
            count++;
            if(count == this.countElectedCandidates){
                maior = c.getVotes();
                break;
            }
        }

        count = 0;
        for(Candidate c : candidatos){
            count++;
            if(c.getResult() == EnumResult.WIN){
                if(c.getVotes() < maior){
                    System.out.println(count + " - " + c.toString());
                }
            }
        }
    }

    public void printFirstAndLastByElectoralParty(){
        System.out.println("\nPrimeiro e último colocados de cada partido:");
        List<Candidate> candidatos = getCandidates();
        Collections.sort(candidatos, EnumComparatorVotosCandidato.INSTANCE);
        List<ElectoralParty> partidos = getElectoralParties();
        int count = 0;
        Candidate primeiro = null;
        Candidate ultimo = null;
        Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nfBr = NumberFormat.getNumberInstance(brLocale);
        nfBr.setGroupingUsed(true);
		nfBr.setMaximumFractionDigits(2);

        for(Candidate c : candidatos){
            if(partidos.contains(c.getElectoralParty())){

                partidos.remove(c.getElectoralParty());
                if(c.getElectoralParty().getVotesNominais() == 0)
                    continue;
                count++;

                List<String> candidatosId = c.getElectoralParty().getCandidatesId();
                List<Candidate> candidatosDoPartido = new LinkedList<Candidate>();
                for(String id : candidatosId){
                    Candidate u = getCandidate(id);
                    candidatosDoPartido.add(u);
                }

                Collections.sort(candidatosDoPartido, EnumComparatorVotosCandidato.INSTANCE);
                primeiro = candidatosDoPartido.get(0);
                
                int i = 0;
                int size = candidatosDoPartido.size();
                do{
                    i++;
                    if(i == size){
                        ultimo = primeiro;
                        break;
                    }
                    ultimo = candidatosDoPartido.get(size-i);
                }while(ultimo.getApplication() != EnumApplication.APPROVED);

                String votoPrimeiro;
                String votoUltimo;
                if (primeiro.getVotes() < 2)
                    votoPrimeiro = "voto";
                else
                    votoPrimeiro = "votos";
                if (ultimo.getVotes() < 2)
                    votoUltimo = "voto";
                else
                    votoUltimo = "votos";
                System.out.println(count + " - "  + primeiro.getElectoralParty().getAcronym() + " - " + ultimo.getElectoralParty().getId() + ", " + primeiro.getBallotBoxId() + " (" + primeiro.getId() + ", " + nfBr.format(primeiro.getVotes()) + " " + votoPrimeiro + ")" + //
                " / " + ultimo.getBallotBoxId() + " (" + ultimo.getId() + ", " + nfBr.format(ultimo.getVotes()) + " " + votoUltimo + ")");    
            }
        }
    }   

    public void printElectedCandidates(EnumCandidateType candidateType){
        List<Candidate> candidatos = getCandidates();
        Collections.sort(candidatos, EnumComparatorVotosCandidato.INSTANCE);
        int count = 0;

        if(candidateType == EnumCandidateType.STATE) System.out.println("\nDeputados estaduais eleitos:");
        else System.out.println("\nDeputados federais eleitos:");
        
        for(Candidate c : candidatos){
            if(c.getResult() == EnumResult.WIN){
                count++;
                System.out.println(count + " - " + c.toString());
                
            }
            if(count == this.countElectedCandidates) break;
        }
    }

    public void printMostVotedCandidates(EnumCandidateType candidateType){
        List<Candidate> candidatos = getCandidates();
        Collections.sort(candidatos, EnumComparatorVotosCandidato.INSTANCE);
        int count = 0;

        System.out.println("\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
       
        for(Candidate c : candidatos){
            count++;
            System.out.println(count + " - " + c.toString());
            if(count == this.countElectedCandidates) break;
        }
    }

    public void printElectedCandidatesByGender() {
        int male = 0;
        System.out.println();
        for(Candidate c: this.candidates.values()){
            if(c.getResult() == EnumResult.WIN){
                if(c.getGender() == EnumGender.MALE) male++;
            }
        }
        
        double male_percentage = (((double)male)*100)/this.countElectedCandidates;
        double female_percentage = 100-male_percentage;

        Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nfBr = NumberFormat.getNumberInstance(brLocale);
        nfBr.setGroupingUsed(true);
		nfBr.setMaximumFractionDigits(2);
        nfBr.setMinimumFractionDigits(2);

        System.out.println("Eleitos, por gênero:");
        System.out.println("Feminino:  " + (this.countElectedCandidates - male) + " (" + nfBr.format(female_percentage) + "%)");
        System.out.println("Masculino: " + male + " (" + nfBr.format(male_percentage) + "%)");
    }

    public void printVoteSummary(){
        int nominais = 0;
        int legenda = 0;
        for(ElectoralParty p : getElectoralParties()){
            nominais += p.getVotesNominais();
            legenda += p.getVotesLegenda();
        }
        Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nfBr = NumberFormat.getNumberInstance(brLocale);
        nfBr.setGroupingUsed(true);
		nfBr.setMaximumFractionDigits(2);
        nfBr.setMinimumFractionDigits(2);
        
		NumberFormat intNfBr = NumberFormat.getNumberInstance(brLocale);
        intNfBr.setGroupingUsed(true);
		intNfBr.setMaximumFractionDigits(2);

        System.out.println("\nTotal de votos válidos:    " + intNfBr.format(legenda + nominais));
        System.out.println("Total de votos nominais:   " + intNfBr.format(nominais) + " (" + nfBr.format(((double)nominais*100)/(double)(legenda + nominais)) + "%)");
        System.out.println("Total de votos de legenda: " + intNfBr.format(legenda) + " (" + nfBr.format(((double)legenda*100)/(double)(legenda + nominais)) + "%)");
        System.out.print("\n");
    }

    public void printElectedCandidatesByAge(Date date) {
        int idade30 = 0;
        int idade40 = 0;
        int idade50 = 0;
        int idade60 = 0;
        int idade60mais = 0;
        List<Candidate> candidatos = getCandidates();

        for(Candidate c : candidatos){
            if(c.getResult() == EnumResult.LOSE) continue;
            int idade = 0;

            //considerando idade sempre maior que 0 anos
            idade = date.getYear() - c.getBirthDate().getYear() - 1;
            if(date.getMonth() > c.getBirthDate().getMonth()){
                idade++;
            }
            else if(date.getMonth() == c.getBirthDate().getMonth()){
                if(date.getDay() >= c.getBirthDate().getDay()){
                    idade++;
                }
            }
            
            if(idade < 30) idade30++;
            else if(30 <= idade && idade < 40) idade40++;
            else if(40 <= idade && idade < 50) idade50++;
            else if(50 <= idade && idade < 60) idade60++;
            else if(60 <= idade) idade60mais++;

        }
        Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nfBr = NumberFormat.getNumberInstance(brLocale);
        nfBr.setGroupingUsed(true);
		nfBr.setMinimumFractionDigits(2);
        nfBr.setMaximumFractionDigits(2);

        System.out.println("\nEleitos, por faixa etária (na data da eleição):");
        System.out.println("      Idade < 30: " + idade30 + " (" + nfBr.format(((double)idade30*100)/(double)this.countElectedCandidates) + "%)");
        System.out.println("30 <= Idade < 40: " + idade40 + " (" + nfBr.format(((double)idade40*100)/(double)this.countElectedCandidates) + "%)");
        System.out.println("40 <= Idade < 50: " + idade50 + " (" + nfBr.format(((double)idade50*100)/(double)this.countElectedCandidates) + "%)");
        System.out.println("50 <= Idade < 60: " + idade60 + " (" + nfBr.format(((double)idade60*100)/(double)this.countElectedCandidates) + "%)");
        System.out.println("60 <= Idade     : " + idade60mais + " (" + nfBr.format(((double)idade60mais*100)/(double)this.countElectedCandidates) + "%)");
    }

    public void readCandidates(FileInputStream  fileCandidates, EnumCandidateType candidateType){
        Scanner scanner = new Scanner(fileCandidates, "ISO-8859-1");
        boolean skip = true;
        boolean ghostCandidate = false;
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
            line = line.replace("\"", "");
            String[] columns = line.split(";");

            ghostCandidate = (!(columns[13].equals("6") && candidateType == EnumCandidateType.FEDERAL) && !(columns[13].equals("7") && candidateType == EnumCandidateType.STATE))? true : false;
            id = columns[16];
            ballotBoxId = columns[18];
            electoralPartyId = columns[27];
            electoralPartyAcronym = columns[28];
            electoralPartyFederationId = columns[30];
            gender = columns[45].equals("2")? EnumGender.MALE : EnumGender.FEMALE;
            result = columns[56].equals("2") | columns[56].equals("3")? EnumResult.WIN : EnumResult.LOSE;
            application = (columns[68].equals("2") | columns[68].equals("16"))? EnumApplication.APPROVED : EnumApplication.REJECTED;
            try {
                birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(columns[42]);
            } catch (Exception e) {
                // e.printStackTrace();
                // System.out.println("Formato de data inválido!");
            }

            if(columns[67].equals("Válido (legenda)")){
                voteType = EnumVoteType.LEGENDA;
            }else if(columns[67].equals("Válido")){
                voteType = EnumVoteType.NOMINAL;
            }else{
                ghostCandidate = true;
            }            
            
            if(!skip){
                electoralParty = getElectoralPartyById(electoralPartyId);
                if(electoralParty == null){
                    electoralParty = new ElectoralParty(electoralPartyId, electoralPartyAcronym, electoralPartyFederationId);
                    addElectoralParty(electoralParty);
                }

                if(ghostCandidate == false){
                    if(candidateType == EnumCandidateType.FEDERAL){
                        candidate = new FederalCandidate(id, ballotBoxId, birthDate, gender, result, application, voteType, electoralParty);
                        addCandidate(candidate);
                    }else if(candidateType == EnumCandidateType.STATE){
                        candidate = new StateCandidate(id, ballotBoxId, birthDate, gender, result, application, voteType, electoralParty);
                        addCandidate(candidate);
                    }
    
                    if(candidate.getResult() == EnumResult.WIN){
                        candidate.getElectoralParty().addCandidatesWin();
                        sumElectedCandidates();
                    }
                }
            }
            skip = false;
            ghostCandidate = false;
        }
        scanner.close();
    }

    public void readVotes(FileInputStream fileVotos, EnumCandidateType candidateType){
        Scanner scanner = new Scanner(fileVotos, "ISO-8859-1");
        String votableId = null;    //NR_VOTAVEL
        int votes = 0;              //QT_VOTOS_NOMINAIS
        boolean header = true;
        boolean skip = false;

        while(scanner.hasNextLine()){
            skip = false;
            String line = scanner.nextLine();

            if(header){
                header = false;
                continue;
            }

            line = line.replace("\"", "");
            String[] columns = line.split(";");

            if(!(columns[17].equals("6") && candidateType == EnumCandidateType.FEDERAL) && !(columns[17].equals("7") && candidateType == EnumCandidateType.STATE)){
                skip = true;
            }
            votableId = columns[19];
            votes = Integer.parseInt(columns[21]);
                        
            if(skip) continue;

            Candidate c = getCandidate(votableId);
            ElectoralParty e = getElectoralPartyById(votableId); 
            if(c != null){
                if((c.getVoteType() == EnumVoteType.NOMINAL) && c.getApplication().equals(EnumApplication.APPROVED)){
                    c.addVotes(votes);
                    c.getElectoralParty().addVotesNominais(votes);
                }
                else if((c.getVoteType() == EnumVoteType.LEGENDA)){
                    c.getElectoralParty().addVotesLegenda(votes);
                }
            }else if (e != null){
                    e.addVotesLegenda(votes);
            }
        }
        scanner.close(); 
    }    
}
