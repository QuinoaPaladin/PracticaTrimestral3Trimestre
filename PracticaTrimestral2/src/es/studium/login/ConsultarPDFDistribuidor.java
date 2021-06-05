package es.studium.login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConsultarPDFDistribuidor
{
	public ArrayList<String> consultarPDF(Connection conexion)
	{
		ArrayList<String> datos = new ArrayList<String>();
		Statement statement = null;
		ResultSet rs = null;
		//Crear una sentencia
		try
		{
			statement = conexion.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			String sentencia = "SELECT idDistribuidor, nombreDistribuidor AS 'Nombre', ubicacionDistribuidor AS 'Ubicacion' FROM distribuidores ORDER BY 1;";
			rs = statement.executeQuery(sentencia);
			while(rs.next())
			{
				datos.add(rs.getString("idDistribuidor"));
				datos.add(rs.getString("Nombre"));
				datos.add(rs.getString("Ubicacion"));
			
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error 4-"+e.getMessage());
		}
		return(datos);
	}
}
