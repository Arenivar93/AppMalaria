package com.minsal.dtic.sinavec.utilidades;

import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCantonDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipioDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;

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


    DaoSession daoSession;
    CtlMunicipioDao daoMunicipio;
    CtlCantonDao daoCanton;
    CtlCaserioDao daoCaserio;

    ArrayList<String> listaMunicipio;
    ArrayList<String> listaCanton;
    List<CtlCanton> cantones;
    ArrayList<String> listaCaserios;
    List<CtlCaserio> caserios;

    public Utilidades(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public List<CtlMunicipio> loadspinnerMunicipio(int idDepto){
        CtlMunicipio municipio=null;
        daoMunicipio=daoSession.getCtlMunicipioDao();
        List<CtlMunicipio> municipios=new ArrayList<CtlMunicipio>();
        municipios=daoMunicipio.queryBuilder().where(CtlMunicipioDao.Properties.IdDepartamento.eq(3))
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




    public List<CtlCanton> loadSpinerCanton(Long idCtn) {
        daoCanton=daoSession.getCtlCantonDao();
        cantones=new ArrayList<CtlCanton>();
        cantones=daoCanton.queryBuilder().where(CtlCantonDao.Properties.IdMunicipio.eq(idCtn))
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
}
