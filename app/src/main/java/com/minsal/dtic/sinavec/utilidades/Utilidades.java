package com.minsal.dtic.sinavec.utilidades;

import android.database.Cursor;
import android.util.Log;

import com.minsal.dtic.sinavec.EntityDAO.ColvolCalve;
import com.minsal.dtic.sinavec.EntityDAO.ColvolCalveDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCantonDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamento;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamentoDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlEstablecimiento;
import com.minsal.dtic.sinavec.EntityDAO.CtlEstablecimientoDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriaderoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUser;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUserDao;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;
import com.minsal.dtic.sinavec.EntityDAO.PlColvol;
import com.minsal.dtic.sinavec.EntityDAO.PlColvolDao;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvariaDao;
import com.minsal.dtic.sinavec.EntityDAO.PlSeguimientoBotiquin;
import com.minsal.dtic.sinavec.EntityDAO.PlSeguimientoBotiquinDao;
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

    public static int rotacion = 0;
    public static boolean valida = true;
    public static String token = null;

    public static final int LIST = 1;
    public static final int GRID = 2;


    public static int visualizacion = LIST;

    public static int fragment = 0;


    private DaoSession daoSession;
    private PlPesquisaLarvariaDao pesDao;
    private CtlEstablecimientoDao estDao;

    private ArrayList<String> listaMunicipio;
    private ArrayList<String> listaCanton;
    private List<CtlCanton> cantones;
    private ArrayList<String> listaCaserios;

    private List<CtlCaserio> caserios;
    private List<PlCapturaAnopheles> capturas;
    private List<PlPesquisaLarvaria> pesquisasBySem;

    private List<CtlPlCriadero> criaderosMap;
    private List<CtlEstablecimiento> establecimientoMap;
    private List<PlColvol> colvolMap;
    private List<CtlPlCriadero> criaderos;
    private List<PlColvol> colvols;
    private List<PlPesquisaLarvaria> pesquisas;
    private List<ColvolCalve> clavesColvol;

    public Utilidades(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public List<CtlMunicipio> loadspinnerMunicipio(int idDepto) {
        CtlMunicipio municipio = null;
        CtlMunicipioDao daoMunicipio = daoSession.getCtlMunicipioDao();
        List<CtlMunicipio> municipios = new ArrayList<CtlMunicipio>();
        municipios = daoMunicipio.queryBuilder().where(CtlMunicipioDao.Properties.IdDepartamento.eq(idDepto))
                .orderAsc(CtlMunicipioDao.Properties.Nombre).list();
        return municipios;
    }

    public ArrayList<String> obtenerListaMunicipio(List<CtlMunicipio> municipios) {
        listaMunicipio = new ArrayList<String>();
        listaMunicipio.add("Seleccione");
        for (int i = 0; i < municipios.size(); i++) {
            listaMunicipio.add(municipios.get(i).getNombre());
        }
        return listaMunicipio;
    }
    public ArrayList<String> getMunicipioTodos(List<CtlMunicipio> municipios) {
        listaMunicipio = new ArrayList<String>();
        listaMunicipio.add("Todos");
        for (int i = 0; i < municipios.size(); i++) {
            listaMunicipio.add(municipios.get(i).getNombre());
        }
        return listaMunicipio;
    }


    public List<CtlCanton> loadSpinerCanton(Long idM) {
        CtlCantonDao daoCanton = daoSession.getCtlCantonDao();
        cantones = new ArrayList<CtlCanton>();
        cantones = daoCanton.queryBuilder().where(CtlCantonDao.Properties.IdMunicipio.eq(idM))
                .orderAsc(CtlCantonDao.Properties.Nombre).list();
        return cantones;
    }

    public ArrayList<String> obetenerListaCantones(List<CtlCanton> cantones) {
        listaCanton = new ArrayList<String>();
        listaCanton.add("Seleccione");
        for (int i = 0; i < cantones.size(); i++) {
            listaCanton.add(cantones.get(i).getNombre());
        }
        return listaCanton;
    }


    public List<CtlCaserio> loadSpinerCaserio(Long idCtn) {
        CtlCaserioDao daoCaserio = daoSession.getCtlCaserioDao();
        caserios = new ArrayList<CtlCaserio>();
        caserios = daoCaserio.queryBuilder().where(CtlCaserioDao.Properties.IdCanton.eq(idCtn))
                .orderAsc(CtlCaserioDao.Properties.Nombre).list();
        return caserios;
    }

    public ArrayList<String> obetenerListaCaserios(List<CtlCaserio> caserios) {
        listaCaserios = new ArrayList<String>();
        listaCaserios.add("Seleccione");
        for (int i = 0; i < caserios.size(); i++) {
            listaCaserios.add(caserios.get(i).getNombre());
        }
        return listaCaserios;
    }

    public List<CtlPlCriadero> obtenerCriaderosByIds(DaoSession daoSession, int idMuni, int idCtn, int idCas) {
        criaderos = new ArrayList<CtlPlCriadero>();
        if (idMuni != 0) {
            if (idCtn != 0 && idCas == 0) {
                CtlPlCriaderoDao criaderoDao = daoSession.getCtlPlCriaderoDao();

                QueryBuilder<CtlPlCriadero> queryBuilder = criaderoDao.queryBuilder();
                Join ctlCaserio = queryBuilder.join(CtlPlCriaderoDao.Properties.IdCaserio, CtlCaserio.class);
                Join ctlCanton = queryBuilder.join(ctlCaserio, CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class, CtlCantonDao.Properties.Id);
                ctlCanton.where(CtlCantonDao.Properties.Id.eq(idCtn));
                criaderos = queryBuilder.orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();

            } else if (idCtn != 0 && idCas != 0) {
                //Ejecutara busqueda de muni ctn y cass
                criaderos = daoSession.getCtlPlCriaderoDao()
                        .queryBuilder()
                        .where(CtlPlCriaderoDao.Properties.IdCaserio.eq(idCas))
                        .orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();

            } else {
                //ejecutara busqueda solo de municipio
                CtlPlCriaderoDao criaderoDao = daoSession.getCtlPlCriaderoDao();

                QueryBuilder<CtlPlCriadero> queryBuilder = criaderoDao.queryBuilder();
                Join ctlCaserio = queryBuilder.join(CtlPlCriaderoDao.Properties.IdCaserio, CtlCaserio.class);
                Join ctlCanton = queryBuilder.join(ctlCaserio, CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class, CtlCantonDao.Properties.Id);
                Join ctlMunicipio = queryBuilder.join(ctlCanton, CtlCantonDao.Properties.IdMunicipio,
                        CtlMunicipio.class, CtlMunicipioDao.Properties.Id);
                ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMuni));
                criaderos = queryBuilder.orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();
            }
        } else {

        }

        return criaderos;

    }

    public List<PlColvol> obtenerColvolByIds(DaoSession daoSession, int idMuni, int idCtn, int idCas) {
        colvols = new ArrayList<PlColvol>();
        if (idMuni != 0) {
            if (idCtn != 0 && idCas == 0) {
                PlColvolDao colvolDao = daoSession.getPlColvolDao();

                QueryBuilder<PlColvol> queryBuilder = colvolDao.queryBuilder();
                Join ctlCaserio = queryBuilder.join(PlColvolDao.Properties.IdCaserio, CtlCaserio.class);
                Join ctlCanton = queryBuilder.join(ctlCaserio, CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class, CtlCantonDao.Properties.Id);
                ctlCanton.where(CtlCantonDao.Properties.Id.eq(idCtn));
                colvols = queryBuilder.orderAsc(PlColvolDao.Properties.Nombre).list();

            } else if (idCtn != 0 && idCas != 0) {
                //Ejecutara busqueda de muni ctn y cass
                colvols = daoSession.getPlColvolDao()
                        .queryBuilder()
                        .where(PlColvolDao.Properties.IdCaserio.eq(idCas))
                        .orderAsc(PlColvolDao.Properties.Nombre).list();

            } else {
                //ejecutara busqueda solo de municipio
                PlColvolDao colvolDao = daoSession.getPlColvolDao();

                QueryBuilder<PlColvol> queryBuilder = colvolDao.queryBuilder();
                Join ctlCaserio = queryBuilder.join(PlColvolDao.Properties.IdCaserio, CtlCaserio.class);
                Join ctlCanton = queryBuilder.join(ctlCaserio, CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class, CtlCantonDao.Properties.Id);
                Join ctlMunicipio = queryBuilder.join(ctlCanton, CtlCantonDao.Properties.IdMunicipio,
                        CtlMunicipio.class, CtlMunicipioDao.Properties.Id);
                ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMuni));
                colvols = queryBuilder.orderAsc(PlColvolDao.Properties.Nombre).list();
            }
        } else {

        }

        return colvols;

    }

    public List<PlTipoActividad> loadspinnerActividad() {
        //PlTipoActividad actividad=null;
        PlTipoActividadDao actividadDao = daoSession.getPlTipoActividadDao();
        List<PlTipoActividad> actividad = new ArrayList<PlTipoActividad>();
        actividad = actividadDao.loadAll();
        return actividad;
    }

    public ArrayList<String> obtenerListaActividad(List<PlTipoActividad> actividades) {
        ArrayList<String> listaActividad = new ArrayList<String>();
        listaActividad.add("Seleccione");
        for (int i = 0; i < actividades.size(); i++) {
            listaActividad.add(actividades.get(i).getNombre());
        }
        return listaActividad;
    }


    public List<PlTipoCaptura> loadspinnerCaptura() {
        PlTipoCapturaDao capturaDao = daoSession.getPlTipoCapturaDao();
        List<PlTipoCaptura> captura = new ArrayList<PlTipoCaptura>();
        captura = capturaDao.loadAll();
        return captura;
    }

    public ArrayList<String> obtenerListaCaptura(List<PlTipoCaptura> capturas) {
        ArrayList<String> listaCaptura = new ArrayList<String>();
        listaCaptura.add("Seleccione");
        for (int i = 0; i < capturas.size(); i++) {
            listaCaptura.add(capturas.get(i).getNombre());
        }
        return listaCaptura;
    }

    public int deptoUser(String username) {
        Log.d("Error1",username);
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
        List<Double> coordenadas = new ArrayList<Double>();
        switch (idDepto) {
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

    public List<PlCapturaAnopheles> loadListcapturas(int idSemana) {

        PlCapturaAnophelesDao capDao = daoSession.getPlCapturaAnophelesDao();
        capturas = new ArrayList<PlCapturaAnopheles>();
        QueryBuilder<PlCapturaAnopheles> qb = capDao.queryBuilder().where(PlCapturaAnophelesDao.Properties.IdSemanaEpidemiologica.eq(idSemana));
        capturas = qb.list();
        return capturas;
    }

    public ArrayList<String> loadListcapturasBySem() {
        ArrayList<String> capturasBySem = new ArrayList<String>();
        float indice;
        Cursor c = daoSession.getDatabase().rawQuery("SELECT strftime('%Y', fecha) AS anio," +
                " id_semana_epidemiologica, SUM(total_mosquitos),SUM(total_anopheles)," +
                "COUNT(id) FROM PL_CAPTURA_ANOPHELES" +
                " GROUP BY id_semana_epidemiologica, anio", null);
        if (c.moveToFirst()) {
            do {
                if (c.getInt(2)>0){
                    indice = (float) c.getInt(3)/ c.getInt(2);
                }else{indice=0;}
                capturasBySem.add(c.getString(0) + "-" + c.getInt(1) + "-" + c.getInt(2)+"-"+c.getInt(3)+"-"+c.getInt(4)+"-"+indice);
            } while (c.moveToNext());
        }
        c.close();
        return capturasBySem;
    }

    public ArrayList<String> getListaCapturas(List<PlCapturaAnopheles> capturas) {
        ArrayList<String> listaCapturas = new ArrayList<String>();
        for (int i = 0; i < capturas.size(); i++) {
            listaCapturas.add(capturas.get(i).getCtlCaserio().getCtlCanton().getCtlMunicipio().getNombre()
                    + "-" + capturas.get(i).getCtlCaserio().getCtlCanton().getNombre()
                    + "-" + capturas.get(i).getCtlCaserio().getNombre()
                    + "-" + capturas.get(i).getPropietario()
                    + "-" + capturas.get(i).getTotalAnopheles()
                    + "-" + capturas.get(i).getIdSemanaEpidemiologica());
        }
        return listaCapturas;
    }

    /**
    *metodos para lista de pesquisa larvaria
    */

    public ArrayList<String> loadListPesquisaBySem() {
        pesDao = daoSession.getPlPesquisaLarvariaDao();
      //  pesquisasBySem = new ArrayList<PlPesquisaLarvaria>();
        ArrayList<String> pesquisaPrueba = new ArrayList<String>();
        float indice;
        Cursor c = daoSession.getDatabase().rawQuery("SELECT strftime('%Y', fecha_hora_reg) AS anio, id_semana_epidemiologica," +
                " SUM(anopheles_uno),SUM(anopheles_dos),SUM(pupa),SUM(numero_cucharonada)FROM PL_PESQUISA_LARVARIA GROUP BY id_semana_epidemiologica, anio", null);
        if (c.moveToFirst()) {
            do {
                if (c.getInt(3)>0){
                    indice = (float) c.getInt(2)/ c.getInt(3);
                }else{indice=0;}
                pesquisaPrueba.add(c.getString(0) + "-" + c.getInt(1) + "-" + c.getInt(2)+"-"+c.getInt(3)+"-"+c.getInt(4)+"-"+c.getInt(5)+"-"+indice);
            } while (c.moveToNext());
        }
        c.close();
        return pesquisaPrueba;
    }
    /**
     *metodos para lista de seguimiento de botiquin por semana
     */

    public ArrayList<String> loadSeguimientosBySem() {
           ArrayList seguimientoBySem = new ArrayList<PlSeguimientoBotiquin>();
       //  pesquisaPrueba = new ArrayList<String>();
        int total;
        Cursor c = daoSession.getDatabase().rawQuery("SELECT strftime('%Y', fecha_hora_reg) AS anio, id_semana_epidemiologica," +
                " SUM(supervisado),SUM(visitado),SUM(en_riesgo),SUM(numero_persona_divulgo)FROM PL_SEGUIMIENTO_BOTIQUIN GROUP BY id_semana_epidemiologica, anio", null);
        if (c.moveToFirst()) {
            do {
                total = c.getInt(2)+c.getInt(3);
                seguimientoBySem.add(c.getString(0) + "-" + c.getInt(1) + "-" + c.getInt(2)+"-"+c.getInt(3)+"-"+c.getInt(4)+"-"+c.getInt(5)+"-"+total);
            } while (c.moveToNext());
        }
        c.close();
        return seguimientoBySem;
    }
    public ArrayList<String> loadSeguimientosDetalleCombinado(int semana) {
        ArrayList seguimientoCombinado = new ArrayList<PlSeguimientoBotiquin>();
        int total;
        String sql = "select seg.id,cla.clave,pro.nombre AS procedencia, col.nombre, mun.nombre as municipio, seg.FECHA_HORA_REG,seg.SUPERVISADO,seg.VISITADO,seg.EN_RIESGO from PL_SEGUIMIENTO_BOTIQUIN seg "+
                     "inner join CLAVE cla ON (seg.ID_CLAVE =cla.id) "+
                     "INNER JOIN CTL_PROCEDENCIA pro ON (pro.id= cla.ID_PROCEDENCIA) "+
                     "INNER join PL_COLVOL col on (col.CLAVE= cla.CLAVE) "+
                     "INNER join CTL_CASERIO cas on (cas.id= col.ID_CASERIO) "+
                     " INNER join CTL_CANTON can on (can.id = cas.ID_CANTON) "+
                     " INNER join CTL_MUNICIPIO mun ON (mun.id = can.ID_MUNICIPIO) "+
                     "WHERE seg.ID_SEMANA_EPIDEMIOLOGICA='"+semana+"'"+
                     " UNION "+
                     " select seg.id,cla.clave,pro.nombre AS procedencia, EST.nombre, mun.nombre as municipio, seg.FECHA_HORA_REG,seg.SUPERVISADO,seg.VISITADO,seg.EN_RIESGO  from PL_SEGUIMIENTO_BOTIQUIN seg "+
                     " inner join CLAVE cla ON (seg.ID_CLAVE =cla.id) "+
                     " INNER JOIN CTL_PROCEDENCIA pro ON (pro.id= cla.ID_PROCEDENCIA) "+
                     " INNER JOIN ESTABLECIMIENTO_CLAVE ESTC ON (ESTC.ID_CLAVE= CLA.ID) "+
                     " INNER JOIN CTL_ESTABLECIMIENTO EST ON (ESTC.ID_ESTABLECIMIENTO= EST.ID) "+
                     " INNER JOIN CTL_MUNICIPIO mun on (mun.id = est.ID_MUNICIPIO) "+
                     "WHERE seg.ID_SEMANA_EPIDEMIOLOGICA='"+semana+"'";
        Cursor c = daoSession.getDatabase().rawQuery(sql, null);
        if (c.moveToFirst()) {
            do {
                  seguimientoCombinado.add(c.getString(0) + "/" +c.getString(2)+ "/"+c.getString(4) + "/" + c.getString(3)+"/"+c.getInt(6)+"/"+c.getInt(7)+"/"+c.getInt(8)+"/"+c.getString(5));
            } while (c.moveToNext());
        }
        c.close();
        return seguimientoCombinado;
    }






    public List<PlPesquisaLarvaria> loadPesquisasDetalleSemana(int semana) {
        pesDao = daoSession.getPlPesquisaLarvariaDao();
        pesquisas = new ArrayList<PlPesquisaLarvaria>();
        QueryBuilder<PlPesquisaLarvaria> qb = pesDao.queryBuilder().where(PlPesquisaLarvariaDao.Properties.IdSemanaEpidemiologica.eq(semana));
        pesquisas = qb.list();
        return pesquisas;
    }
    public List<PlSeguimientoBotiquin> loadSeguimientoDetalleSemana(int semana) {
        PlSeguimientoBotiquinDao segDao  = daoSession.getPlSeguimientoBotiquinDao();
        List<PlSeguimientoBotiquin> seguimientos = new ArrayList<PlSeguimientoBotiquin>();
        QueryBuilder<PlSeguimientoBotiquin> qb = segDao.queryBuilder().where(PlSeguimientoBotiquinDao.Properties.IdSemanaEpidemiologica.eq(semana));
        seguimientos = qb.list();
        return seguimientos;
    }


    public List<CtlPlCriadero> loadCriaderosMap( int idMunicipio) {

        CtlPlCriaderoDao criaderoDao = daoSession.getCtlPlCriaderoDao();

        criaderosMap = new ArrayList<CtlPlCriadero>();
        if (idMunicipio>0){
            QueryBuilder<CtlPlCriadero> qb = criaderoDao.queryBuilder().where(CtlPlCriaderoDao.Properties.Latitud.isNotNull()).where(CtlPlCriaderoDao.Properties.Estado_sync.notEq(1));
            Join ctlCaserio = qb.join(CtlPlCriaderoDao.Properties.IdCaserio,CtlCaserio.class);
            Join ctlCanton = qb.join(ctlCaserio,CtlCaserioDao.Properties.IdCanton,
                    CtlCanton.class,CtlCantonDao.Properties.Id);
            Join ctlMunicipio = qb.join(ctlCanton,CtlCantonDao.Properties.IdMunicipio,
                    CtlMunicipio.class,CtlMunicipioDao.Properties.Id);
            ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMunicipio));
            criaderosMap = qb.orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();
        }else {
            criaderosMap = criaderoDao.queryBuilder()
                    .where(CtlPlCriaderoDao.Properties.Latitud.isNotNull()).where(CtlPlCriaderoDao.Properties.Estado_sync.notEq(1)).list();
        }

        return criaderosMap;

    }
    public List<CtlEstablecimiento> loadEstablecimientoMap(int idMunicipio, int idDepto) {
        estDao = daoSession.getCtlEstablecimientoDao();
        establecimientoMap = new ArrayList<CtlEstablecimiento>();
        if (idMunicipio>0){
            QueryBuilder<CtlEstablecimiento> qb = estDao.queryBuilder().where(CtlEstablecimientoDao.Properties.Latitud.isNotNull()).where(CtlEstablecimientoDao.Properties.IdMunicipio.eq(idMunicipio));

            establecimientoMap = qb.orderAsc(CtlEstablecimientoDao.Properties.Nombre).list();
        }else {
            QueryBuilder<CtlEstablecimiento> qb = estDao.queryBuilder().where(CtlEstablecimientoDao.Properties.Latitud.isNotNull());
            Join ctlMunicipio = qb.join(CtlEstablecimientoDao.Properties.IdMunicipio,CtlMunicipio.class);
            Join ctlDepartamento = qb.join(ctlMunicipio,CtlMunicipioDao.Properties.IdDepartamento,
                    CtlDepartamento.class,CtlDepartamentoDao.Properties.Id);
            ctlDepartamento.where(CtlDepartamentoDao.Properties.Id.eq(idDepto));
            establecimientoMap = qb.list();
        }
        return establecimientoMap;
    }



    public List<PlColvol> loadColvolMap( int idMunicipio) {

        PlColvolDao colvolDao = daoSession.getPlColvolDao();
        colvolMap = new ArrayList<PlColvol>();
        if (idMunicipio>0){
            QueryBuilder<PlColvol> qb = colvolDao.queryBuilder().where(PlColvolDao.Properties.Latitud.isNotNull());
            Join ctlCaserio = qb.join(PlColvolDao.Properties.IdCaserio,CtlCaserio.class);
            Join ctlCanton = qb.join(ctlCaserio,CtlCaserioDao.Properties.IdCanton,
                    CtlCanton.class,CtlCantonDao.Properties.Id);
            Join ctlMunicipio = qb.join(ctlCanton,CtlCantonDao.Properties.IdMunicipio,
                    CtlMunicipio.class,CtlMunicipioDao.Properties.Id);
            ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMunicipio));
            colvolMap = qb.orderAsc(PlColvolDao.Properties.Nombre).list();
        }else {
            colvolMap = colvolDao.queryBuilder()
                    .where(PlColvolDao.Properties.Latitud.isNotNull()).list();
        }
        return colvolMap;

    }

        //obteniendo el usuario en sesion
        public long getIdUser(String username) {
            long id = 0;
            if (!username.equals("")) {
                List<FosUserUser> ids = null;
                FosUserUserDao userDao = daoSession.getFosUserUserDao();
                QueryBuilder<FosUserUser> qb = userDao.queryBuilder();
                qb.where(FosUserUserDao.Properties.Username.eq(username));
                ids = qb.list();
                for (FosUserUser f : ids) {
                    id = f.getId();
                }
            }
            return id;
        }


    public long getIdSibasiUser(String username) {
        long idSibasi = 0;
        if (!username.equals("")) {
            List<FosUserUser> ids = null;
            FosUserUserDao userDao = daoSession.getFosUserUserDao();
            QueryBuilder<FosUserUser> qb = userDao.queryBuilder();
            qb.where(FosUserUserDao.Properties.Username.eq(username));
            ids = qb.list();
            for (FosUserUser f : ids) {
                idSibasi = f.getIdSibasi();
            }
        }
        return idSibasi;

    }
    public List<ColvolCalve> loadClavesColvolMap(int idMuni,int idCtn,int idCas){
        ColvolCalveDao colvolClaveDao=daoSession.getColvolCalveDao();
        clavesColvol=new ArrayList<ColvolCalve>();
        QueryBuilder<ColvolCalve> queryBuilder=colvolClaveDao.queryBuilder();
        Join colvol = queryBuilder.join(ColvolCalveDao.Properties.IdColvol,PlColvol.class);

        if (idCtn!=0 && idCas==0){//busqueda por canton
            Join ctlCaserio = queryBuilder.join(colvol,PlColvolDao.Properties.IdCaserio,CtlCaserio.class,CtlCaserioDao.Properties.Id);
            Join ctlCanton = queryBuilder.join(ctlCaserio,CtlCaserioDao.Properties.IdCanton,CtlCanton.class,CtlCantonDao.Properties.Id);
            ctlCanton.where(CtlCantonDao.Properties.Id.eq(idCtn));
        }else if (idCtn!=0 && idCas!=0){//busqueda por caserio
            Join ctlCaserio = queryBuilder.join(colvol,PlColvolDao.Properties.IdCaserio,CtlCaserio.class,CtlCaserioDao.Properties.Id);
            ctlCaserio.where(CtlCaserioDao.Properties.Id.eq(idCas));
        }else{//busqueda por municipio
            Join ctlCaserio = queryBuilder.join(colvol,PlColvolDao.Properties.IdCaserio,CtlCaserio.class,CtlCaserioDao.Properties.Id);
            Join ctlCanton = queryBuilder.join(ctlCaserio,CtlCaserioDao.Properties.IdCanton,CtlCanton.class,CtlCantonDao.Properties.Id);
            Join ctlMunicipio = queryBuilder.join(ctlCanton,CtlCantonDao.Properties.IdMunicipio,CtlMunicipio.class,CtlMunicipioDao.Properties.Id);
            ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMuni));
        }
        colvol.where(PlColvolDao.Properties.Latitud.isNotNull());
        //colvol.orderAsc
        clavesColvol=queryBuilder.list();
        return clavesColvol;
    }
    /*public List<CtlPlCriadero> loadCriaderosMap( int idMunicipio) {

        CtlPlCriaderoDao criaderoDao = daoSession.getCtlPlCriaderoDao();

        criaderosMap = new ArrayList<CtlPlCriadero>();
        if (idMunicipio>0){
            QueryBuilder<CtlPlCriadero> qb = criaderoDao.queryBuilder().where(CtlPlCriaderoDao.Properties.Latitud.isNotNull()).where(CtlPlCriaderoDao.Properties.Estado_sync.notEq(1));
            Join ctlCaserio = qb.join(CtlPlCriaderoDao.Properties.IdCaserio,CtlCaserio.class);
            Join ctlCanton = qb.join(ctlCaserio,CtlCaserioDao.Properties.IdCanton,
                    CtlCanton.class,CtlCantonDao.Properties.Id);
            Join ctlMunicipio = qb.join(ctlCanton,CtlCantonDao.Properties.IdMunicipio,
                    CtlMunicipio.class,CtlMunicipioDao.Properties.Id);
            ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMunicipio));
            criaderosMap = qb.orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();
        }else {
            criaderosMap = criaderoDao.queryBuilder()
                    .where(CtlPlCriaderoDao.Properties.Latitud.isNotNull()).where(CtlPlCriaderoDao.Properties.Estado_sync.notEq(1)).list();
        }

        return criaderosMap;

    }*/


}
