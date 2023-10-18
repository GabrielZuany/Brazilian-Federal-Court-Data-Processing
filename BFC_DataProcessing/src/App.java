import java.util.Date;


import bfcdp.candidate.Candidate;
import bfcdp.candidate.FederalCandidate;
//import bfcdp.candidate.StateCandidate;
import bfcdp.electoralparty.ElectoralParty;
import bfcdp.enums.EnumApplication;


//import bfcdp.electoralparty.ElectoralParty;
//import bfcdp.BFCDataProcessing;
import bfcdp.enums.EnumGender;
import bfcdp.enums.EnumResult;

public class App {
    public static void main(String[] args) throws Exception {
        //Candidate candidate = new Candidate(null, null, null, null, null, null, null); // impossible to instantiate an abstract class
        ElectoralParty PL = new ElectoralParty("22","PL", "22222", 246183, 19215, 5);
        ElectoralParty PT = new ElectoralParty("13","PT", "13113", 133536, 13070, 2);

        Candidate federalCandidate = new FederalCandidate("1", "Zuzu Partidario da esquerda", new Date(), EnumGender.MALE, EnumResult.WIN, EnumApplication.APPROVED, PT, 1456);
        Candidate federalCandidate2 = new FederalCandidate("2", "Zuzu Partidario da direita", new Date(), EnumGender.MALE, EnumResult.LOSE, EnumApplication.REJECTED, PL, 220);

        if(federalCandidate instanceof FederalCandidate){
            System.out.println("yay");
            System.out.print(federalCandidate.toString());
            System.out.print(federalCandidate2.toString() + "\n");
            System.out.print(federalCandidate2.toStringDebug());
            System.out.println(federalCandidate.toStringDebug());
            System.out.print(PT.toString());
            System.out.println(PL.toString());
        }else{
            System.out.println("nay");
        }
        
        //StateCandidate stateCandidate = new StateCandidate();
        //ElectoralParty electoralParty = new ElectoralParty();
        //BFCDataProcessing bfcDataProcessing = new BFCDataProcessing();
        //System.out.println("Hello, World!");
    }
}

