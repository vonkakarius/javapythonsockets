import java.net.*;
import java.io.*;

class Client
{
    // initialize socket and input output streams
    private Socket socket = null;
    private BufferedReader in = null;
    private DataOutputStream out = null;

    // Construtor
    public Client(String address, int port)
    {
        // Estabelecer conexão
        try
        {
            socket = new Socket(address, port);
            System.out.println("(#) Conectado ao servidor.\n");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    // Comunicador
    public String palpitar(String jogadaJava)
    {
        try
        {
            out.writeUTF(jogadaJava);
            String jogadaPython;

            try
            {
                jogadaPython = in.readLine();
                return jogadaPython;
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return "";
    }

    // Destrutor
    protected void dispose()
    {
        try
        {
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
}

public class Main
{
    public static void main(String args[])
    {
        // Setup
        Client client = new Client("127.0.0.1", 9595);
        String[] jogadas = new String[]{"Pedra", "Tesoura", "Spock", "Lagarto", "Papel"};
        String jogadaJava, jogadaPython;
        int indice;

        // Jogadas
        for (int i = 1; i <= 15; i++)
        {
            indice = (int) (Math.random() * 5);
            jogadaJava = jogadas[indice];
            jogadaPython = client.palpitar(jogadaJava);
            mostrarResultado(jogadaJava, jogadaPython, i);
        }

        client.dispose();
    }

    public static void mostrarResultado(String jogJ, String jogP, int rodada)
    {
        String resultado;

        // Calcula campeão
        if (jogJ.equals(jogP))
            resultado = "EMPATOU!";
        else if ((jogP == "Pedra" && (jogJ == "Spock" || jogJ == "Papel")) ||
            (jogP == "Tesoura" && (jogJ == "Spock" || jogJ == "Pedra")) ||
            (jogP == "Spock" && (jogJ == "Lagarto" || jogJ == "Papel")) ||
            (jogP == "Lagarto" && (jogJ == "Pedra" || jogJ == "Tesoura")) ||
            (jogP == "Papel" && (jogJ == "Tesoura" || jogJ == "Lagarto")))
            resultado = "VOCÊ VENCEU!";
        else
            resultado = "VOCÊ PERDEU!";

        // Exibe
        System.out.println("+----------------------------------+");
        System.out.printf("              RODADA %d             \n", rodada);
        System.out.println("+----------------------------------+");
        System.out.printf(" > Você: %s\n", jogJ);
        System.out.printf(" > Adversário: %s\n", jogP);
        System.out.printf("+----------------------------------+\n");
        System.out.printf(" > %s\n", resultado);
        System.out.printf("+----------------------------------+\n\n");
    }
}