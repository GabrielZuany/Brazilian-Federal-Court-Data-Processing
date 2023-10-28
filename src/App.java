import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


// alteirei a leitura para n matar o programa cedo

public class App {
    public static void main(String[] args) throws Exception {
        EnumCandidateType candidateType = (args[0].compareTo("--federal") == 0)? EnumCandidateType.FEDERAL : EnumCandidateType.STATE;
        String candidateQuery = args[1];
        String votingSection = args[2];
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(args[3]);
        BFCDataProcessing sistemaEleitoral = new BFCDataProcessing(date);


        //LEITURA CANDIDATOS E PARTIDOS
        try{
            FileInputStream fileCandidates = new FileInputStream(candidateQuery);
            sistemaEleitoral.LeCandidatos(fileCandidates, candidateType);
            fileCandidates.close();
        }catch(FileNotFoundException fileException){
            System.out.println(fileException.getMessage());
            fileException.printStackTrace();
        }catch(Exception e){
            System.out.println("Arquivo de entrada Candidatos inexistente!");
            e.printStackTrace();
        }

        //LEITURA VOTOS
        try{
            FileInputStream fileVotos =  new FileInputStream(votingSection);
            sistemaEleitoral.LeVotos(fileVotos, candidateType);    
            fileVotos.close();
        }catch(FileNotFoundException fileException){
            System.out.println(fileException.getMessage());
            fileException.printStackTrace();
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        sistemaEleitoral.numeroEleitos();
        sistemaEleitoral.DeputadosEleitos(candidateType);       
        sistemaEleitoral.CandidatosMaisVotados(candidateType);   
        sistemaEleitoral.TeriamSidoEleitos();
        sistemaEleitoral.EleitosBeneficiados();
        sistemaEleitoral.mostVotedParties();
        sistemaEleitoral.PrimeiroEUltimoColocadosPorPartido();
        sistemaEleitoral.EleitosPorFaixaEtaria(date);
        sistemaEleitoral.EleitosPorGenero();                     
        sistemaEleitoral.ResumoVotos();
    }
}