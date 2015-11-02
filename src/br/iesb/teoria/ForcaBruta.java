package br.iesb.teoria;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ForcaBruta {

    private static final int N = 6;
    private static double initialTime;
    private static double interval;
    private static List<Character> senha;

    public static void main(String[] args) throws Exception {
	senha = new ArrayList<Character>();
	for (int i = 0; i < N; i++) {
	    senha.add((char) (Math.random() * 223 + 32));
	}
	System.out.println("Senha: " + senha);
	initialTime = System.currentTimeMillis();
	crack(senha);
	interval = System.currentTimeMillis() - initialTime;
	System.out.println("Tempo(seg): " + interval / 1000);
	FileWriter arq = new FileWriter(System.getProperty("user.dir") + "/src/forcabruta/resultados.txt", true);
	PrintWriter gravarArq = new PrintWriter(arq);
	gravarArq.println("Senha: " + senha);
	gravarArq.println("Tempo: " + interval / 1000);
	arq.close();
	try {
	    Document doc = Jsoup.connect("http://online.iesb.br/aonline/logon.asp").get();
	    System.out.println("title: " + doc.title());
	    Elements inputsText = doc.select("input");
	    inputsText.select("[name=txtnumero_matricula]").attr("value", "1212082005");
	    inputsText.select("[name=txtsenha_tac]").attr("value", "augusto");
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    private static void crack(List<Character> senha) {
	List<Character> bam = new ArrayList<Character>();
	Integer i;
	senha.forEach(s -> bam.add((char) 32));
	while (!bam.equals(senha)) {
	    i = 0;
	    nextChar(bam, i); // bam[i]++
	    while ((bam.get(i) == (char) 255) && (i < senha.size())) {
		bam.set(i++, (char) 32);
		nextChar(bam, i); // bam[i]++
	    }
	}
	System.out.println("crack() → Bam: " + bam + " Senha: " + senha);
    }

    private static void nextChar(List<Character> bam, Integer i) {
	char temp = 0;
	temp = Character.valueOf(bam.get(i));
	temp = (char) (temp + 1);
	bam.set(i, temp);
    }

}
