import bfcdp.candidate.Candidate;
import bfcdp.candidate.FederalCandidate;
import bfcdp.candidate.StateCandidate;
import bfcdp.electoralparty.ElectoralParty;
import bfcdp.BFCDataProcessing;

public class App {
    public static void main(String[] args) throws Exception {
        Candidate candidate = new Candidate();
        FederalCandidate federalCandidate = new FederalCandidate();
        StateCandidate stateCandidate = new StateCandidate();
        ElectoralParty electoralParty = new ElectoralParty();
        BFCDataProcessing bfcDataProcessing = new BFCDataProcessing();
        System.out.println("Hello, World!");
    }
}

