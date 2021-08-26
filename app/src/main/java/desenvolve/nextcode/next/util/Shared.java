package desenvolve.nextcode.next.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Shared {

    /**
     * Chave para buscar o token.
     */
    public static final String KEY_ID_USUARIO = "KEY_ID_USUARIO";
    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String KEY_COINS = "KEY_COINS";

    /**
     * Armazena um determinado dado conforme sua tipagem.
     * @param context
     *          Contexto que chamou esse método.
     * @param key
     *          Chave a ser utilizada para adicionar o dado.
     * @param value
     *          Tipagem do objeto a ser adicionado.
     */
    public static final void put(final Context context, final String key, final Object value) {

        //
        // Monta o shared.
        //
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        //
        // Se for para adicionar uma String ...
        //
        if (value instanceof String) {
            editor.putString(key, (String)value);
        }
        //
        // Se for para adicionar um booleano ...
        //
        else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean)value);
        }
        //
        // Se for para adicionar um inteiro ...
        //
        else if (value instanceof Integer) {
            editor.putInt(key, (Integer)value);
        }
        //
        // Se for para adicionar um valor long.
        //
        else if (value instanceof Long) {
            editor.putLong(key, (Long)value);
        }

        //
        // Salva a alteração.
        //
        editor.commit();
    }

    /**
     * Retorna uma String que está armazenada na memória interna.
     * @param context
     *          Activity solicitante.
     * @param key
     *          Chave a ser encontrada.
     * @return
     */
    public static final String getString(final Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
    }

    /**
     * Retorna uma String que está armazenada na memória interna.
     * @param context
     *          Activity solicitante.
     * @param key
     *          Chave a ser encontrada.
     * @return
     */
    public static final boolean getBoolean(final Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false);
    }

    /**
     * Retorna uma String que está armazenada na memória interna.
     * @param context
     *          Activity solicitante.
     * @param key
     *          Chave a ser encontrada.
     * @return
     */
    public static final int getInt(final Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, 0);
    }

    /**
     * Retorna uma String que está armazenada na memória interna.
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static final int getInt(final Context context, final String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }

    /**
     * Retorna uma String que está armazenada na memória interna.
     * @param context
     *          Activity solicitante.
     * @param key
     *          Chave a ser encontrada.
     * @return
     */
    public static final long getLong(final Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, 0);
    }

    public static final void removeValue(final Context context, final String key) {
        //
        // Monta o shared.
        //
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
