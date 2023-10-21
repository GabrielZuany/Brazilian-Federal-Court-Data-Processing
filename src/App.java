import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import bfcdp.BFCDataProcessing;
import bfcdp.candidate.Candidate;
import bfcdp.candidate.FederalCandidate;
import bfcdp.candidate.StateCandidate;
import bfcdp.electoralparty.ElectoralParty;
import bfcdp.enums.EnumApplication;
import bfcdp.enums.EnumCandidateType;
import bfcdp.enums.EnumGender;
import bfcdp.enums.EnumResult;
import bfcdp.enums.EnumVoteType;

public class App {
    public static void main(String[] args) throws Exception {
        EnumCandidateType candidateType = (args[0]=="--federal")? EnumCandidateType.FEDERAL : EnumCandidateType.STATE;
        String candidateQuery = args[1];
        String votingSection = args[2];
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(args[3]);
        BFCDataProcessing sistemaEleitoral = new BFCDataProcessing();


        //LEITURA CANDIDATOS E PARTIDOS
        try(FileInputStream fileCandidates = new FileInputStream(candidateQuery)){
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
                        birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(items);
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
                    electoralParty = sistemaEleitoral.getElectoralPartyById(electoralPartyId);
                    if(electoralParty == null){
                        electoralParty = new ElectoralParty(electoralPartyId, electoralPartyAcronym, electoralPartyFederationId);
                        sistemaEleitoral.addElectoralParty(electoralParty);
                    }

                    if(candidateType == EnumCandidateType.FEDERAL){
                        candidate = new FederalCandidate(id, ballotBoxId, birthDate, gender, result, application, voteType, electoralParty);
                    }else{
                        candidate = new StateCandidate(id, ballotBoxId, birthDate, gender, result, application, voteType, electoralParty);
                    }
                    sistemaEleitoral.addCandidate(candidate);
                    //System.out.print(candidate.toStringDebug());
                }
                skip = false;
            }
        }
        catch(Exception e){
            System.out.println("Arquivo de entrada Candidatos inexistente!");
            e.printStackTrace();
        }

        //LEITURA CANDIDATOS E PARTIDOS
        String votableId = null;    //NR_VOTAVEL
        int votes = 0;              //QT_VOTOS_NOMINAIS
        try(FileInputStream fileVotos =  new FileInputStream(votingSection)){
            Scanner scanner = new Scanner(fileVotos, "ISO-8859-1");
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

                Candidate c = sistemaEleitoral.getCandidate(votableId);
                if(c != null){
                    if((c.getVoteType() == EnumVoteType.NOMINAL) && (c.getApplication() == EnumApplication.APPROVED)){
                        c.addVotes(votes);
                        c.getElectoralParty().addVotesNominais(votes);
                    }
                    // else{
                    //     c.getElectoralParty().addVotesLegenda(votes);
                    // }
                }else{
                    ElectoralParty e = sistemaEleitoral.getElectoralPartyById(votableId); 
                    if(e == null){
                        System.out.println("Partido não encontrado!");
                        break;
                    }
                    e.addVotesLegenda(votes);
                }
            }  
            
        }        
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        //sistemaEleitoral.mostVotedCandidates();
        sistemaEleitoral.mostVotedParties();
    }
}



// 547889