#ifndef _CANDIDATO_H_
#define _CANDIDATO_H_

#define FEDERAL false
#define ESTADUAL true
#define GANHOU true
#define PERDEU false
#define DEFERIDO true
#define INDEFERIDO false
#define MASCULINO false
#define FEMININO true
#define NOMINAL true
#define LEGENDA false

#include "partido.h"
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

class Partido;

class Candidato
{
private:
    string NR_CANDIDATO;                    // NR_CANDIDATO
    string NM_URNA_CANDIDATO;               // NM_URNA_CANDIDATO
    tm DT_NASCIMENTO;                       // DT_NASCIMENTO
    bool CD_GENERO;                         // CD_GENERO [0: MASCULINO; 1: FEMININO]
    bool CD_SIT_TOT_TURNO;                  // CD_SIT_TOT_TURNO [1: GANHOU; 0: PERDEU ]
    bool CD_SITUACAO_CANDIDATO_TOT;         // CD_SITUACAO_CANDIDATO_TOT [1: DEFERIDO; 0: INDEFERIDO ]
    bool NM_TIPO_DESTINACAO_VOTOS;          // NM_TIPO_DESTINACAO_VOTOS [1: NOMINAL; 0: LEGENDA]
    bool CD_CARGO;                          // [0: FEDERAL; 1: ESTADUAL]
    Partido *partido;
    string NR_PARTIDO;
    int votos = 0;
    
public:
    Candidato();
    Candidato( string &NR_CANDIDATO,  string &NM_URNA_CANDIDATO,  tm &DT_NASCIMENTO,  bool &CD_GENERO,  bool &CD_SIT_TOT_TURNO,  bool &CD_SITUACAO_CANDIDATO_TOT,  bool &NM_TIPO_DESTINACAO_VOTOS, Partido *partido, string &NR_PARTIDO);
    Candidato( string &iNR_CANDIDATOd,  string &NM_URNA_CANDIDATO,  tm &DT_NASCIMENTO,  bool &CD_GENERO,  bool &CD_SIT_TOT_TURNO,  bool &CD_SITUACAO_CANDIDATO_TOT,  bool &NM_TIPO_DESTINACAO_VOTOS, Partido *partido, int votos);
    bool getTipoVoto() const {return NM_TIPO_DESTINACAO_VOTOS;};
    bool getGenero() const {return CD_GENERO; };
    tm getDT_NASCIMENTO() const { return DT_NASCIMENTO; };
    bool getCD_SITUACAO_CANDIDATO_TOT() const {return CD_SITUACAO_CANDIDATO_TOT; };
    void addVotos(int votos);
    Partido* getPartidoPtr();
    string getNR_CANDIDATO() const { return NR_CANDIDATO; };
    string getNR_PARTIDO() const { return NR_PARTIDO; };
    bool getCD_SIT_TOT_TURNO() { return this->CD_SIT_TOT_TURNO;}
    int getVotos() const { return this->votos; };
    string getNM_URNA_CANDIDATO() const { return NM_URNA_CANDIDATO; };
    void print_debug();
    bool getCD_SIT_TOT_TURNO() const { return CD_SIT_TOT_TURNO; };
    string toString(Partido *partido);
};


#endif