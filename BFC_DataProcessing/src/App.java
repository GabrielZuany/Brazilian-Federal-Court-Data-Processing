import java.util.Date;


import bfcdp.candidate.Candidate;
import bfcdp.candidate.FederalCandidate;
import bfcdp.candidate.StateCandidate;
import bfcdp.electoralparty.ElectoralParty;
import bfcdp.enums.EnumApplication;


//import bfcdp.electoralparty.ElectoralParty;
//import bfcdp.BFCDataProcessing;
import bfcdp.enums.EnumGender;
import bfcdp.enums.EnumResult;

public class App {
    public static void main(String[] args) throws Exception {
        //Candidate candidate = new Candidate(null, null, null, null, null, null, null); // impossible to instantiate an abstract class
        ElectoralParty electoralParty = new ElectoralParty("13","PT", "13113", 133536, 13070, 4);
        Candidate federalCandidate = new FederalCandidate("1", "Zuzu Partidario da esquerda", new Date(), EnumGender.MALE, EnumResult.WIN, EnumApplication.APPROVED, electoralParty, 1456);
        Candidate federalCandidate2 = new FederalCandidate("2", "Lolo", new Date(), EnumGender.MALE, EnumResult.LOSE, EnumApplication.REJECTED, electoralParty, 220);

        if(federalCandidate instanceof FederalCandidate){
            System.out.println("yay");
            System.out.print(federalCandidate.toString());
            System.out.print(federalCandidate2.toString() + "\n");
            System.out.println(electoralParty.toString());
        }else{
            System.out.println("nay");
        }
        
        //StateCandidate stateCandidate = new StateCandidate();
        //ElectoralParty electoralParty = new ElectoralParty();
        //BFCDataProcessing bfcDataProcessing = new BFCDataProcessing();
        //System.out.println("Hello, World!");
    }
}

