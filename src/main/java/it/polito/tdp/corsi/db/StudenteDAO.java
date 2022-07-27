package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.corsi.model.Divisione;
import it.polito.tdp.corsi.model.Studente;

public class StudenteDAO {

	public List<Studente> getStudentiByCorso(String codins) {
		String sql = "select s.matricola, s.cognome, s.nome, s.CDS " + "from studente s, iscrizione i "
				+ "where s.matricola = i.matricola and i.codins = ?";

		List<Studente> result = new ArrayList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Studente(res.getInt("matricola"), res.getString("cognome"), res.getString("nome"),
						res.getString("CDS")));
			}
			res.close();
			st.close();
			conn.close();

			return result;
		} catch (SQLException e) {
			System.err.println("Errore nel DAO");
			e.printStackTrace();
			return null;
		}

	}

	public List<Divisione> getDivisioneStudenti(String codins) {
		String sql = "select s.CDS, count(*) as n " + "from studente s, iscrizione i "
				+ "where s.matricola = i.matricola and i.codins = ? and s.CDS <> '' " + "group by s.CDS";
		List<Divisione> result = new ArrayList<Divisione>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Divisione(res.getString("CDS"), res.getInt("n")));
			}
			res.close();
			st.close();
			conn.close();

			return result;
		} catch (SQLException e) {
			System.err.println("Errore nel DAO");
			e.printStackTrace();
			return null;
		}
	}

}
