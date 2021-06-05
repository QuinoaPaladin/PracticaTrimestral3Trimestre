package es.studium.login;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BajaDistribuidor implements ActionListener, WindowListener
{
	// Ventana de Borrado de Cliente
	Frame frmBajaDistribuidor = new Frame("Baja de Distribuidor");
	Label lblMensajeBajaDistribuidor = new Label("Seleccionar el distribuidor:");
	Choice choDistribuidores = new Choice();
	Button btnBorrarDistribuidores = new Button("Borrar");
	Dialog dlgSeguroDistribuidor = new Dialog(frmBajaDistribuidor, "¿Seguro?", true);
	Label lblSeguroDistribuidor = new Label("¿Está seguro de borrar?");
	Button btnSiSeguroDistribuidor = new Button("Sí");
	Button btnNoSeguroDistribuidor = new Button("No");
	Dialog dlgConfirmacionBajaDistribuidor = new Dialog(frmBajaDistribuidor, "Baja Distribuidor", true);
	Label lblConfirmacionBajaDistribuidor = new Label("Baja de distribuidor correcta");

	BaseDatos bd;
	String sentencia = "";
	String sentencianull = "";
	Connection connection = null;
	Statement statement = null;
	Statement statementnull = null;
	ResultSet rs = null;
	ResultSet rsnull = null;

	public BajaDistribuidor()
	{
		frmBajaDistribuidor.setLayout(new FlowLayout());
		frmBajaDistribuidor.add(lblMensajeBajaDistribuidor);
		// Rellenar el Choice
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM distribuidores";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			choDistribuidores.removeAll();
			while(rs.next())
			{
				choDistribuidores.add(rs.getInt("idDistribuidor") +"-"+rs.getString("nombreDistribuidor") + "-" +rs.getString("ubicacionDistribuidor"));
			}
		}
		catch (SQLException sqle)
		{

		}
		frmBajaDistribuidor.add(choDistribuidores);
		btnBorrarDistribuidores.addActionListener(this);
		frmBajaDistribuidor.add(btnBorrarDistribuidores);

		frmBajaDistribuidor.setSize(250,140);
		frmBajaDistribuidor.setResizable(false);
		frmBajaDistribuidor.setLocationRelativeTo(null);
		frmBajaDistribuidor.addWindowListener(this);
		frmBajaDistribuidor.setVisible(true);
	}
	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		// TODO Auto-generated method stub
		if(frmBajaDistribuidor.isActive())
		{
			frmBajaDistribuidor.setVisible(false);
		}
		else if(dlgSeguroDistribuidor.isActive())
		{
			dlgSeguroDistribuidor.setVisible(false);
		}
		else if(dlgConfirmacionBajaDistribuidor.isActive())
		{
			btnSiSeguroDistribuidor.removeActionListener(this);
			btnNoSeguroDistribuidor.removeActionListener(this);
			btnBorrarDistribuidores.removeActionListener(this);
			dlgConfirmacionBajaDistribuidor.setVisible(false);
			dlgSeguroDistribuidor.setVisible(false);
			frmBajaDistribuidor.setVisible(false);
		}
	}
	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		// TODO Auto-generated method stub
		if(evento.getSource().equals(btnBorrarDistribuidores))
		{
			dlgSeguroDistribuidor.setLayout(new FlowLayout());
			dlgSeguroDistribuidor.addWindowListener(this);
			dlgSeguroDistribuidor.setSize(180,100);
			dlgSeguroDistribuidor.setResizable(false);
			dlgSeguroDistribuidor.setLocationRelativeTo(null);
			dlgSeguroDistribuidor.add(lblSeguroDistribuidor);
			btnSiSeguroDistribuidor.addActionListener(this);
			dlgSeguroDistribuidor.add(btnSiSeguroDistribuidor);
			btnNoSeguroDistribuidor.addActionListener(this);
			dlgSeguroDistribuidor.add(btnNoSeguroDistribuidor);
			dlgSeguroDistribuidor.setVisible(true);
		}
		else if(evento.getSource().equals(btnNoSeguroDistribuidor))
		{
			dlgSeguroDistribuidor.setVisible(false);
		}
		else if(evento.getSource().equals(btnSiSeguroDistribuidor))
		{
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			// Hacer un DELETE FROM clientes WHERE idCliente = X
			String[] elegido = choDistribuidores.getSelectedItem().split("-");
			sentencianull = "UPDATE productos set idDistribuidorFK=null WHERE idDistribuidorFK = " +elegido[0];
			sentencia = "DELETE FROM distribuidores WHERE idDistribuidor = "+elegido[0];
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				statementnull = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				statementnull.executeUpdate(sentencianull);
				Guardarlog.guardar(Login.txtUsuario.getText(),sentencianull);
				Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
				statement.executeUpdate(sentencia);
				lblConfirmacionBajaDistribuidor.setText("Baja de Distribuidor Correcta");
			}
			catch (SQLException sqle)
			{
				lblConfirmacionBajaDistribuidor.setText("Error en Baja");
			}
			finally
			{
				dlgConfirmacionBajaDistribuidor.setLayout(new FlowLayout());
				dlgConfirmacionBajaDistribuidor.addWindowListener(this);
				dlgConfirmacionBajaDistribuidor.setSize(200,100);
				dlgConfirmacionBajaDistribuidor.setResizable(false);
				dlgConfirmacionBajaDistribuidor.setLocationRelativeTo(null);
				dlgConfirmacionBajaDistribuidor.add(lblConfirmacionBajaDistribuidor);
				dlgConfirmacionBajaDistribuidor.setVisible(true);
			}
		}
	}	
}