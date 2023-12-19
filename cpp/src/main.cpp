#include <iostream>
#include <fstream>
#include <sstream>
#include <stdexcept>
#include <iomanip>
#include <locale>
#include "libs/include/bfc.h"

using namespace std;

int main(int argc, char* argv[]) {
    bool tipo_candidato = !string(argv[1]).compare("--estadual"); //estadual: true, feredal: false
    string tipo_consulta = argv[2];
    string secao_votos = argv[3];
    string data_str = argv[4];
    tm data_dt = {};
    istringstream data_dtStream(data_str);
    data_dtStream >> get_time(&data_dt, "%d/%m/%y");
    Bfc sistema_eleitoral(data_dt);

    ifstream candidata_dtsFile;
    candidata_dtsFile.exceptions(ifstream::badbit | ifstream::failbit);
    candidata_dtsFile.open(tipo_consulta);
    sistema_eleitoral.readCandidatos(candidata_dtsFile, tipo_candidato);

    ifstream votesFile;
    votesFile.exceptions(ifstream::badbit | ifstream::failbit);
    votesFile.open(secao_votos);
    sistema_eleitoral.readVotos(votesFile, tipo_candidato);


    sistema_eleitoral.total_eleitos();                           
    sistema_eleitoral.candidatos_eleitos(tipo_candidato);        
    sistema_eleitoral.candidatos_mais_votados(tipo_candidato);   
    sistema_eleitoral.teriam_sido_eleitos();                     
    sistema_eleitoral.eleitos_beneficiados();                    
    sistema_eleitoral.partidos_mais_votados(); 
    sistema_eleitoral.primeiro_e_ultimo_por_partido();
    sistema_eleitoral.eleitos_por_faixa_etaria();
    sistema_eleitoral.eleitos_por_genero();
    sistema_eleitoral.resumo_votacao();

    return 0;
}