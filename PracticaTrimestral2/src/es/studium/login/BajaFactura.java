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

public class BajaFactura implements ActionListener, WindowListener
{
	// Ventana de Borrado de Cliente
	Frame frmBajaFactura = new Frame("Baja de Factura");
	Label lblMensajeBajaFactura = new Label("Seleccionar la factura:");
	Choice choFacturas = new Choice();
	Button btnBorrarFactura = new Button("Borrar");
	Dialog dlgSeguroFactura = new Dialog(frmBajaFactura, "¿Seguro?", true);
	Label lblSeguroFactura = new Label("¿Está seguro de borrar?");
	Button btnSiSeguroFactura = new Button("Sí");
	Button btnNoSeguroFactura = new Button("No");
	Dialog dlgConfirmacionBajaFactura = new Dialog(frmBajaFactura, "Baja Factura", true);
	Label lblConfirmacionBajaFactura = new Label("Baja de factura correcta");

	BaseDatos bd;
	String sentencia = "";
	String sentencianull = "";
	Connection connection = null;
	Statement statement = null;
	Statement statementnull = null;
	ResultSet rs = null;
	ResultSet rsnull = null;

	public BajaFactura()
	{
		frmBajaFactura.setLayout(new FlowLayout());
		frmBajaFactura.add(lblMensajeBajaFactura);
		// Rellenar el Choice
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM facturas";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			choFacturas.removeAll();
			while(rs.next())
			{
				String[] fechaEuropea = rs.getString("fechaFactura").split("-");
				choFacturas.add(rs.getInt("idFactura") +"- "+ fechaEuropea[2] + "-" + fechaEuropea[1] + "-" + fechaEuropea[0] +" - "+rs.getString("horaFactura"));
			}
		}
		catch (SQLException sqle)
		{

		}
		frmBajaFactura.add(choFacturas);
		btnBorrarFactura.addActionListener(this);
		frmBajaFactura.add(btnBorrarFactura);

		frmBajaFactura.setSize(250,140);
		frmBajaFactura.setResizable(false);
		frmBajaFactura.setLocationRelativeTo(null);
		frmBajaFactura.addWindowListener(this);
		frmBajaFactura.setVisible(true);
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
		if(frmBajaFactura.isActive())
		{
			frmBajaFactura.setVisible(false);
		}
		else if(dlgSeguroFactura.isActive())
		{
			dlgSeguroFactura.setVisible(false);
		}
		else if(dlgConfirmacionBajaFactura.isActive())
		{
			btnSiSeguroFactura.removeActionListener(this);
			btnNoSeguroFactura.removeActionListener(this);
			btnBorrarFactura.removeActionListener(this);
			dlgConfirmacionBajaFactura.setVisible(false);
			dlgSeguroFactura.setVisible(false);
			frmBajaFactura.setVisible(false);
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
		if(evento.getSource().equals(btnBorrarFactura))
		{
			dlgSeguroFactura.setLayout(new FlowLayout());
			dlgSeguroFactura.addWindowListener(this);
			dlgSeguroFactura.setSize(180,100);
			dlgSeguroFactura.setResizable(false);
			dlgSeguroFactura.setLocationRelativeTo(null);
			dlgSeguroFactura.add(lblSeguroFactura);
			btnSiSeguroFactura.addActionListener(this);
			dlgSeguroFactura.add(btnSiSeguroFactura);
			btnNoSeguroFactura.addActionListener(this);
			dlgSeguroFactura.add(btnNoSeguroFactura);
			dlgSeguroFactura.setVisible(true);
		}
		else if(evento.getSource().equals(btnNoSeguroFactura))
		{
			dlgSeguroFactura.setVisible(false);
		}
		else if(evento.getSource().equals(btnSiSeguroFactura))
		{
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			// Hacer un DELETE FROM clientes WHERE idCliente = X
			String[] elegido = choFacturas.getSelectedItem().split("-");
			sentencianull = "UPDATE detallesfacturas set idFacturaFK=null WHERE idFacturaFK = " +elegido[0]; 
			sentencia = "DELETE FROM facturas WHERE idFactura = "+elegido[0];
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				statementnull = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				statementnull.executeUpdate(sentencianull);
				statement.executeUpdate(sentencia);
				Guardarlog.guardar(Login.txtUsuario.getText(),sentencianull);
				Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
				lblConfirmacionBajaFactura.setText("Baja de Factura Correcta");
			}
			catch (SQLException sqle)
			{
				lblConfirmacionBajaFactura.setText("Error en Baja");
			}
			finally
			{
				dlgConfirmacionBajaFactura.setLayout(new FlowLayout());
				dlgConfirmacionBajaFactura.addWindowListener(this);
				dlgConfirmacionBajaFactura.setSize(200,100);
				dlgConfirmacionBajaFactura.setResizable(false);
				dlgConfirmacionBajaFactura.setLocationRelativeTo(null);
				dlgConfirmacionBajaFactura.add(lblConfirmacionBajaFactura);
				dlgConfirmacionBajaFactura.setVisible(true);
			}
		}
	}	
}