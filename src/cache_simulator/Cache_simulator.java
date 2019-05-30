/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cache_simulator;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.exit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Igor e Jonnhy
 */
public class Cache_simulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//----------------------------------------------------------------------------------------------------------------------------------        
//                     Gerador de endereços pré definidos
//----------------------------------------------------------------------------------------------------------------------------------
        //Geradormemprog.Gerador();
//----------------------------------------------------------------------------------------------------------------------------------
//                          Declaração de variáveis
//----------------------------------------------------------------------------------------------------------------------------------
        String file_name = "", tag = "", offset = "", politc = "random";
        String[] gerador = new String[3];
        String endereco = "";
        int nsets = 256, bsize = 4, assoc = 1;
        int bits_offset = 0, bits_indice = 0, bits_tag = 0, tamanho = 0;
        int indice, total_misses = 0;
        float miss_rate = (float) 0.0;
//----------------------------------------------------------------------------------------------------------------------------------
//        Manipulação das exceções passadas por parâmetros no terminal
//----------------------------------------------------------------------------------------------------------------------------------
        if (args.length == 2) {
            if (args[0].split(":").length == 4) {
                if (!(args[0].split(":")[0].equals("0"))) {
                    nsets = Integer.parseInt(args[0].split(":")[0]);
                }
                if (!(args[0].split(":")[1].equals("0"))) {
                    bsize = Integer.parseInt(args[0].split(":")[1]);
                }
                if (!(args[0].split(":")[2].equals("0"))) {
                    assoc = Integer.parseInt(args[0].split(":")[2]);
                }
                if (!(args[0].split(":")[3].equals("0"))) {
                    politc = args[0].split(":")[3];
                }
            } else if (args[0].split(":").length == 3 || args[0].split(":").length == 2 || args[0].split(":").length == 1) {
                System.out.println("Parâmetros inválidos.");
                System.out.println("Insira o seguinte formato: <nsets>:<bsize>:<assoc>:<politc>  <nome do arquivo>");
                exit(1);
            }
            file_name = args[1];
        } else if (args.length == 1) {
            if (args[0].split(":").length == 4) {
                if (!(args[0].split(":")[0].equals("0"))) {
                    nsets = Integer.parseInt(args[0].split(":")[0]);
                }
                if (!(args[0].split(":")[1].equals("0"))) {
                    bsize = Integer.parseInt(args[0].split(":")[1]);
                }
                if (!(args[0].split(":")[2].equals("0"))) {
                    assoc = Integer.parseInt(args[0].split(":")[2]);
                }
                if (!(args[0].split(":")[3].equals("0"))) {
                    politc = args[0].split(":")[3];
                }
            } else {
                file_name = args[0];
            }
        }
//--------------------------------------------------------------------------------------------------------------------------------
//                      Setagem dos parâmetros da cache
//--------------------------------------------------------------------------------------------------------------------------------
        bits_offset = (int) (Math.floor(Math.log(bsize) / Math.log(2)));
        bits_indice = (int) (Math.floor(Math.log(nsets) / Math.log(2)));
        bits_tag = 32 - (bits_indice + bits_offset);
        tamanho = (nsets * bsize * assoc);

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("                                        PARÂMETROS DA CACHE                                         ");
        System.out.println("----------------------------------------------------------------------------------------------------");

        if (tamanho >= 1024) {
            tamanho = tamanho / 1024;
            System.out.println("Tamanho:\t\t\t" + tamanho + " MB");
        } else {
            System.out.println("Tamanho:\t\t\t" + tamanho + " B");
        }

        System.out.println("Bits de tag:\t\t\t" + bits_tag);
        System.out.println("Bits de indice:\t\t\t" + bits_indice);
        System.out.println("Bits de offset:\t\t\t" + bits_offset);
        System.out.println("Quantidade de conjuntos:\t" + nsets);
        System.out.println("Tamanho do bloco:\t\t" + bsize);
        System.out.println("Grau de associatividade:\t" + assoc);
        System.out.println("Política de substituição:\t" + politc);
        System.out.println("----------------------------------------------------------------------------------------------------");
//--------------------------------------------------------------------------------------------------------------------------------
//               Instância da Cache, leitura do arquivo e manipulação dos endereços
//--------------------------------------------------------------------------------------------------------------------------------
        Cache cache = new Cache(nsets, assoc, bits_tag);

        try {
            InputStream arq;
            arq = new FileInputStream(file_name);
            DataInputStream input = new DataInputStream(arq);

            while (input.available() > 0) {
                endereco = Integer.toBinaryString(input.readInt());
                if (endereco.length() < 32) {
                    endereco = Cache_simulator.preencher(endereco);
                }
                gerador = Cache_simulator.fat_end(endereco, bits_tag, bits_indice, bits_offset);
                tag = gerador[0];
                indice = Integer.parseInt(gerador[1], 2);
                offset = gerador[2];
                cache.busca_cache_tag(indice, assoc, tag, nsets, politc);
            }
            
            total_misses = cache.get_miss_comp() + cache.get_miss_comflito() + cache.get_miss_capac();
            miss_rate = ((float) total_misses/cache.get_qnt_acessos())*100;
            
            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("                                         SIMULAÇÃO DA CACHE                                         ");
            System.out.println("----------------------------------------------------------------------------------------------------");
            cache.print_cache(nsets, assoc);
            System.out.println("----------------------------------------------------------------------------------------------------");

            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("                                       RESULTADOS DA SIMULAÇÃO                                      ");
            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("Número de acessos:\t" + cache.get_qnt_acessos());
            System.out.println("Hits:\t\t\t" + cache.get_hit());
            System.out.println("Misses Compulsorios:\t" + cache.get_miss_comp());
            System.out.println("Misses Conflito:\t" + cache.get_miss_comflito());
            System.out.println("Misses Capacidade:\t" + cache.get_miss_capac());
            System.out.println("Total de Misses:\t" + total_misses);
            System.out.println("Miss rate:\t\t" + miss_rate + "%");
            System.out.println("----------------------------------------------------------------------------------------------------");

            arq.close();
            input.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Erro: Arquivo não encontrado, impossível fazer a leitura.");
        } catch (IOException ex) {
            Logger.getLogger(Cache_simulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Método que transforma uma string binária em 32 bists
     * @param endereco
     * @return
     */
//------------------------------------------------------------------------------------------------------------------------------------    
    public static String preencher(String endereco) {
        String preencher = "";
        int tamanho = 32 - endereco.length();
        for (int i = 0; i < tamanho; i++) {
            preencher += "0";
        }
        endereco = preencher + endereco;
        return endereco;
    }
//------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Método que separa os bits de tag, índice e offset
     * @param end
     * @param tag
     * @param indice
     * @param offset
     * @return
     */
//------------------------------------------------------------------------------------------------------------------------------------
    public static String[] fat_end(String end, int tag, int indice, int offset) {
        String[] endereco = new String[3];

        endereco[0] = end.substring(0, tag);
        if (indice == 0) {
            endereco[1] = "00";
        } else {
            endereco[1] = end.substring(tag, (tag + indice));
        }
        endereco[2] = end.substring((tag + indice), (tag + indice + offset));

        return endereco;
    }
}
//-----------------------------------------------------------------------------------------------------------------------------------