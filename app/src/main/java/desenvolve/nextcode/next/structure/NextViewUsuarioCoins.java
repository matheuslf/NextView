package desenvolve.nextcode.next.structure;

import java.io.Serializable;

public class NextViewUsuarioCoins implements Serializable {

    private long Id;
    private long IdConta;
    private long id_usuario;
    private long coins;

    public NextViewUsuarioCoins() {

    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getIdConta() {
        return IdConta;
    }

    public void setIdConta(long idConta) {
        IdConta = idConta;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }
}
