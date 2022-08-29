/*
Aluno: Bruno Yudi Mino Okada

Sua  tarefa  será  construir  uma  estrutura  de  dados,  baseada  em  listas  encadeadas  sem  usar
qualquer  estrutura  de  dados  já  disponíveis  nas  linguagens  de  programação.  Lembre-se  que  para  tal
você precisará definir uma aquitetura de memória e, no mínimo, os métodos de inclusão, exclusão e
busca de dados nesta estrutura.
Você deverá criar uma lista com, no mínimo, 200.000 itens armazenados. Estes itens devem ser
números inteiros randomicamente gerados (use o twister de Mersenne para gerar os inteiros), em um
range de 1 a 1.000.000. Seu objetivo será medir o tempo para criar a lista, o tempo para reverter esta
lista, o tempo necessário para encontrar o item que está no meio da lista e o tempo necessário para
apagar todos os itens da lista, liberando a memória.
Caso o número de itens na sua lista seja impar, o item do meio será o item que estiver no lado
inferior da lista.
A  saída  deste  programa  será:  o  item  que  está  no  meio  da  lista  e  os  tempos  que  devem  ser
medidos. A formatação da saída fica a seu critério.

Fonte do código do Twister de Mersenne utilizado:
SEAN LUKE. George Mason University. Mersenne Twister Java.
Disponível em: https://cs.gmu.edu/~sean/research/mersenne/MersenneTwister.java. Acesso em: 27 de ago. de 2022.
 */

import static java.lang.Math.abs;

public class ListaEncadeada {
    Referencia head;

    static class Referencia {
        int valor;
        Referencia proximo;

        Referencia(int x){
            valor = x;
            proximo = null;
        }
    }


    //Método para adicionar elementos na lista encadeada

    public static ListaEncadeada adicionar(ListaEncadeada listaEncadeada, int numero) {
        Referencia elemento = new Referencia(numero);
        elemento.proximo = null;

        if (listaEncadeada.head == null){
            listaEncadeada.head = elemento;
        }
        else{
            Referencia last = listaEncadeada.head;
            while (last.proximo != null){
                last = last.proximo;
            }
            last.proximo = elemento;
        }
        return listaEncadeada;
    }

    //Método para remover elementos na lista encadeada
    public static ListaEncadeada remover(ListaEncadeada listaEncadeada, int indice){
        Referencia ref = listaEncadeada.head, anterior = null;

        if(indice == 0 && ref != null){
            listaEncadeada.head = ref.proximo;
            return listaEncadeada;
        }
        int contador = 0;
        while(ref != null){
            if (contador == indice){
                anterior.proximo = ref.proximo;
                break;
            }
            else {
                anterior = ref;
                ref = ref.proximo;
                contador++;
            }
        }
        if (ref == null){
            System.out.println("Elemento nao existe");
        }
        return listaEncadeada;
    }

    //Método para reverter a ordem dos elementos da lista encadeada
    public static Referencia reverterLista(Referencia referencia){
        Referencia atual = referencia;
        Referencia anterior = null;
        Referencia proximo = null;
        while (atual != null){
            proximo = atual.proximo;
            atual.proximo = anterior;
            anterior = atual;
            atual = proximo;
        }
        referencia = anterior;
        return referencia;
    }

    //Método para imprimir os elementos lista encadeada
    //A saida diz que e o elemento do meio, mas pode ser qualquer outro
    //Só está dizendo que e o do meio por causa do enunciado
    public static void imprimirElemento(ListaEncadeada listaEncadeada, int indice){
        Referencia ref = listaEncadeada.head, anterior = null;
        int elemento;

        if(indice == 0 && ref != null){
            elemento = ref.valor;
            System.out.println("O elemento do meio da lista e: " + elemento);
        }
        int contador = 0;
        while(ref != null){
            if (contador == indice){
                elemento = ref.valor;
                System.out.println("O elemento do meio da lista e: " + elemento);
                break;
            }
            else {
                anterior = ref;
                ref = ref.proximo;
                contador++;
            }
        }
        if (ref == null){
            System.out.println("Elemento nao existe");
        }
    }

    public static void main(String[] args) {
        ListaEncadeada lista = new ListaEncadeada();
        final long seed = 4357L;
        int random;
        int aleat;
        MersenneTwister mersenneTwister = new MersenneTwister(seed);

        //Método para adicionar o elemento aleatorio gerado pelo Twister de Mersenne e calcular o tempo para esta acao
        long tempoInicioInsercao = System.currentTimeMillis();
        for (int i=1;i<=200000;i++){
            aleat = mersenneTwister.nextInt();
            //transforma os valores negativos gerados em positivos
            random = abs(aleat)/10000;
            if (random > 1 && random < 1000000){
                lista = ListaEncadeada.adicionar(lista, random);
            }
            else{
                i--;         //Se o numero gerado não estiver entre 1 e 1.000.000, não vai contar para os 200.000 elementos
            }
        }
        long tempoFinalInsercao = System.currentTimeMillis();
        long tempoInsercao = tempoFinalInsercao - tempoInicioInsercao;
        System.out.println("Tempo de criacao da lista: " + tempoInsercao + "ms");

        //Método para buscar o elemento do meio da lista e calcular o tempo
        long tempoInicioBusca = System.currentTimeMillis();
        ListaEncadeada.imprimirElemento(lista,99999);
        long tempoFinalBusca = System.currentTimeMillis();
        long tempoBusca = tempoFinalBusca - tempoInicioBusca;
        System.out.println("Tempo de busca do elemento do meio: " + tempoBusca + "ms");

        //Método para reverter a ordem dos elementos da lista e calcular o tempo
        long tempoInicioReversao = System.currentTimeMillis();
        ListaEncadeada.reverterLista(lista.head);
        long tempoFinalReversao = System.currentTimeMillis();
        long tempoReversao = tempoFinalReversao - tempoInicioReversao;
        System.out.println("Tempo de reversao da lista: " + tempoReversao + "ms");

        //Método para remover todos os elementos da lista e calcular o tempo
        long tempoInicioRemocao = System.currentTimeMillis();
        while(lista.head != null){
            ListaEncadeada.remover(lista, 0);
        }
        long tempoFinalRemocao = System.currentTimeMillis();
        long tempoRemocao = tempoFinalRemocao - tempoInicioRemocao;
        System.out.println("Tempo para apagar a lista: " + tempoRemocao + "ms");
    }
}
