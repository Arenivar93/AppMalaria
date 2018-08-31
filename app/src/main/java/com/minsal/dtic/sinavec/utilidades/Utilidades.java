package com.minsal.dtic.sinavec.utilidades;

import android.database.Cursor;
import android.provider.Settings;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCantonDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamentoDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriaderoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.PlColvol;
import com.minsal.dtic.sinavec.EntityDAO.PlColvolDao;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoActividad;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoActividadDao;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoCaptura;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoCapturaDao;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrador on 07-18-18.
 */

public class Utilidades {

    public static int rotacion=0;
    public static boolean valida=true;

    public static final int LIST=1;
    public static final int GRID=2;


    public static int visualizacion=LIST;

    public static int fragment=0;


    DaoSession daoSession;
    CtlMunicipioDao daoMunicipio;
    CtlCantonDao daoCanton;
    CtlCaserioDao daoCaserio;

    ArrayList<String> listaMunicipio;
    ArrayList<String> listaCanton;
    List<CtlCanton> cantones;
    ArrayList<String> listaCaserios;
    List<CtlCaserio> caserios;
    List<CtlPlCriadero> criaderos;
    List<PlColvol> colvols;

    public Utilidades(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public List<CtlMunicipio> loadspinnerMunicipio(int idDepto){
        CtlMunicipio municipio=null;
        daoMunicipio=daoSession.getCtlMunicipioDao();
        List<CtlMunicipio> municipios=new ArrayList<CtlMunicipio>();
        municipios=daoMunicipio.queryBuilder().where(CtlMunicipioDao.Properties.IdDepartamento.eq(idDepto))
                .orderAsc(CtlMunicipioDao.Properties.Nombre).list();
        return municipios;
    }
    public ArrayList<String> obtenerListaMunicipio(List<CtlMunicipio> municipios){
        listaMunicipio=new ArrayList<String>();
        listaMunicipio.add("Seleccione");
        for (int i=0;i<municipios.size();i++){
            listaMunicipio.add(municipios.get(i).getNombre());
        }
        return listaMunicipio;
    }


    public List<CtlCanton> loadSpinerCanton(Long idM) {
        daoCanton=daoSession.getCtlCantonDao();
        cantones=new ArrayList<CtlCanton>();
        cantones=daoCanton.queryBuilder().where(CtlCantonDao.Properties.IdMunicipio.eq(idM))
                .orderAsc(CtlCantonDao.Properties.Nombre).list();
        return cantones;
    }

    public ArrayList<String> obetenerListaCantones(List<CtlCanton> cantones) {
        listaCanton=new ArrayList<String>();
        listaCanton.add("Seleccione");
        for (int i=0;i<cantones.size();i++){
            listaCanton.add(cantones.get(i).getNombre());
        }
        return listaCanton;
    }


    public List<CtlCaserio> loadSpinerCaserio(Long idCtn) {
        daoCaserio=daoSession.getCtlCaserioDao();
        caserios=new ArrayList<CtlCaserio>();
        caserios=daoCaserio.queryBuilder().where(CtlCaserioDao.Properties.IdCanton.eq(idCtn))
                .orderAsc(CtlCaserioDao.Properties.Nombre).list();
        return caserios;
    }

    public ArrayList<String> obetenerListaCaserios(List<CtlCaserio> caserios) {
        listaCaserios=new ArrayList<String>();
        listaCaserios.add("Seleccione");
        for (int i=0;i<caserios.size();i++){
            listaCaserios.add(caserios.get(i).getNombre());
        }
        return listaCaserios;
    }

    public List<CtlPlCriadero> obtenerCriaderosByIds(DaoSession daoSession, int idMuni, int idCtn, int idCas){
        criaderos=new ArrayList<CtlPlCriadero>();
        if(idMuni!=0){
            if(idCtn!=0 && idCas==0){
                //Ejecutara busqueda de municipio y canton
                 /* QueryBuilder<User> queryBuilder = userDao.queryBuilder();
                queryBuilder.join(Address.class, AddressDao.Properties.userId)
                        .where(AddressDao.Properties.Street.eq("Sesame Street"));
                List<User> users = queryBuilder.list();*/

                CtlPlCriaderoDao criaderoDao=daoSession.getCtlPlCriaderoDao();

                QueryBuilder<CtlPlCriadero> queryBuilder = criaderoDao.queryBuilder();
                Join ctlCaserio =queryBuilder.join(CtlPlCriaderoDao.Properties.IdCaserio,CtlCaserio.class);
                Join ctlCanton=queryBuilder.join(ctlCaserio,CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class,CtlCantonDao.Properties.Id);
                ctlCanton.where(CtlCantonDao.Properties.Id.eq(idCtn));
                criaderos=queryBuilder.orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();

            }else if(idCtn!=0 && idCas!=0){
                //Ejecutara busqueda de muni ctn y cass
                criaderos=daoSession.getCtlPlCriaderoDao()
                        .queryBuilder()
                        .where(CtlPlCriaderoDao.Properties.IdCaserio.eq(idCas))
                        .orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();

            }else{
                //ejecutara busqueda solo de municipio
                CtlPlCriaderoDao criaderoDao=daoSession.getCtlPlCriaderoDao();

                QueryBuilder<CtlPlCriadero> queryBuilder = criaderoDao.queryBuilder();
                Join ctlCaserio =queryBuilder.join(CtlPlCriaderoDao.Properties.IdCaserio,CtlCaserio.class);
                Join ctlCanton=queryBuilder.join(ctlCaserio,CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class,CtlCantonDao.Properties.Id);
                Join ctlMunicipio=queryBuilder.join(ctlCanton,CtlCantonDao.Properties.IdMunicipio,
                        CtlMunicipio.class,CtlMunicipioDao.Properties.Id);
                ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMuni));
                criaderos=queryBuilder.orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();
            }
            }else {

        }

        return criaderos;

    }
    public List<PlColvol> obtenerColvolByIds(DaoSession daoSession, int idMuni, int idCtn, int idCas){
        colvols=new ArrayList<PlColvol>();
        if(idMuni!=0){
            if(idCtn!=0 && idCas==0){
                PlColvolDao colvolDao=daoSession.getPlColvolDao();

                QueryBuilder<PlColvol> queryBuilder = colvolDao.queryBuilder();
                Join ctlCaserio =queryBuilder.join(PlColvolDao.Properties.IdCaserio,CtlCaserio.class);
                Join ctlCanton=queryBuilder.join(ctlCaserio,CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class,CtlCantonDao.Properties.Id);
                ctlCanton.where(CtlCantonDao.Properties.Id.eq(idCtn));
                colvols=queryBuilder.orderAsc(PlColvolDao.Properties.Nombre).list();

            }else if(idCtn!=0 && idCas!=0){
                //Ejecutara busqueda de muni ctn y cass
                colvols=daoSession.getPlColvolDao()
                        .queryBuilder()
                        .where(PlColvolDao.Properties.IdCaserio.eq(idCas))
                        .orderAsc(PlColvolDao.Properties.Nombre).list();

            }else{
                //ejecutara busqueda solo de municipio
                PlColvolDao colvolDao=daoSession.getPlColvolDao();

                QueryBuilder<PlColvol> queryBuilder = colvolDao.queryBuilder();
                Join ctlCaserio =queryBuilder.join(PlColvolDao.Properties.IdCaserio,CtlCaserio.class);
                Join ctlCanton=queryBuilder.join(ctlCaserio,CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class,CtlCantonDao.Properties.Id);
                Join ctlMunicipio=queryBuilder.join(ctlCanton,CtlCantonDao.Properties.IdMunicipio,
                        CtlMunicipio.class,CtlMunicipioDao.Properties.Id);
                ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMuni));
                colvols=queryBuilder.orderAsc(PlColvolDao.Properties.Nombre).list();
            }
        }else {

        }

        return colvols;

    }

    public List<PlTipoActividad> loadspinnerActividad(){
        //PlTipoActividad actividad=null;
        PlTipoActividadDao actividadDao=daoSession.getPlTipoActividadDao();
        List<PlTipoActividad> actividad=new ArrayList<PlTipoActividad>();
        actividad=actividadDao.loadAll();
        return actividad;
    }
    public ArrayList<String> obtenerListaActividad(List<PlTipoActividad> actividades){
        ArrayList<String> listaActividad=new ArrayList<String>();
        listaActividad.add("Seleccione");
        for (int i=0;i<actividades.size();i++){
            listaActividad.add(actividades.get(i).getNombre());
        }
        return listaActividad;
    }




    public List<PlTipoCaptura> loadspinnerCaptura(){
        PlTipoCapturaDao capturaDao=daoSession.getPlTipoCapturaDao();
        List<PlTipoCaptura> captura=new ArrayList<PlTipoCaptura>();
        captura=capturaDao.loadAll();
        return captura;
    }
    public ArrayList<String> obtenerListaCaptura(List<PlTipoCaptura> capturas){
        ArrayList<String> listaCaptura=new ArrayList<String>();
        listaCaptura.add("Seleccione");
        for (int i=0;i<capturas.size();i++){
            listaCaptura.add(capturas.get(i).getNombre());        }
        return listaCaptura;
    }

    public int deptoUser(String username) {

        CtlDepartamentoDao departamentoDao = daoSession.getCtlDepartamentoDao();
        String sqlQUERY = "SELECT d.id FROM ctl_departamento d " +
                "INNER JOIN ctl_municipio m on (m.id_departamento = d.id)\n" +
                "INNER JOIN ctl_establecimiento es on(es.id_municipio= m.id)\n" +
                "INNER JOIN fos_user_user f on (f.id_sibasi= es.id)\n" +
                "WHERE f.username ='" + username + "'";
        Cursor cursor = daoSession.getDatabase().rawQuery(sqlQUERY, null);

        int idDepartamento = 0;
        if (cursor.moveToFirst()) {
            idDepartamento = cursor.getInt(0);
        }
        return idDepartamento;
    }

    public List<Double> getCoordenadasDepartamento(int idDepto) {
        List<Double> coordenadas=new ArrayList<Double>();
        switch (idDepto){
            case 1:
                coordenadas.add(13.924417447800225);
                coordenadas.add(-89.84502099044607);
            break;
            case 2:
                coordenadas.add(13.98944214420671);
                coordenadas.add(-89.55775603094685);
                break;
            case 3:
                coordenadas.add(13.713998160450721);
                coordenadas.add(-89.7241812947351);
                break;
            case 4:
                coordenadas.add(14.040246499891111);
                coordenadas.add(-88.93654739847989);
                break;
            case 5:
                coordenadas.add(13.48790905547123);
                coordenadas.add(-89.3138970134354);
                break;
            case 6:
                coordenadas.add(13.700522292352314);
                coordenadas.add(-89.2243065830553);
                break;
            case 7:
                coordenadas.add(13.721760783928321);
                coordenadas.add(-88.93454747211422);
                break;
            case 8:
                coordenadas.add(13.509297506318713);
                coordenadas.add(-88.87253379682079);
                break;
            case 9:
                coordenadas.add(13.840857923573395);
                coordenadas.add(-88.85099106975167);
                break;
            case 10:
                coordenadas.add(13.638495811409328);
                coordenadas.add(-88.7812149622755);
                break;
            case 11:
                coordenadas.add(13.337202572142669);
                coordenadas.add(-88.43989024244115);
                break;
            case 12:
                coordenadas.add(13.481664900678894);
                coordenadas.add(-88.17468692767659);
                break;
            case 13:
                coordenadas.add(13.690076813573697);
                coordenadas.add(-88.10076110598004);
                break;
            case 14:
                coordenadas.add(13.166424607066649);
                coordenadas.add(-87.94670660373527);
                break;
        }

        return coordenadas;
    }
}
