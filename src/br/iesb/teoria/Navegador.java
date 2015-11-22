package br.iesb.teoria;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Class Navegador.
 */
public class Navegador {

    /** Atributo client. */
    private final HttpClient client = HttpClientBuilder.create().build();

    /**
     * Efetua login no site.
     *
     * @param url
     *            - URL de Login do site
     * @param user
     *            - usuario
     * @param password
     *            - senha
     * @return true - login ok | false - login fail
     * @throws UnsupportedEncodingException
     *             the unsupported encoding exception
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    public boolean login(final String url, final String user, final String password) throws UnsupportedEncodingException, IOException {

	/* Método POST */
	final HttpPost post = new HttpPost(url);
	boolean result = false;

	/* Configura os parâmetros do POST */
	final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("txtnumero_matricula", user));
	nameValuePairs.add(new BasicNameValuePair("txtsenha_tac", password));
	nameValuePairs.add(new BasicNameValuePair("cmdEnviar", "Entrar"));

	/*
	 * Codifica os parametros.
	 * 
	 * Antes do encoder: fulano@email.com Depois do enconder:
	 * fulano%40email.com
	 */
	post.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.ASCII));

	/* Define navegador */
	post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");

	/* Efetua o POST */
	HttpResponse response = client.execute(post);

	/*
	 * Resposta HTTP: Sempre imprimirá “HTTP/1.1 302 Object moved” (no caso
	 * da devmedia)
	 */
	// System.out.println("Login form get: " + response.getStatusLine());

	/*
	 * Consome o conteúdo retornado pelo servidor Necessário esvaziar o
	 * response antes de usar o httpClient novamente
	 */
	EntityUtils.consume(response.getEntity());

	/*
	 * Testar se o login funcionou.
	 * 
	 * Estratégia: acessar uma página que só está disponível quando se está
	 * logado Em caso de erro, o servidor irá redirecionar para a página de
	 * login A pagina de login contem uma string: "Login DevMedia" Se esta
	 * String estiver presente, significa que o login não foi efetuado com
	 * sucesso
	 * 
	 */
	openPage("http://online.iesb.br/aonline/notas_freq.asp");
	final HttpGet get = new HttpGet("http://online.iesb.br/aonline/main.asp");
	HttpResponse response_check = client.execute(get);

	/*
	 * Verifica se a String: "Login DevMedia" está presente
	 */
	if (checkSuccess(response_check)) {
	    System.out.println("Conexao Estabelecida!");
	    result = true;
	} // else {
	  // System.out.println("Login não-efetuado!\nSenha: " + password);
	  // }

	return result;
    }

    /**
     * Abre página.
     *
     * @param url
     *            - Página a acessar
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    public void openPage(final String url) throws IOException {
	final HttpGet get = new HttpGet(url);
	final HttpResponse response = client.execute(get);
	saveHTML(response);
    }

    /**
     * Busca por String que indica se o usuário está logado ou não.
     *
     * @param response
     *            the response
     * @return true - Não achou String | false - Achou String
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    private boolean checkSuccess(final HttpResponse response) throws IOException {
	final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	String line;
	boolean found = false;
	/*
	 * Deixa correr todo o laco, mesmo achando a String, para consumir o
	 * content
	 */
	while ((line = rd.readLine()) != null) {
	    if (line.contains("Seu tempo expirou.")) {
		found = true;
	    }
	}
	return !found;
    }

    /**
     * Salva a página.
     *
     * @param response
     *            the response
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    private void saveHTML(final HttpResponse response) throws IOException {
	final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	String line;
	File arquivo = new File("C:/Users/Dell/Documents/arquivo.html");
	PrintWriter writer = new PrintWriter(arquivo);
	while ((line = rd.readLine()) != null) {
	    writer.println(line);
	}
	writer.flush();
	writer.close();
    }

    /**
     * Inicia a aplicação da rede.
     *
     * @param args
     *            the args
     * @return true, se bem-sucedido
     */
    public static boolean start(List<String> args) {
	Navegador navegador = new Navegador();
	try {
	    // Tenta efetuar login na página de irrestrita
	    boolean ok = navegador.login("http://online.iesb.br/aonline/middle_logon.asp", args.get(0), args.get(1));
	    if (ok) {
		// Acessa página restrita
		navegador.openPage("http://online.iesb.br/aonline/main.asp");
		return true;
	    }
	} catch (UnsupportedEncodingException ex) {
	    ex.printStackTrace();
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
	return false;
    }
}
