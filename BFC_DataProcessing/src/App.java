import java.util.Date;

import bfcdp.candidate.Candidate;
import bfcdp.candidate.FederalCandidate;
import bfcdp.candidate.StateCandidate;
import bfcdp.enums.EnumApplication;
//import bfcdp.electoralparty.ElectoralParty;
//import bfcdp.BFCDataProcessing;
import bfcdp.enums.EnumGender;
import bfcdp.enums.EnumResult;

public class App {
    public static void main(String[] args) throws Exception {
        //Candidate candidate = new Candidate(null, null, null, null, null, null, null); // impossible to instantiate an abstract class
        Candidate federalCandidate = 
        new FederalCandidate("1", "0", new Date(), EnumGender.MALE, EnumResult.WIN, EnumApplication.APPROVED, null, 1456);

        if(federalCandidate instanceof FederalCandidate){
            System.out.println("yay");
        }else{
            System.out.println("nay");
        }
        
        //StateCandidate stateCandidate = new StateCandidate();
        //ElectoralParty electoralParty = new ElectoralParty();
        //BFCDataProcessing bfcDataProcessing = new BFCDataProcessing();
        //System.out.println("Hello, World!");
    }
}

