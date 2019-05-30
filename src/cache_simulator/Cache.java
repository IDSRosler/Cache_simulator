/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cache_simulator;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author igor e jonnhy
 */
public class Cache {

    private List<Bloco>[] cache;
    private int miss_comp, miss_conflito, miss_capac, hit, qnt_acessos;

//--------------------------------------------------------------------------------------------
    /**
     * Método construtor
     * @param nsets
     * @param assoc
     * @param tag
     */
//--------------------------------------------------------------------------------------------
    public Cache(int nsets, int assoc, int tag) {
        String teste = "";
        miss_comp = 0;
        miss_conflito = 0;
        miss_capac = 0;
        hit = 0;
        qnt_acessos = 0;
        for (int i = 0; i < tag; i++) {
            teste += "*";
        }
        cache = new LinkedList[nsets];
        for (int i = 0; i < nsets; i++) {
            cache[i] = new LinkedList<>();
            for (int j = 0; j < assoc; j++) {
                cache[i].add(new Bloco());
                cache[i].get(j).set_tag(teste);
            }
        }
    }
//---------------------------------------------------------------------------------------------
    /**
     * Método que busca um dado na cache de acordo com o endereço de entrada
     * @param indice
     * @param assoc
     * @param tag
     * @param nsets
     * @param polit
     */
//---------------------------------------------------------------------------------------------
    public void busca_cache_tag(int indice, int assoc, String tag, int nsets, String polit) {
        int cont = 0;
        qnt_acessos++;
        for (int i = 0; i < assoc; i++) {
            if (!(cache[indice].get(i).get_bitVal())) {
                cache[indice].get(i).set_tag(tag);
                cache[indice].get(i).set_bitVal(true);
                miss_comp++;
                break;
            } else {
                if (cache[indice].get(i).get_tag().equals(tag)) {
                    hit++;
                    break;
                } else {
                    cont++;
                }
            }
        }
        if ((cont == assoc)) {
            if (!(miss_capac(nsets, assoc))) {
                miss_conflito++;
                select(polit, indice, assoc, tag);
            } else {
                miss_capac++;
                select(polit, indice, assoc, tag);
            }
        }
    }
//----------------------------------------------------------------------------------------------
   
    /**
     * Método que seleciona o tipo de política de substituição que será usado
     * @param polit
     * @param indice
     * @param assoc
     * @param tag
     */
//----------------------------------------------------------------------------------------------
    public void select(String polit, int indice, int assoc, String tag) {
        if (polit.equals("fifo")) {
            fifo(indice, assoc, tag);
        } else {
            rand(assoc, indice, tag);
        }
    }
//----------------------------------------------------------------------------------------------
    /**
     * Política de substituição random
     * @param assoc
     * @param indice
     * @param tag
     */
//----------------------------------------------------------------------------------------------
    public void rand(int assoc, int indice, String tag) {
        int posicao;
        Random r = new Random();
        posicao = r.nextInt(assoc);
        cache[indice].get(posicao).set_tag(tag);
    }
//----------------------------------------------------------------------------------------------
    /**
     * Política de substituição fifo
     * @param indice
     * @param assoc
     * @param tag
     */
//----------------------------------------------------------------------------------------------
    public void fifo(int indice, int assoc, String tag) {
        cache[indice].remove(0);
        cache[indice].add(new Bloco());
        cache[indice].get(assoc - 1).set_bitVal(true);
        cache[indice].get(assoc - 1).set_tag(tag);
    }
//----------------------------------------------------------------------------------------------
    public int get_miss_comp() {
        return miss_comp;
    }

    public int get_miss_comflito() {
        return miss_conflito;
    }

    public int get_miss_capac() {
        return miss_capac;
    }

    public int get_hit() {
        return hit;
    }

    public int get_qnt_acessos() {
        return qnt_acessos;
    }
//----------------------------------------------------------------------------------------------
    /**
     * Método que identivica se há miss de capacidade
     * @param nsets
     * @param assoc
     * @param flag
     * @return
     */
 //---------------------------------------------------------------------------------------------
    public boolean miss_capac(int nsets, int assoc) {
        int cont = 0;
        boolean caso = false;
            for (int i = 0; i < nsets; i++) {
                for (int j = 0; j < assoc; j++) {
                    if (cache[i].get(j).get_bitVal()) {
                        cont++;
                    }
                }
            }
            caso = (nsets * assoc) == cont;
        return caso;
    }
//----------------------------------------------------------------------------------------------
    /**
     * Método que imprime os dados da cache
     * @param nsets
     * @param assoc
     */
//----------------------------------------------------------------------------------------------
    public void print_cache(int nsets, int assoc) {
        for (int i = 0; i < nsets; i++) {
            System.out.print(i + ":\t ");
            for (int j = 0; j < assoc; j++) {
                System.out.print(cache[i].get(j).get_tag() + "\t ");
            }
            System.out.println("");
        }
    }

}
//----------------------------------------------------------------------------------------------