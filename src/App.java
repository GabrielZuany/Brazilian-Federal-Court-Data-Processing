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
            sistemaEleitoral.LeCandidatos(fileCandidates, candidateType);
        }catch(Exception e){
            System.out.println("Arquivo de entrada Candidatos inexistente!");
            e.printStackTrace();
        }

        //LEITURA VOTOS
        try(FileInputStream fileVotos =  new FileInputStream(votingSection)){
            sistemaEleitoral.LeVotos(fileVotos, candidateType);            
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        //sistemaEleitoral.mostVotedCandidates();
        sistemaEleitoral.numeroEleitos();
        sistemaEleitoral.mostVotedParties();
    }
}

//estamos colocando a quantidade de candidatos eleitos, depois implementar funcoes do trabalho

//enfase na contagem de nominais e legenda, talvez ele tenha errado

// 547889