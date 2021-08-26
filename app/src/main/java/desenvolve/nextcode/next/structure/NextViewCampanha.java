package desenvolve.nextcode.next.structure;

import java.io.Serializable;

public class NextViewCampanha implements Serializable {

    private long Id;
    private long IdConta;
    private long id_usuario;
    private String url;
    private long view_quantity;
    private long count_quantity;
    private long watch_seconds;
    private long total_coins;

    public NextViewCampanha() {

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getView_quantity() {
        return view_quantity;
    }

    public void setView_quantity(long view_quantity) {
        this.view_quantity = view_quantity;
    }

    public long getCount_quantity() {
        return count_quantity;
    }

    public void setCount_quantity(long count_quantity) {
        this.count_quantity = count_quantity;
    }

    public long getWatch_seconds() {
        return watch_seconds;
    }

    public void setWatch_seconds(long watch_seconds) {
        this.watch_seconds = watch_seconds;
    }

    public long getTotal_coins() {
        return total_coins;
    }

    public void setTotal_coins(long total_coins) {
        this.total_coins = total_coins;
    }
}
