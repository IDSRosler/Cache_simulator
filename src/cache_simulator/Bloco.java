/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cache_simulator;


/**
 *
 * @author Igor e Jonnhy
 */
public class Bloco {

    private boolean bit_val;
    private String tag;

//----------------------------------------------------------
    /**
     * Método contrutor 
     */
//----------------------------------------------------------
    public Bloco() {
        this.tag = "";
        this.bit_val = false;
    }
//----------------------------------------------------------
    /**
     * Método que verifica o valor do bit de validade
     * @return
     */
//----------------------------------------------------------
    public boolean get_bitVal() {
        return bit_val;
    }
//----------------------------------------------------------
    /**
     * Método que seta o valor do bit de validade
     * @param bitVal
     */
//----------------------------------------------------------  
    public void set_bitVal(boolean bitVal) {
        this.bit_val = bitVal;
    }
//----------------------------------------------------------
    /**
     * Método que devolve o valor da tag
     * @return
     */
//----------------------------------------------------------
    public String get_tag() {
        return tag;
    }
//----------------------------------------------------------
    /**
     * Método que seta o valor da tag
     * @param tag
     */
//---------------------------------------------------------- 
    public void set_tag(String tag) {
        this.tag = tag;
    }
}
//----------------------------------------------------------