#ifndef _PARTIDO_H_
#define _PARTIDO_H_

#include <string>
#include <iostream>
#include <map>
#include <iomanip>
#include <locale>
#include <sstream>
#include <fstream>
#include <stdexcept>
#include <ctime>
#include <list>
#include <algorithm>

using namespace std;

struct thousand_formater : numpunct<char>
{
    // separa milhar com pontos
    char do_thousands_sep() const { return '.';  }

    // grupos de 3 digitos
    string do_grouping() const { return "\03"; }
};

class Partido {
private:
    string NR_PARTIDO;  
    string SG_PARTIDO; 
    string NR_FEDERACAO;  
    int votosNominais = 0;
    int votosLegenda = 0;
    int candidatosVencedores = 0;
    list<string> candidatosId;   // save the id of the candidatos (MAP KEY)    
public:
    Partido();
    Partido(const string &NR_PARTIDO, const string &SG_PARTIDO, const string &NR_FEDERACAO);
    Partido(const string &NR_PARTIDO, const string &SG_PARTIDO, const string &NR_FEDERACAO, int votesNominais, int votosLegenda, int candidatosVencedores);
    Partido(const string &NR_PARTIDO, const string &SG_PARTIDO, const string &NR_FEDERACAO, int votesNominais, int votosLegenda, int candidatosVencedores, list<string> candidatesId);
    string getNR_PARTIDO() const { return NR_PARTIDO; };
    string getSG_PARTIDO() const { return SG_PARTIDO; };
    string getNR_FEDERACAO() const{ return NR_FEDERACAO; }; 
    void addCandidato(string candidateId);
    void addCandidatosVencedores();
    void addVotosNominais(int votes);
    void addVotosLegenda(int votes);
    int getVotosNominais() const { return votosNominais; };
    int getVotosLegenda() const { return votosLegenda; };
    int getVotosTotais() const { return votosNominais + votosLegenda; };
    int getTotalEleitos() const { return candidatosVencedores; };
    list<string> getCandidatosId() const { return candidatosId; };
    string toString();
};

string br_format(int i);

#endif 