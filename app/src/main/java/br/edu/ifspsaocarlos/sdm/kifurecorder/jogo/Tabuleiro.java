package br.edu.ifspsaocarlos.sdm.kifurecorder.jogo;

import android.util.Log;

import br.edu.ifspsaocarlos.sdm.kifurecorder.MainActivity;

/**
 * Representa um estado de tabuleiro
 */
public class Tabuleiro {

    public final static int VAZIO = 0;
    public final static int PEDRA_PRETA = 1;
    public final static int PEDRA_BRANCA = 2;

    private int dimensao;
    private Integer[][] tabuleiro = new Integer[9][9];

    public Tabuleiro(int dimensao) {
        this.dimensao = dimensao;
        this.tabuleiro = new Integer[dimensao][dimensao];
        for (int i = 0; i < dimensao; ++i) {
            for (int j = 0; j < dimensao; ++j) {
                tabuleiro[i][j] = VAZIO;
            }
        }
    }

    public void colocarPedra(int linha, int coluna, int pedra) {
        if (pedra != PEDRA_PRETA && pedra != PEDRA_BRANCA) {
            // Problema
            throw new RuntimeException("Pedra inválida!");
        }
        if (linha < 0 || coluna < 0 || linha >= dimensao || coluna >= dimensao) {
            throw new RuntimeException("Posição inválida!");
        }
        tabuleiro[linha][coluna] = pedra;
    }

    public int getPosicao(int linha, int coluna) {
        return tabuleiro[linha][coluna];
    }

    public int getDimensao() {
        return dimensao;
    }

    public String toString() {
        StringBuilder saida = new StringBuilder();

        for (int i = 0; i < dimensao; ++i) {
            for (int j = 0; j < dimensao; ++j) {
                if (tabuleiro[i][j] == VAZIO) {
                    saida.append('.');
                }
                else if (tabuleiro[i][j] == PEDRA_PRETA) {
                    saida.append('P');
                }
                else if (tabuleiro[i][j] == PEDRA_BRANCA) {
                    saida.append('B');
                }
            }
            saida.append(System.getProperty("line.separator"));
        }

        return saida.toString();
    }

    public Tabuleiro rotacionarEmSentidoHorario() {
        Tabuleiro tabuleiroRotacionado = new Tabuleiro(dimensao);
        for (int i = 0; i < dimensao; ++i) {
            for (int j = 0; j < dimensao; ++j) {
                if (tabuleiro[dimensao - 1 - j][i] != Tabuleiro.VAZIO) {
                    tabuleiroRotacionado.colocarPedra(i, j, tabuleiro[dimensao - 1 - j][i]);
                }
            }
        }
        return tabuleiroRotacionado;
    }

    /**
     * Verifica se dois tabuleiros são exatamente iguais.
     *
     * @return Verdadeiro se os dois tabuleiros são exatamente iguais, falso
     *         caso contrário
     */
    public boolean identico(Tabuleiro outroTabuleiro) {
        if (dimensao != outroTabuleiro.dimensao) {
            return false;
        }
        for (int i = 0; i < dimensao; ++i) {
            for (int j = 0; j < dimensao; ++j) {
                if (getPosicao(i, j) != outroTabuleiro.getPosicao(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Verifica se dois tabuleiros são iguais, incluindo se um tabuleiro é uma
     * rotação do outro.
     *
     * @return Verdadeiro se os dois tabuleiros são iguais, falso caso
     *         contrário
     */
    public boolean equals(Tabuleiro outroTabuleiro) {
        if (dimensao != outroTabuleiro.dimensao) {
            return false;
        }

        Tabuleiro rotacao1 = outroTabuleiro.rotacionarEmSentidoHorario();
        Tabuleiro rotacao2 = rotacao1.rotacionarEmSentidoHorario();
        Tabuleiro rotacao3 = rotacao2.rotacionarEmSentidoHorario();

        Log.d(MainActivity.TAG, "Comparando tabuleiro " + this + " com tabuleiros " + outroTabuleiro + ", " + rotacao1 + ", " + rotacao2 + " e " + rotacao3);

        if (this.identico(outroTabuleiro) || this.identico(rotacao1)
                || this.identico(rotacao2) || this.identico(rotacao3)) {
            return true;
        }

        return false;
    }

}
