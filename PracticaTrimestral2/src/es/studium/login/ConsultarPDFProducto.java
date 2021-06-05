package es.studium.login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConsultarPDFProducto
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
			String sentencia = "SELECT idProducto, nombreProducto AS 'Nombre', precioProducto AS 'Precio', ivaProducto AS 'IVA', idDistribuidorFK AS 'Distribuidor' FROM productos ORDER BY 1;";
			rs = statement.executeQuery(sentencia);
			while(rs.next())
			{
				datos.add(rs.getString("idProducto"));
				datos.add(rs.getString("Nombre"));
				datos.add(rs.getString("Precio"));
				datos.add(rs.getString("IVA"));
				datos.add(rs.getString("Distribuidor"));
			
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error 4-"+e.getMessage());
		}
		return(datos);
	}
}
