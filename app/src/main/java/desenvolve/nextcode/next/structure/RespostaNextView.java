package desenvolve.nextcode.next.structure;

import java.io.Serializable;

public class RespostaNextView implements Serializable {

    public boolean Sucesso;
    public String Mensagem;
    public long Id;
    public long Coins;

    public RespostaNextView() {

    }

    public boolean isSucesso() {
        return Sucesso;
    }

    public void setSucesso(boolean sucesso) {
        Sucesso = sucesso;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getCoins() {
        return Coins;
    }

    public void setCoins(long coins) {
        Coins = coins;
    }
}
