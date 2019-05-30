/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cache_simulator;

/**
 *
 * @author Cliente
 */

import java.io.*;
import java.util.Random;
public class Geradormemprog {

    /**
     * @param args the command line arguments
     */
    public static void Gerador() {
          try {
                   File file = new File("arqTexto1.txt");
                   PrintWriter outFile = new PrintWriter(new FileWriter(file));
                   FileOutputStream outFileBin = new FileOutputStream("arqBinario1.dat");
		   DataOutputStream out = new DataOutputStream(outFileBin);

                   int numGerado = 0;
                   Random r = new Random();
                   for(int i=0;i<100;i++){
                        numGerado=r.nextInt(10);
                        outFile.println(numGerado);
                        out.writeInt(numGerado);
                        //System.out.println(numGerado);
                   }
                   for(int i=0;i<100;i++){
                        numGerado=r.nextInt(1000);
                        outFile.println(numGerado);
                        out.writeInt(numGerado);
                        //System.out.println(numGerado);
                   }
                   for(int i=0;i<200;i++){
                        numGerado=r.nextInt(10000);
                        outFile.println(numGerado);
                        out.writeInt(numGerado);
                        //System.out.println(numGerado);
                   }
                   for(int i=0;i<300;i++){
                        numGerado=r.nextInt(100000);
                        outFile.println(numGerado);
                        out.writeInt(numGerado);
                        //System.out.println(numGerado);
                   }
                   for(int i=0;i<300;i++){
                        numGerado=r.nextInt(1000000);
                        outFile.println(numGerado);
                        out.writeInt(numGerado);
                        //System.out.println(numGerado);
                   }
		   outFile.close();
                   outFileBin.close();
	   }
	   catch (FileNotFoundException exception){
		System.out.println("Arquivo nao encontrado.");
	   }
	   catch (IOException exception){
		System.out.println("Erro de I/O: " + exception);
	   }	
    }
}