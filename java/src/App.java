import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import bfcdp.BFCDataProcessing;
import bfcdp.enums.EnumCandidateType;

public class App {
    public static void main(String[] args) throws Exception {
        EnumCandidateType candidateType = (args[0].compareTo("--federal") == 0)? EnumCandidateType.FEDERAL : EnumCandidateType.STATE;
        String candidateQuery = args[1];
        String votingSection = args[2];
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(args[3]);
        BFCDataProcessing electoralSystem = new BFCDataProcessing(date);

        try{
            FileInputStream candidatesFile = new FileInputStream(candidateQuery);
            electoralSystem.readCandidates(candidatesFile, candidateType);
            candidatesFile.close();
        }catch(FileNotFoundException fileException){
            System.out.println(fileException.getMessage());
            fileException.printStackTrace();
        }catch(Exception e){
            System.out.println("Arquivo de entrada Candidatos inexistente!");
            e.printStackTrace();
        }

        try{
            FileInputStream votesFile =  new FileInputStream(votingSection);
            electoralSystem.readVotes(votesFile, candidateType);    
            votesFile.close();
        }catch(FileNotFoundException fileException){
            System.out.println(fileException.getMessage());
            fileException.printStackTrace();
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        electoralSystem.countElectedCandidates();
        electoralSystem.printElectedCandidates(candidateType);       
        electoralSystem.printMostVotedCandidates(candidateType);   
        electoralSystem.printCouldBeElectedCandidates();
        electoralSystem.EleitosBeneficiados();
        electoralSystem.mostVotedParties();
        electoralSystem.printFirstAndLastByElectoralParty();
        electoralSystem.printElectedCandidatesByAge(date);
        electoralSystem.printElectedCandidatesByGender();                     
        electoralSystem.printVoteSummary();
    }
}