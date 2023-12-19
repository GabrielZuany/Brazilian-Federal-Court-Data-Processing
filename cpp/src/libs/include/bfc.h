#ifndef _BFC_H_
#define _BFC_H_

#include "candidato.h"
#include <string>
#include <iostream>
#include <vector>
#include <map>
#include <algorithm>
#include <iomanip>
#include <locale>
#include <sstream>
#include <fstream>
#include <stdexcept>
#include <ctime>

using namespace std;

class Bfc{
private:   
    map<string,Candidato> candidatos;
    map<string,Partido> partidos;
    int totalEleitos = 0;
    tm data;
public:
    Bfc(const tm data);
    void readCandidatos(ifstream &arquivoCandidatos, bool tipoCandidato);
    void readVotos(ifstream &arquivoVotos, bool tipoCandidato);
    Partido *getPartidoPorId(string id);
    void addPartido(Partido partido);
    void addCandidato(Candidato candidato);
    void sumCandidatosEleitos();
    Candidato* getCandidatoPorId(string id);
    void total_eleitos();
    void candidatos_eleitos(bool tipo_candidatos);
    void candidatos_mais_votados(bool tipo_candidatos);
    void teriam_sido_eleitos();
    void eleitos_beneficiados();
    void partidos_mais_votados();
    void primeiro_e_ultimo_partido();
    void eleitos_por_genero();
    void resumo_votacao();
    void eleitos_por_faixa_etaria();
    void primeiro_e_ultimo_por_partido();
};

void printaPorcentagem(int a, int b);

#endif // _BFC_H_ | Brasilian Federal Court Data Processing