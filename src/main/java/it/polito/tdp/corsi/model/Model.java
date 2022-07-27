package it.polito.tdp.corsi.model;

import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;
import it.polito.tdp.corsi.db.StudenteDAO;

public class Model {

	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;

	public Model() {
		this.corsoDao = new CorsoDAO();
		this.studenteDao = new StudenteDAO();
	}

	public List<Corso> getCorsiByPeriodo(int periodo) {
		return this.corsoDao.getCorsiByPeriodo(periodo);
	}

	public Map<Corso, Integer> getIscritti(int periodo) {
		return this.corsoDao.getIscritti(periodo);
	}

	public List<Studente> getStudentiByCorso(String codins) {
		return this.studenteDao.getStudentiByCorso(codins);
	}

	public List<Divisione> getDivisioneStudenti(String codins) {
		return this.studenteDao.getDivisioneStudenti(codins);
	}

}
