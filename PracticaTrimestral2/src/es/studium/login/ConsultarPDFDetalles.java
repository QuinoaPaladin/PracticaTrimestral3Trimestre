package es.studium.login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConsultarPDFDetalles
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
			String sentencia = "SELECT idDetallesFactura AS 'idDetalles', totalIVA AS 'IVA Total', totalCoste AS 'Coste Total', idFacturaFK AS 'IDFactura', idProductoFK AS 'IDProducto' FROM detallesfacturas ORDER BY 1;";
			rs = statement.executeQuery(sentencia);
			while(rs.next())
			{
				datos.add(rs.getString("idDetalles"));
				datos.add(rs.getString("IVA Total"));
				datos.add(rs.getString("Coste Total"));
				datos.add(rs.getString("IDFactura"));
				datos.add(rs.getString("IDProducto"));
			
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error 4-"+e.getMessage());
		}
		return(datos);
	}
}
