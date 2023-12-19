#include "../include/bfc.h"

#include <string.h>
#include<set>

string iso_8859_1_to_utf8(string &str){
    // adaptado de: https://stackoverflow.com/a/39884120 :-)
    string strOut;
    for (string::iterator it = str.begin(); it != str.end(); ++it)
    {
      uint8_t ch = *it;
      if (ch < 0x80)
      {
        // ja esta na faixa ASCII (bit mais significativo 0), so copiar para a saida
        strOut.push_back(ch);
      }
      else
      {
        // esta na faixa ASCII-estendido, escrever 2 bytes de acordo com UTF-8
        // o primeiro byte codifica os 2 bits mais significativos do byte original (ISO-8859-1)
        strOut.push_back(0b11000000 | (ch >> 6));
        // o segundo byte codifica os 6 bits menos significativos do byte original (ISO-8859-1)
        strOut.push_back(0b10000000 | (ch & 0b00111111));
      }
    }
    return strOut;
}

Bfc::Bfc(const tm data):
    data(data)
{
}

void __pulaColuna(istringstream &linhaStream, int x){
    string aux;
    for(int i = 0; i < x; i++){
        getline(linhaStream,aux,';');
    }
}

void __remove_aspas(string &str){
    str.erase(remove(str.begin(), str.end(), '\"'), str.end());
}

void Bfc::sumCandidatosEleitos(){
    this->totalEleitos++;
}

void Bfc::addCandidato(Candidato candidato){
    if(candidatos.find(candidato.getNR_CANDIDATO()) == candidatos.end()){
        candidatos.insert({candidato.getNR_CANDIDATO(), candidato});
        candidato.getPartidoPtr()->addCandidato(candidato.getNR_CANDIDATO());
        if(partidos.find(candidato.getPartidoPtr()->getNR_PARTIDO()) == partidos.end()){
            partidos.insert({candidato.getPartidoPtr()->getNR_PARTIDO(), *(candidato.getPartidoPtr())});
        }
    }
}

void Bfc::addPartido(Partido electoralParty){
        partidos.insert({electoralParty.getNR_PARTIDO(), electoralParty});
}


Partido *Bfc::getPartidoPorId(string id){
    if(partidos.find(id) == partidos.end()){
        return NULL;
    }
    return &(partidos.at(id));
}

Candidato* Bfc::getCandidatoPorId(string id){
    if(candidatos.find(id) == candidatos.end()) return NULL;
    return &(candidatos.at(id));
}

void Bfc::readCandidatos(ifstream &inputStream, bool tipo_candidato){
    bool skip = false;
    bool ghostCandidate = false;
    Candidato candidato;
    string NR_CANDIDATO;
    string NM_URNA_ANDIDATO;
    tm DT_NASCIMENTO;
    bool CD_GENERO;
    bool CD_SIT_TOT_TURNO;
    bool CD_SITUACAO_CANDIDATO_TOT;
    bool NM_TIPO_DESTINACAO_VOTOS;
    Partido *partido; 
    string NR_PARTIDO;
    string SG_PARTIDO;
    string NR_FEDERACAO;

    string linhaNaoConvertida;
    string linha;
    string aux;
    string lixo;
    getline(inputStream,linhaNaoConvertida); // cabecalho
    int i = 1;
    int t = 0;
    try {
        while (getline(inputStream,linhaNaoConvertida)){   // LEITURA
            linha = iso_8859_1_to_utf8(linhaNaoConvertida);
            istringstream linhaStream(linha);

            __pulaColuna(linhaStream, 13);
            getline(linhaStream,aux,';');
            __remove_aspas(aux);
            ghostCandidate = (!(aux.compare("6") == 0 && tipo_candidato == FEDERAL) && !(aux.compare("7") == 0 && tipo_candidato == ESTADUAL))? true : false;
            // CD_CARGO 14

            __pulaColuna(linhaStream, 2);
            getline(linhaStream,NR_CANDIDATO,';');
            __remove_aspas(NR_CANDIDATO);        
            // NR_CANDIDATO 17

            __pulaColuna(linhaStream, 1);
            getline(linhaStream,NM_URNA_ANDIDATO,';');
            __remove_aspas(NM_URNA_ANDIDATO);
            // NM_URNA_CANDIDATO 19

            __pulaColuna(linhaStream, 8);
            getline(linhaStream,NR_PARTIDO,';');
            __remove_aspas(NR_PARTIDO);
            // NR_PARTIDO 28
        
            getline(linhaStream,SG_PARTIDO,';');
            __remove_aspas(SG_PARTIDO);
            // SG_PARTIDO 29

            __pulaColuna(linhaStream, 1);
            getline(linhaStream,NR_FEDERACAO,';');
            __remove_aspas(NR_FEDERACAO);
            // NR_FEREDACAO 31


            __pulaColuna(linhaStream, 11);
            getline(linhaStream,aux,';');
            __remove_aspas(aux);
            istringstream dateStream(aux);
            dateStream >> get_time(&DT_NASCIMENTO, "%d/%m/%y");
            // DT_NASCIMENTO 43

            __pulaColuna(linhaStream, 2);
            getline(linhaStream,aux,';');
            __remove_aspas(aux);
            CD_GENERO = (aux.compare("2") == 0)? MASCULINO : FEMININO;
            // CD_GENERO 46

            __pulaColuna(linhaStream, 10);
            getline(linhaStream,aux,';');
            __remove_aspas(aux);
            CD_SIT_TOT_TURNO = ((aux.compare("2") == 0) || (aux.compare("3") == 0))? GANHOU : PERDEU;
            // CD_SIT_TOT_TURNO 57

            __pulaColuna(linhaStream, 10);
            getline(linhaStream,aux,';');
            __remove_aspas(aux);
            if(aux.compare("Válido (legenda)") == 0){
                NM_TIPO_DESTINACAO_VOTOS = LEGENDA;
            }else if( aux.compare("Válido") == 0){
                NM_TIPO_DESTINACAO_VOTOS = NOMINAL;
            }else{
                // NM_TIPO_DESTINACAO_VOTOS = NOMINAL;
                ghostCandidate = true;
            }
            // NM_TIPO_DESTINACAO_VOTOS 68

            getline(linhaStream,aux,';');
            __remove_aspas(aux);
            CD_SITUACAO_CANDIDATO_TOT = ((aux.compare("2") == 0) || (aux.compare("16") == 0))? DEFERIDO : INDEFERIDO;
            // CD_SITUACAO_CANDIDATO_TOT 69
       

            partido = getPartidoPorId(NR_PARTIDO);
            if(partido == NULL){
                Partido p = Partido(NR_PARTIDO, SG_PARTIDO, NR_FEDERACAO);
                partido = &p;
                addPartido(*partido);
            }
            if(ghostCandidate == false){
                candidato = Candidato(NR_CANDIDATO, NM_URNA_ANDIDATO, DT_NASCIMENTO, CD_GENERO, CD_SIT_TOT_TURNO, CD_SITUACAO_CANDIDATO_TOT, NM_TIPO_DESTINACAO_VOTOS, partido, NR_PARTIDO);
                addCandidato(candidato);
  
                if(candidato.getCD_SIT_TOT_TURNO() == GANHOU){
                    Partido *p = getPartidoPorId(NR_PARTIDO);
                    p->addCandidatosVencedores();
                    sumCandidatosEleitos();
                }
            }

            ghostCandidate = false;
            i++;
        }
    } catch (const exception &e) {
        if(!inputStream.eof()){
            cerr << "erro ao ler candidatos" << endl;
            exit(98);
        }
        inputStream.close();
    }
}

void Bfc::readVotos(ifstream &inputStream, bool tipo_candidato){
    string NR_VOTAVEL;
    int votos = 0;
    bool header = true;
    bool skip = false;
    Candidato* candidato;
    Partido* electoralParty;
    int n_columns = 67;
    int debug_total_votos = 0;

    string aux;
    string lixo;
    string linhaNaoConvertida;
    string linha;
    getline(inputStream, linhaNaoConvertida); // cabecalho
    skip = false;
    int i = 1;
    try{
        while(getline(inputStream, linhaNaoConvertida)){
            linha = iso_8859_1_to_utf8(linhaNaoConvertida);
            istringstream linhaStream(linha);
            skip = false;
            i++;
            for(int i = 0; i< n_columns; i++){
                getline(linhaStream, aux, ';');
                __remove_aspas(aux);

                if(i == 17){ // tipo de candidato
                    if(!((aux.compare("6") == 0) && tipo_candidato == FEDERAL) && !((aux.compare("7") == 0) && tipo_candidato == ESTADUAL)){
                        skip = true;
                    }
                }
                if(i == 19){ // NR_VOTAVEL
                    NR_VOTAVEL = aux;
                }
                if(i == 21){ // votos
                    votos = stoi(aux);
                }
                if(i > 21) break;
            }
            
            if(skip) continue;

            candidato = getCandidatoPorId(NR_VOTAVEL);
            electoralParty = getPartidoPorId(NR_VOTAVEL);

            if(candidato != NULL){
                Partido *e = getPartidoPorId(candidato->getNR_PARTIDO());
                if(((candidato->getTipoVoto() == NOMINAL) && (candidato->getCD_SITUACAO_CANDIDATO_TOT() == DEFERIDO))){
                    candidato->addVotos(votos);
                    e->addVotosNominais(votos);
                }
                else if(candidato->getTipoVoto() == LEGENDA){
                    e->addVotosNominais(votos);
                }
            }else if(electoralParty != NULL){
                electoralParty->addVotosLegenda(votos);
            }

        }   
    } catch (const exception &e) {
        if(!inputStream.eof()){
            cerr << "erro ao ler votos" << endl;
            cerr << e.what() << endl;
            exit(99);
        }
        inputStream.close();
    }
}

void Bfc::total_eleitos(){
    cout << "Número de vagas: " << totalEleitos << endl;
}

bool __compara_votos(const Candidato& a, const Candidato& b) {
    return a.getVotos() > b.getVotos();
}

bool __compara_votos_DESC(const Candidato& a, const Candidato& b) {
    return a.getVotos() < b.getVotos();
}

vector<Candidato> __candidatos_to_vector(map<string, Candidato> canadidatos_map){
    vector<Candidato> vector;
    for(auto it = canadidatos_map.begin(); it != canadidatos_map.end(); ++it){ vector.push_back(it->second); }
    return vector;
}

void Bfc::candidatos_eleitos(bool tipo_candidatos) {
    vector<Candidato> candidatos = __candidatos_to_vector(this->candidatos);
    sort(candidatos.begin(), candidatos.end(), __compara_votos);
    int count = 0;

    if (tipo_candidatos == ESTADUAL) {
        cout << "\nDeputados estaduais eleitos:" << endl;
    } else {
        cout << "\nDeputados federais eleitos:" << endl;
    }
    for (auto it = candidatos.begin(); it != candidatos.end(); ++it) {
        if (it->getCD_SIT_TOT_TURNO() == GANHOU) {
            count++;
            Partido *e = getPartidoPorId(it->getNR_PARTIDO());
            cout << count << " - " << it->toString(e) << endl;
        }
        if (count == this->totalEleitos) break;
    }
}

void Bfc::candidatos_mais_votados(bool tipo_candidatos) {
    vector<Candidato> candidatos = __candidatos_to_vector(this->candidatos); 
    sort(candidatos.begin(), candidatos.end(), __compara_votos);
    int count = 0;

    cout << "\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):" << endl;

    for(auto it = candidatos.begin(); it != candidatos.end(); ++it){
        count++;
        Partido *e = getPartidoPorId(it->getNR_PARTIDO());
        cout << count << " - " << it->toString(e) << endl;
        if(count == this->totalEleitos) break;
    }
}

void Bfc::teriam_sido_eleitos(){
    vector<Candidato> candidatos = __candidatos_to_vector(this->candidatos); 
    sort(candidatos.begin(), candidatos.end(), __compara_votos);
    int count = 0;

    cout << "\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)" << endl;

    for(auto it = candidatos.begin(); it != candidatos.end(); ++it){
        count++;
        if(it->getCD_SIT_TOT_TURNO() == PERDEU){
            Partido *e = getPartidoPorId(it->getNR_PARTIDO());
            cout << count << " - " << it->toString(e) << endl;
        }
        if (count == this->totalEleitos) break;
    }
}

void Bfc::eleitos_beneficiados(){
    vector<Candidato> candidatos = __candidatos_to_vector(this->candidatos); 
    sort(candidatos.begin(), candidatos.end(), __compara_votos);
    int count = 0;
    int maior = 0;

    cout << "\nEleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)" << endl;

    for(auto it = candidatos.begin(); it != candidatos.end(); ++it){
        count++;
        if(count == this->totalEleitos){
            maior = it->getVotos();
            break;
        }
    }
    count = 0;
    for(auto it = candidatos.begin(); it != candidatos.end(); ++it){
        count++;
        if(it->getCD_SIT_TOT_TURNO() == GANHOU){
            if(it->getVotos() < maior){
                Partido *e = getPartidoPorId(it->getNR_PARTIDO());
                cout << count << " - " << it->toString(e) << endl;
            }
        }
    }
}

vector<Partido> __partido_map_to_vector(map<string,Partido>partido_map){
    vector<Partido> vector;
    for(auto it = partido_map.begin(); it != partido_map.end(); ++it){ vector.push_back(it->second); }
    return vector;
}

bool __compara_partidos(Partido &p1, Partido &p2){
    if (p1.getVotosTotais() > p2.getVotosTotais()) {
        return true;
    } else if (p1.getVotosTotais() < p2.getVotosTotais()) {
        return false;
    } else {
        int id1 = stoi(p1.getNR_PARTIDO());
        int id2 = stoi(p2.getNR_PARTIDO());
        return id1 < id2;
    }
}

void Bfc::partidos_mais_votados(){
    vector<Partido> partidos = __partido_map_to_vector(this->partidos);
    sort(partidos.begin(), partidos.end(), __compara_partidos);
    int count = 1;

    cout << "\nVotação dos partidos e número de candidatos eleitos:" << endl;

    for(auto it = partidos.begin(); it != partidos.end(); ++it){
        
        cout << count << " - " << it->toString() << endl;
        count++;
    }
}

void Bfc::eleitos_por_genero(){
    int masculino = 0;
    int eleitos = 0;

    for (const auto& pair : candidatos) {
            const Candidato& c = pair.second;
            if (c.getCD_SIT_TOT_TURNO() == GANHOU) {
                eleitos++;
                if(c.getGenero() == MASCULINO){
                    masculino++;
                }
            }
        }

        double masc_percentual = (static_cast<double>(masculino) * 100) / eleitos;
        double fem_percentual = 100 - masc_percentual;

        cout << "\nEleitos, por gênero:" << endl;
        
        cout << "Feminino: " << (eleitos - masculino) << " (";
        printaPorcentagem((eleitos - masculino), masculino);
        cout << "%)" << endl;

        cout << "Masculino: " << masculino << " ("; 
        printaPorcentagem(masculino, (eleitos - masculino));
        cout << "%)" << endl;
}

void printaPorcentagem(int a, int b){
    double x1 = (double)a, x2 = (double)b;
    double aux = x1 + x2;
    double porcentagem = (x1/aux)*100;
    int inteiro1 = porcentagem;
    double virgula = porcentagem - inteiro1;
    virgula = virgula*100;
    int virgulaInteiro = virgula;
    if(virgulaInteiro == 0)
        printf("%d,00", inteiro1);
    else
        printf("%d,%d", inteiro1, virgulaInteiro);
}

void Bfc::resumo_votacao(){
    int nominais = 0;
    int legenda = 0;
    vector<Partido> partidos = __partido_map_to_vector(this->partidos);
    for (const Partido& p : partidos) {
        nominais += p.getVotosNominais();
        legenda += p.getVotosLegenda();
    }
    
    cout << "\nTotal de votos válidos:    " <<  br_format(legenda + nominais) << endl;
    cout << "Total de votos nominais:   " << br_format(nominais) << " (";
    printaPorcentagem(nominais, legenda);
    cout << "%)" << endl;
    
    cout << "Total de votos de legenda: " << br_format(legenda) << " (";
    printaPorcentagem(legenda, nominais);
    cout << "%)" << endl;
    cout << "\n";
}

void Bfc::eleitos_por_faixa_etaria(){
        int idade30 = 0;
        int idade40 = 0;
        int idade50 = 0;
        int idade60 = 0;
        int idade60mais = 0;

        vector<Candidato> candidatos = __candidatos_to_vector(this->candidatos);

        for (const Candidato& c : candidatos) {
            if (c.getCD_SIT_TOT_TURNO() == PERDEU) continue;

            int idade = 0;
            
            tm birthDate = c.getDT_NASCIMENTO();
            idade = data.tm_year - birthDate.tm_year - 1;
            idade += (data.tm_mon > birthDate.tm_mon ||  (data.tm_mon == birthDate.tm_mon && data.tm_mday >= birthDate.tm_mday));

            if (idade < 30)
                idade30++;
            else if (30 <= idade && idade < 40)
                idade40++;
            else if (40 <= idade && idade < 50)
                idade50++;
            else if (50 <= idade && idade < 60)
                idade60++;
            else if (60 <= idade)
                idade60mais++;
        }

        cout << "\nEleitos, por faixa etária (na data da eleição):" << std::endl;
        cout << "      Idade < 30: " << idade30 << " (";
        printaPorcentagem(idade30, (totalEleitos-idade30));
        cout << "%)" << endl;
        
        cout << "30 <= Idade < 40: " << idade40 << " ("; 
        printaPorcentagem(idade40, (totalEleitos-idade40));
        cout << "%)" << endl;

        cout << "40 <= Idade < 50: " << idade50 << " ("; 
        printaPorcentagem(idade50, (totalEleitos-idade50));
        cout << "%)" << endl;
        
        cout << "50 <= Idade < 60: " << idade60 << " ("; 
        printaPorcentagem(idade60, (totalEleitos-idade60));
        cout << "%)" << endl;
        
        cout << "60 <= Idade     : " << idade60mais << " (";
        printaPorcentagem(idade60mais, (totalEleitos-idade60mais));
        cout << "%)" << endl;
}

void Bfc::primeiro_e_ultimo_por_partido(){
    map<string, Partido> partidos_map = this->partidos;
    map<string, Candidato> candidatos_map = this->candidatos;
    int count = 0;

    cout << "\nPrimeiro e último colocados de cada partido:" << endl;

    vector<Candidato> candidatos_map_values;
    for(auto &pair : candidatos_map){
        Candidato &c = pair.second;
        candidatos_map_values.push_back(c);
    }
    sort(candidatos_map_values.begin(), candidatos_map_values.end(), __compara_votos_DESC);

    set<string>partidos_id;
    for(Candidato &c : candidatos_map_values){  partidos_id.insert(c.getNR_PARTIDO());  }
    
    for(string id: partidos_id){
        // partidos_id.remove(id);
        if(getPartidoPorId(id)->getVotosNominais() == 0) continue;

        Partido *partido_ptr = getPartidoPorId(id);
        Partido partido = *partido_ptr;
        list<string> candidatos_id = partido.getCandidatosId();
        vector<Candidato> candidatos_vec;
            
        for(auto &id : candidatos_id){
            Candidato *c = getCandidatoPorId(id);
            candidatos_vec.push_back(*c);
        }
        sort(candidatos_vec.begin(), candidatos_vec.end(), __compara_votos);
        if(candidatos_vec.size() == 0) continue;

        int i = 0;
        int size = candidatos_vec.size();
        Candidato primeiro = candidatos_vec[0];
        Candidato ultimo = candidatos_vec[size-1];     

        do{
            i++;
            if(i == size){
                ultimo = primeiro;
                break;
            }
            ultimo = candidatos_vec[size-1];
        }while(ultimo.getCD_SITUACAO_CANDIDATO_TOT() != DEFERIDO);
        
        
        string votos_primeiro = "votos", votos_ultimo = "votos";
        if(primeiro.getVotos() < 2){
            votos_primeiro = "voto";
        }
        if(ultimo.getVotos() < 2){
            votos_ultimo = "voto";
        }
        count++;
        
        Partido *e = getPartidoPorId(primeiro.getNR_PARTIDO());
        cout << count << " - " << e->getSG_PARTIDO() << " - " << ultimo.getNR_PARTIDO() \
        << ", " << primeiro.getNM_URNA_CANDIDATO() << " (" << primeiro.getNR_CANDIDATO() << ", " <<primeiro.getVotos() << " " << votos_primeiro << ")" \
        << " / " << ultimo.getNM_URNA_CANDIDATO() << " (" << ultimo.getNR_CANDIDATO() << ", " << ultimo.getVotos() << " " << votos_ultimo << ")" << endl;
    }
}
