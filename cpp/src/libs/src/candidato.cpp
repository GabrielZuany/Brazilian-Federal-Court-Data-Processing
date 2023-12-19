#include "../include/candidato.h"

Candidato::Candidato()
{}

Candidato::Candidato( string &NR_CANDIDATO,  string &NM_URNA_CANDIDATO,  tm &DT_NASCIMENTO,  bool &CD_GENERO,  bool &CD_SIT_TOT_TURNO,  bool &CD_SITUACAO_CANDIDATO_TOT,  bool &NM_TIPO_DESTINACAO_VOTOS, Partido *electoralParty, string &NR_PARTIDO):
    NR_CANDIDATO(NR_CANDIDATO), NM_URNA_CANDIDATO(NM_URNA_CANDIDATO), DT_NASCIMENTO(DT_NASCIMENTO), CD_GENERO(CD_GENERO), CD_SIT_TOT_TURNO(CD_SIT_TOT_TURNO), CD_SITUACAO_CANDIDATO_TOT(CD_SITUACAO_CANDIDATO_TOT), NM_TIPO_DESTINACAO_VOTOS(NM_TIPO_DESTINACAO_VOTOS), 
    partido(electoralParty), NR_PARTIDO(NR_PARTIDO)
{}

Candidato::Candidato( string &NR_CANDIDATO,  string &NM_URNA_CANDIDATO,  tm &DT_NASCIMENTO,  bool &CD_GENERO,  bool &CD_SIT_TOT_TURNO,  bool &CD_SITUACAO_CANDIDATO_TOT,  bool &NM_TIPO_DESTINACAO_VOTOS, Partido *electoralParty, int votos):
    NR_CANDIDATO(NR_CANDIDATO), NM_URNA_CANDIDATO(NM_URNA_CANDIDATO), DT_NASCIMENTO(DT_NASCIMENTO), CD_GENERO(CD_GENERO), CD_SIT_TOT_TURNO(CD_SIT_TOT_TURNO), CD_SITUACAO_CANDIDATO_TOT(CD_SITUACAO_CANDIDATO_TOT), NM_TIPO_DESTINACAO_VOTOS(NM_TIPO_DESTINACAO_VOTOS), partido(electoralParty), votos(votos)
{}

void Candidato::print_debug(){
    cout << "NR_CANDIDATO: " << NR_CANDIDATO << endl;
    cout << "NM_URNA_CANDIDATO: " << NM_URNA_CANDIDATO << endl;
    cout << "DT_NASCIMENTO: " << DT_NASCIMENTO.tm_mday << "/" << DT_NASCIMENTO.tm_mon+1 << "/" << DT_NASCIMENTO.tm_year << endl;

    if(CD_GENERO == MASCULINO){
        cout << "CD_GENERO: MASCULINO" << endl;
    }else{
        cout << "CD_GENERO: FEMININO" << endl;
    }
    
    if(CD_SIT_TOT_TURNO == GANHOU){
        cout << "CD_SIT_TOT_TURNO: GANHOU" << endl;
    }else{
        cout << "CD_SIT_TOT_TURNO: PERDEU" << endl;
    }

    if (CD_SITUACAO_CANDIDATO_TOT == DEFERIDO){
        cout << "CD_SITUACAO_CANDIDATO_TOT: DEFERIDO" << endl;
    }else{
        cout << "CD_SITUACAO_CANDIDATO_TOT: INDEFERIDO" << endl;
    }

    if(NM_TIPO_DESTINACAO_VOTOS == NOMINAL){
        cout << "NM_TIPO_DESTINACAO_VOTOS: NOMINAL" << endl;
    }else{
        cout << "NM_TIPO_DESTINACAO_VOTOS: LEGENDA" << endl;
    }

    cout << "CD_CARGO: " << CD_CARGO << endl;
    cout << "votos: " << votos << endl;

    cout << "electoralParty: " << NR_PARTIDO << endl;
    cout << endl;
}


string Candidato::toString(Partido *partido){
    string s = "";

    if(!(partido->getNR_FEDERACAO().compare("-1") == 0)){
        s += "*"; // olha o python hihihi
    }    
    s += NM_URNA_CANDIDATO + " (" + NR_PARTIDO + ", " + br_format(votos) + " votos)";
    
    return s;
}

void Candidato::addVotos(int votos){
    this->votos += votos;
}

Partido* Candidato::getPartidoPtr(){
    return this->partido;
}
