package br.iesb.teoria;

import java.util.ArrayList;
import java.util.List;

public class ForcaBruta {

    private static long initialTime;
    private static long interval;
    private static List<Character> senha;

    public static void main(String[] args) {

	senha = new ArrayList<Character>();

	initialTime = System.currentTimeMillis();
	for (int i = 0; i < 2; i++) {
	    senha.add((char) (Math.random() * 127 + 32));
	}
	System.out.println("Senha: " + senha);
	crack(senha);
	System.out.println("Senha: " + senha);
	interval = System.currentTimeMillis() - initialTime;
	System.out.println("Tempo(seg): " + interval / 1000);

    }

    private static void crack(List<Character> senha) {
	List<Character> bam = new ArrayList<Character>();

	Integer i = 0;
	char temp = 0;

	for (int j = 0; j < senha.size(); j++) {
	    bam.add((char) 32);
	}
	System.out.println("Bam: " + bam);
	
	while (!senha.containsAll(bam)) {
	    temp = Character.valueOf(bam.get(i));
	    temp = (char) (temp + 1);
	    bam.set(i, temp);
	    System.out.println("Bam: " + bam + " Senha: " + senha);
	    if (bam.get(i).equals(senha.get(i))) {
		bam.set(++i, (char) 32);
		temp = bam.get(i);
		temp = (char) (temp + 1);
		bam.set(i, temp);
	    }
//	    while ((bam.get(i) == (char) 255) && (i < senha.size())) {
//		bam.set(i++, (char) 32);
//		System.out.println(i);
//		temp = bam.get(i);
//		temp = (char) (temp + 1);
//		bam.set(i, temp);
//	    }
	}

    }

}
