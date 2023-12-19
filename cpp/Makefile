############
# Exemplo de makefile
# Prof. João Paulo A. Almeida
# Programação OO
#
# A princípio, você não precisa alterar nada, mas aqui assume-se que o diretório atual
# é o diretório onde estão os códigos fonte (.cpp).
#
# nome do compilador
# CPP = g++

# # opções de compilação
# CFLAGS = -Wall -g
# CPPFLAGS = -std=c++17

# # define lista de arquivos-fonte, assumindo que estão no diretório atual
# FONTES = $(wildcard *.cpp)

# # define lista dos arquivos-objeto usando nomes da lista de arquivos-fonte
# OBJETOS = $(FONTES:.cpp=.o)

# # nome do arquivo executável
# EXECUTAVEL = deputados

# ############ alvos
# #
# # use @ antes de todos os comandos, pois é necessário no script de teste
# #
# # alvo principal é o executável
# all: $(EXECUTAVEL)

# # para linkar o executável, precisamos dos arquivos-objetos
# $(EXECUTAVEL): $(OBJETOS)
# 	@$(CPP) -o $@ $^

# # alvo para cada arquivo-objeto depende do código fonte
# # (observação, aqui não estamos capturando as dependências de arquivos .h)
# %.o: %.cpp
# 	@$(CPP) $(CPPFLAGS) -c $(CFLAGS) $^

# # comandos para execução
# runfederal: $(EXECUTAVEL)
# 	@./$(EXECUTAVEL) --federal candidatos.csv votacao.csv 02/10/2022
# runestadual: $(EXECUTAVEL)
# 	@./$(EXECUTAVEL) --estadual candidatos.csv votacao.csv 02/10/2022

# # comando para limpeza
# clean:
# 	@rm *.o $(EXECUTAVEL) *.csv *.txt


# Isso aqui eh do lolo e do zuzu
all: deputados

deputados:
	@g++ -std=c++17 -g -o deputados src/main.cpp src/libs/src/*.cpp



#compilacao como pedido
#runfederal: deputados
#	@./deputados --federal candidatos.csv votacao.csv 02/10/2022

#runestadual: deputados
#	@./deputados --estadual candidatos.csv votacao.csv 02/10/2022


#testes
# inputs/votacao_secao_2022_ES/votacao_secao_2022_ES.csv
e: deputados 
	@./deputados --estadual inputs/consulta_cand_2022/consulta_cand_2022_ES.csv inputs/votacao_secao_2022_ES/votacao_secao_2022_ES.csv 02/10/2022

val: deputados
	@valgrind --leak-check=full --show-leak-kinds=all --track-origins=yes ./deputados --estadual inputs/consulta_cand_2022/consulta_cand_2022_ES.csv inputs/votacao_secao_2022_ES/votacao_secao_2022_ES.csv 02/10/2022

make c:
	@rm deputados
