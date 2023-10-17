package bfcdp.candidate;

import java.util.Date;
import bfcdp.electoralparty.ElectoralParty;
import bfcdp.enums.EnumApplication;
import bfcdp.enums.EnumGender;
import bfcdp.enums.EnumResult;

public abstract class Candidate {
    private String id;                      // NR_CANDIDATO
    private String ballotBoxId;             // NM_URNA_CANDIDATO
    private Date birthDate;                 // DT_NASCIMENTO
    private EnumGender gender;              // CD_GENERO
    private EnumResult result;              // CD_SIT_TOT_TURNO
    private EnumApplication application;    // CD_SITUACAO_CANDIDATO_TOT
    //private EnumVoteType voteType;        // NM_TIPO_DESTINACAO_VOTOS
    private ElectoralParty electoralParty;
    private int votes;                      // QT_VOTOS_NOMINAIS

    public Candidate(String id, String ballotBoxId, Date birthDate, 
                    EnumGender gender, EnumResult result, EnumApplication application, 
                    ElectoralParty electoralParty) 
    {
        this.id = id;
        this.ballotBoxId = ballotBoxId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.result = result;
        this.application = application;
        this.electoralParty = electoralParty;
    }

    public Candidate(String id, String ballotBoxId, Date birthDate, 
                    EnumGender gender, EnumResult result, EnumApplication application,
                    ElectoralParty electoralParty, int votes) 
    {
        this.id = id;
        this.ballotBoxId = ballotBoxId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.result = result;
        this.application = application;
        this.electoralParty = electoralParty;
        this.votes = votes;
    }

    public String getId() {
        return this.id;
    }

    public String getBallotBoxId() {
        return this.ballotBoxId;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public EnumGender getGender() {
        return this.gender;
    }

    public EnumResult getResult() {
        return this.result;
    }

    public EnumApplication getApplication() {
        return this.application;
    }

    public ElectoralParty getElectoralParty() {
        return this.electoralParty;
    }

    public int getVotes() {
        return this.votes;
    }
    
}
