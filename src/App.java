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


//coloquei as leituras separadas da main, corrigi o erro de leitura para federal que existia e n sabiamos, criei as 3 primeiras funcoes de impressao e deixei o toString no formato certo
//falta ajeitar o ponto no meio dos numeros, aquele metodo nao funciona, no meu computador ta printando virgula com ele, vamos ter q ver isso, eu tenho o codigo salvo de como colocar, posso fazer isso dps
//deixei todas as impressoes no final da main p ir fazendo, vou dormir agr, n sei se consigo acordar 2h certinho, mas com certeza eu entro, pd comecar sem mim se quiser, vou entrar assim q acordar
//implementei tbm o comparator de idade, ficou bem confuso devido a implementacao do Data, mas acho q ta funcionando, criei uma main e testei, funcionou mec, te mandei foto dela no zap

public class App {
    public static void main(String[] args) throws Exception {
        EnumCandidateType candidateType = (args[0].compareTo("--federal") == 0)? EnumCandidateType.FEDERAL : EnumCandidateType.STATE;
        String candidateQuery = args[1];
        String votingSection = args[2];
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(args[3]);
        BFCDataProcessing sistemaEleitoral = new BFCDataProcessing(date);


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

        sistemaEleitoral.numeroEleitos();

        sistemaEleitoral.DeputadosEleitos(candidateType);
        sistemaEleitoral.CandidatosMaisVotados(candidateType);
        // sistemaEleitoral.TeriamSidoEleitos();
        // sistemaEleitoral.EleitosBeneficiados();
        // sistemaEleitoral.mostVotedParties();
        // sistemaEleitoral.PrimeiroEUltimoColocadosPorPartido();
        // sistemaEleitoral.EleitosPorFaixaEtaria();
        // sistemaEleitoral.EleitosPorGenero();
        // sistemaEleitoral.ResumoVotos();
    }
}

//estamos colocando a quantidade de candidatos eleitos, depois implementar funcoes do trabalho

//enfase na contagem de nominais e legenda, talvez ele tenha errado

// 547889