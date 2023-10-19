import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import bfcdp.candidate.Candidate;
import bfcdp.candidate.FederalCandidate;
//import bfcdp.candidate.StateCandidate;
import bfcdp.electoralparty.ElectoralParty;
import bfcdp.enums.EnumApplication;
import bfcdp.enums.EnumCandidateType;
//import bfcdp.electoralparty.ElectoralParty;
//import bfcdp.BFCDataProcessing;
import bfcdp.enums.EnumGender;
import bfcdp.enums.EnumResult;


//java -jar deputados.jar --estadual consulta_cand_2022_ES.csv votacao_secao_2022_ES.csv 02/10/2022
//java -jar deputados.jar --federal consulta_cand_2022_ES.csv votacao_secao_2022_ES.csv 02/10/2022
public class App {
    public static void main(String[] args) throws Exception {
        EnumCandidateType candidateType = (args[0]=="--federal")? EnumCandidateType.FEDERAL : EnumCandidateType.STATE;
        String candidateQuery = args[1];
        String votingSection = args[2];
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(args[3]);
      
        /*System.out.println(candidateType);
        System.out.println(candidateQuery);
        System.out.println(votingSection);
        System.out.println(date);*/

        try(FileInputStream fin = new FileInputStream(candidateQuery)){
            Scanner scanner = new Scanner(fin, "ISO-8859-1");

            //System("parser.py"); 
            // pd.read_csv(fin) => drop(colunas inuteis) => to_csv("saida.csv")
            // fin = saida.csv
            // nome, data, numero, partido, votos, resultado, situacao, tipo
            // array[0] = nome
            // array[1] = data
            // array[2] = numero
            // array[3] = partido

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                line = line.replace("\"", "");
                System.out.println(line);
                
                try(Scanner lineScanner = new Scanner(line)){
                    lineScanner.useDelimiter(";");
                    String token = lineScanner.next();
                    // switch(count){
                    //     case 1: cand[count].data = data;
                    //     case 3: nome;
                    //     case 6:        
                    // }
                }catch(Exception e){
                    System.out.println("Erro ao ler linha");
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            System.out.println("Arquivo entrada.txt inexistente");
            e.printStackTrace();
        }
    }  
}


/*

    try(FileInputStream fin = new FileInputStream("entrada.csv")){
        Scanner s = new Scanner(fin, "ISO-8859-1");
        List<Aluno> alunos = new ArrayList<>();


        while(s.hasNextLine()){
            String line = s.nextLine();
            try(Scanner lineScanner = new Scanner(line)){
                lineScanner.useDelimiter(";");

                //matricula
                String token = lineScanner.next();
                System.out.println(token);


            }
        }
    
    }catch(Exception e){
        System.out.println("Arquivo entrada.txt inexistente");
        e.printStackTrace();
    }
    

 */


//Candidate candidate = new Candidate(null, null, null, null, null, null, null); // impossible to instantiate an abstract class
// ElectoralParty PL = new ElectoralParty("22","PL", "22222", 246183, 19215, 5);
// ElectoralParty PT = new ElectoralParty("13","PT", "13113", 133536, 13070, 2);

// Candidate federalCandidate = new FederalCandidate("1", "Zuzu Partidario da esquerda", new Date(), EnumGender.MALE, EnumResult.WIN, EnumApplication.APPROVED, PT, 1456);
// Candidate federalCandidate2 = new FederalCandidate("2", "Zuzu Partidario da direita", new Date(), EnumGender.MALE, EnumResult.LOSE, EnumApplication.REJECTED, PL, 220);

// if(federalCandidate instanceof FederalCandidate){
//     System.out.println("yay");
//     System.out.print(federalCandidate.toString());
//     System.out.print(federalCandidate2.toString() + "\n");
//     System.out.print(federalCandidate2.toStringDebug());
//     System.out.println(federalCandidate.toStringDebug());
//     System.out.print(PT.toString());
//     System.out.println(PL.toString());
// }else{
//     System.out.println("nay");
// }

// //StateCandidate stateCandidate = new StateCandidate();
// //ElectoralParty electoralParty = new ElectoralParty();
// //BFCDataProcessing bfcDataProcessing = new BFCDataProcessing();
// //System.out.println("Hello, World!");


// // int no = 2000000000;
// // String str = String.format("%,d", no);
// // //str = 124.750
// // System.out.println(str);