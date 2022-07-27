package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {

	public List<Corso> getCorsiByPeriodo(int periodo) {
		String sql = "select * " // togliere '\n' e aggiungere sempre
				+ "from corso " // uno spazio al fondo
				+ "where pd = ?";
		List<Corso> result = new ArrayList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo); // l'indicizzazione parte da 1, a differenza degli array
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Corso c = new Corso(res.getString("codins"), res.getInt("crediti"), res.getString("nome"),
						res.getInt("pd"));
				result.add(c);
			}
			st.close();
			res.close();
			conn.close();
			return result;

		} catch (SQLException e) {
			System.err.println("Errore nel DAO");
			e.printStackTrace();
			return null;
		}

	}

	public Map<Corso, Integer> getIscritti(int periodo) {
		String sql = "select c.codins, c.crediti, c.nome, c.pd, count(*) as n " + "from corso c, iscrizione i "
				+ "where c.codins = i.codins and c.pd = ? " + "group by c.codins, c.crediti, c.nome, c.pd";

		Map<Corso, Integer> result = new HashMap<Corso, Integer>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.put(new Corso(res.getString("codins"), res.getInt("crediti"), res.getString("nome"),
						res.getInt("pd")), res.getInt("n"));
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
