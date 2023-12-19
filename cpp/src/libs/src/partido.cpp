#include "../include/partido.h"

string br_format(int i){
    /**
     * @brief Entao ce toma: passa um inteiro e recebe uma string com o numero formatado BR
     * 
     */
    
    stringstream ss;
    ss.imbue(locale(cout.getloc(), new thousand_formater));
    ss << i;
    string resultado = ss.str();
    // cout << resultado << endl;
    return resultado;
}

Partido::Partido()
{
}

Partido::Partido(const string &NR_PARTIDO, const string &SG_PARTIDO, const string &NR_FEDERACAO):
    NR_PARTIDO(NR_PARTIDO), SG_PARTIDO(SG_PARTIDO), NR_FEDERACAO(NR_FEDERACAO)
{}
Partido::Partido(const string &NR_PARTIDO, const string &SG_PARTIDO, const string &NR_FEDERACAO, int votosNominais, int votosLegenda, int candidatosVencedores):
    NR_PARTIDO(NR_PARTIDO), SG_PARTIDO(SG_PARTIDO), NR_FEDERACAO(NR_FEDERACAO), votosNominais(votosNominais), votosLegenda(votosLegenda), candidatosVencedores(candidatosVencedores)
{}
Partido::Partido(const string &NR_PARTIDO, const string &SG_PARTIDO, const string &NR_FEDERACAO, int votosNominais, int votosLegenda, int candidatosVencedores, list<string> candidatosId):
    NR_PARTIDO(NR_PARTIDO), SG_PARTIDO(SG_PARTIDO), NR_FEDERACAO(NR_FEDERACAO), votosNominais(votosNominais), votosLegenda(votosLegenda), candidatosVencedores(candidatosVencedores), candidatosId(candidatosId)
{}

void Partido::addCandidato(string candidateId){
    // n entedi oq ta fazendo direito no if
    if(find(candidatosId.begin(), candidatosId.end(), candidateId) == candidatosId.end())
        candidatosId.push_back(candidateId);
}

void Partido::addCandidatosVencedores(){
    candidatosVencedores++;
}

void Partido::addVotosNominais(int votos){
    this->votosNominais += votos;
}

void Partido::addVotosLegenda(int votos){
    this->votosLegenda += votos;
}

string Partido::toString() {
    ostringstream stream;

    string voto = ((getVotosNominais() + getVotosLegenda()) < 2) ? " voto" : " votos";
    string nominal = (getVotosNominais() < 2) ? " nominal e " : " nominais e ";

    stream << getSG_PARTIDO() << " - " << getNR_PARTIDO() << ", " << br_format(getVotosNominais() + getVotosLegenda()) << voto
            << " (" << br_format(getVotosNominais()) << nominal << br_format(getVotosLegenda()) << " de legenda), " << getTotalEleitos();

    stream << ((getTotalEleitos() > 1) ? " candidatos eleitos" : " candidato eleito");

    return stream.str();
}
