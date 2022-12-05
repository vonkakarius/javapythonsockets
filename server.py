import socket
from random import choice

# Regras
jogadas = ('Pedra', 'Tesoura', 'Spock', 'Lagarto', 'Papel')
vence = {
    'Pedra': ['Spock', 'Papel'],
    'Tesoura': ['Spock', 'Pedra'],
    'Spock': ['Lagarto', 'Papel'],
    'Lagarto': ['Pedra', 'Tesoura'],
    'Papel': ['Tesoura', 'Lagarto']
}

# Resultado
def mostrar_resultado(jogada_python, jogada_java, rodada):
    # Cálculo
    if jogada_python in vence[jogada_java]:
        resultado = 'VOCÊ VENCEU!'
    elif jogada_java in vence[jogada_python]:
        resultado = 'VOCÊ PERDEU!'
    else:
        resultado = 'EMPATOU!'

    # Exibição
    print('+----------------------------------+')
    print(f'|             RODADA {rodada:<2}            |')
    print('+----------------------------------+')
    print(f'| Você: {jogada_python:27}| ')
    print(f'| Adversário: {jogada_java:21}| ')
    print('+----------------------------------+')
    print(f'| {resultado:^32} |')
    print('+----------------------------------+\n')

# Servidor
def run_server():
    # Socket
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        # Conexão
        sock.bind(('127.0.0.1', 9595))
        sock.listen(1)
        print('(#) Aguardando conexão do jogador Java...')
        conn, _ = sock.accept()
        print('(#) Jogador conectado!\n')
        rodada = 1

        # Conversa
        while True:
            jogada_java = conn.recv(9).decode()[2:]
            if not jogada_java: break
            jogada_python = choice(jogadas)
            conn.sendall((jogada_python+'\r\n').encode())
            mostrar_resultado(jogada_python, jogada_java, rodada)
            rodada += 1

# Setup da Main
if __name__ == '__main__':
    run_server()