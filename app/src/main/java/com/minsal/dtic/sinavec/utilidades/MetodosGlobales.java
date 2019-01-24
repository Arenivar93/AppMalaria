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
import com.minsal.dtic.sinavec.EntityDAO.FosUserUser;


import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


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
    public static final ArrayList<String> getTipoDocumento() {
        ArrayList<String> tipos = new ArrayList<>();
        tipos.add("Seleccione");
        tipos.add("DUI");
        tipos.add("Carné Minoridad");
        tipos.add("Carné ISSS");
        tipos.add("Pasaporte");
        tipos.add("Carné Trabajo");
        tipos.add("Carné Estudiante");
        tipos.add("Ninguno");
        tipos.add("Otros");
        tipos.add("Partida Nacimiento");
        tipos.add("NIT");
        return tipos;
    }
    public static final ArrayList<String> getSexo() {
        ArrayList<String> tipos = new ArrayList<>();
        tipos.add("Seleccione");
        tipos.add("Masculino");
        tipos.add("Femenino");

        return tipos;
    }
    public static final ArrayList<String> getTipoEdad() {
        ArrayList<String> tipoEdad = new ArrayList<>();
        tipoEdad.add("Años");
        tipoEdad.add("Meses");
        tipoEdad.add("Dias");

        return tipoEdad;
    }
    public static String calcularEdad(int yearOfBirth, int monthOfBirth, int dayOfBirth) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now();
            LocalDate birthdate = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
            Period p = Period.between(birthdate, today);
            String anios = String.valueOf(p.getYears());
            String meses = String.valueOf(p.getMonths());
            String dias = String.valueOf(p.getDays());
            return anios+"/"+meses+"/"+dias;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            Calendar c2 = new GregorianCalendar(yearOfBirth, monthOfBirth, dayOfBirth);
            Calendar c1 = new GregorianCalendar(year, month, day);
            long end = c2.getTimeInMillis();
            long start = c1.getTimeInMillis();
            long milliseconds = TimeUnit.MILLISECONDS.toMillis(Math.abs(end - start));
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(milliseconds);
            int mYear = c.get(Calendar.YEAR) - 1970;
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH) - 1;
            return mYear+"/"+mMonth+"/"+mDay;
        }
    }







}
