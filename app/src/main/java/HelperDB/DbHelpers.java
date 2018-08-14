package HelperDB;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by desarrollo on 08-10-18.
 */

public class DbHelpers extends SQLiteOpenHelper {
    public  static  final int VERSIONDB=1;
    private static String DB_PATH="/data/data/com.minsal.dtic.sinavec/databases/";
    private static  String DB_NAME="malaria";
    private Context myContext;
    private  static File DATABASE_FILE;
    public static  final int DATABASE_VERSION =1;
    public String eluser;

    public DbHelpers(Context context) {
        super(context, DB_NAME, null, VERSIONDB);
        this.myContext =context;

    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        //no se crea la base ya que se traera una estructura realizada con dbeaver

    }

    public void inicializarBase(SQLiteDatabase db){
        db.execSQL("INSERT INTO Municipio(id,nombre)VALUES(1,'Prueba')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    //este metodo servira para copiar la base de datos en caso que no exista
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist==true) {
            // Si existe, no hacemos nada!
            Log.i("si exixteeeeee","siiiii exixte");
            getWritableDatabase();

        }else{
            Log.i("no exixte","no exixte");
            copyDataBaseAsync copyDataBase = new copyDataBaseAsync(myContext);
            copyDataBase.execute();
        }
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
    //metodo que copia la base de datos
    private void copyDataBase() throws IOException {
        getReadableDatabase();
        OutputStream databaseOutputStream = new FileOutputStream("" + DB_PATH	+ DB_NAME);
        InputStream databaseInputStream;

        //InputStream databaseInputStream2=new FileInputStream()

        byte[] buffer = new byte[1024];
        int length;

        databaseInputStream = myContext.getAssets().open(DB_NAME);
        while ((length = databaseInputStream.read(buffer)) > 0) {
            Log.i("copiando","copiando la base");
            databaseOutputStream.write(buffer);
        }

        databaseInputStream.close();
        databaseOutputStream.flush();
        databaseOutputStream.close();
        setDatabaseVersion();
        Log.i("Acción", "Copio la base de datos");
    }
    private void setDatabaseVersion() {
        DATABASE_FILE = myContext.getDatabasePath(DB_NAME); //obtiene la ruta de donde esta la base
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(DATABASE_FILE.getAbsolutePath(), null,SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("PRAGMA user_version = " + DATABASE_VERSION);
        } catch (SQLiteException e ) {
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }
    public boolean validateLogin(String elUser,String elPass){
        this.eluser = elUser;
        boolean existe = false;
        String sqlQUERY="select username from fos_user_user where username='"+elUser+"'";


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQUERY,null);
        boolean exixte =false;

        if (cursor.moveToFirst())
        {
            existe=true;
        }else{
            existe= false;
        }
        db.close();
        Log.i("usaurio", String.valueOf(existe));
        return existe;
    }
    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    //clase que copia la base de datos de forma asincrona
    public class copyDataBaseAsync extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        Context contexto;
        //constructor de la clase asincrona que recibe como argumento el contexto
        public copyDataBaseAsync(Context contexto){
            this.contexto = contexto;
        }
        protected String doInBackground(String... params) {
            try {
                //llama el metodo copyDataBase
                copyDataBase();
                //irLogin();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
//            progressDialog.dismiss();

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(contexto,"","Copiando la Base de datos...",true);
        }

    }


}
