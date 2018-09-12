package com.minsal.dtic.sinavec.utilidades;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


//Librerias para implementar la encriptacion de Sonata en Java

import java.security.MessageDigest;
import static java.util.Objects.hash;
//import java.util.Base64;

import com.minsal.dtic.sinavec.EntityDAO.CtlTabletDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;


import java.io.File;


public class MetodosGlobales{
    private static String DB_PATH="/data/data/com.minsal.dtic.sinavec/databases/";
    private static  String DB_NAME="malaria";

    private DaoSession daoSession;
    public String eluser;

    public MetodosGlobales(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    //este metodo verifica si existe la base de datos
    public boolean checkDataBase() {
        boolean existe= false;
        String myPath = DB_PATH + DB_NAME;
        File file = new File(myPath);
        existe=file.exists();
        //si esxiste retorna true
        return  existe;
    }

    public int validateLogin(String elUser,String pw) throws Exception {
        this.eluser = elUser;
        boolean existe = false;
        String salt;
        String password;
        String encriptado;
        String sqlQUERY="select username,salt,password from fos_user_user where username='"+elUser+"'";
        Cursor cursor = daoSession.getDatabase().rawQuery(sqlQUERY,null);
        if (cursor.moveToFirst())
        {
            //existe=true;
            /*Encuentro la clave encriptada del usuario, la validacion consistira en encriptar la clave
              ingresada por el usuario en el dispositivo movil, la cual se simulara la encriptacion
              implementada por Sonata admin, la cual se implementaran los algotitmos de
              encriptacion Sha512 y Base64, la encriptacion generada debera de ser igual al password
              encriptado almacenado en la base de datos*/
            salt=cursor.getString(1);
            password=cursor.getString(2);
            encriptado=hashPassword(pw,salt);
            //String nueva=encriptado;
            String encriptadoSinCaracteres=encriptado.replace("\n","");
            if(password.equals(encriptadoSinCaracteres)){
                return 2;
            }else{
                return 1;
            }
        }else{
            return 0;
        }
    }

    public static String hashPassword(String password, String salt) throws Exception {
        String result = password;
        String appendedSalt = new StringBuilder().append('{').append(salt).append('}').toString();
        String appendedSalt2 = new StringBuilder().append(password).append('{').append(salt).append('}').toString();

        if(password != null) {
            MessageDigest mda = MessageDigest.getInstance("SHA-512");
            byte[] pwdBytes = password.getBytes("UTF-8");
            byte[] saltBytes = appendedSalt.getBytes("UTF-8");
            byte[] saltBytes2 = appendedSalt2.getBytes("UTF-8");
            byte[] encriptacion = encode(mda, pwdBytes, saltBytes);
           for (int i = 1; i < 5000; i++) {
               encriptacion = encode(mda, encriptacion, saltBytes2);
            }
            result=new String(android.util.Base64.encode(encriptacion,android.util.Base64.DEFAULT));
            //result = new String(Base64.getEncoder().encode(encriptacion));
        }
        return result;
    }

    private static byte[] encode(MessageDigest mda, byte[] pwdBytes,byte[] saltBytes) {
        mda.update(pwdBytes);
        byte [] encriptaSha512 = mda.digest(saltBytes);
        return encriptaSha512;
    }


    public int consultaTabelt(){
        CtlTabletDao tabletDao = daoSession.getCtlTabletDao();
        int table = (int)tabletDao.count();
        return table;

    }

    //comprueba si hay acceso a interne con alguna de las redes conectadas
    public static boolean compruebaConexion(Context context)
    {
        boolean connected = false;
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }






}
