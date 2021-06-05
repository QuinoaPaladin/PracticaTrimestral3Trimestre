package es.studium.login;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AltaFactura implements ActionListener, WindowListener
{
	Frame frmAltaFactura = new Frame("Alta de Factura");
	Label lblFechaFactura = new Label("Fecha:");
	TextField txtFechaFactura = new TextField(20);
	Label lblHoraFactura = new Label("Hora:");
	TextField txtHoraFactura = new TextField(20);
	Button btnAltaFactura = new Button("Alta");
	Button btnCancelarAltaFactura = new Button("Cancelar");

	Dialog dlgConfirmarAltaFactura = new Dialog(frmAltaFactura, "Alta Factura", true);
	Label lblMensajeAltaFactura = new Label("Alta de Factura Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	

	public AltaFactura()
	{
		frmAltaFactura.setLayout(new FlowLayout());
		frmAltaFactura.add(lblFechaFactura);
		txtFechaFactura.setText("");
		frmAltaFactura.add(txtFechaFactura);
		frmAltaFactura.add(lblHoraFactura);
		txtFechaFactura.setText("");
		frmAltaFactura.add(txtHoraFactura);
		btnAltaFactura.addActionListener(this);
		frmAltaFactura.add(btnAltaFactura);
		btnCancelarAltaFactura.addActionListener(this);
		frmAltaFactura.add(btnCancelarAltaFactura);

		frmAltaFactura.setSize(250,140);
		frmAltaFactura.setResizable(false);
		frmAltaFactura.setLocationRelativeTo(null);
		frmAltaFactura.addWindowListener(this);
		txtFechaFactura.requestFocus();
		frmAltaFactura.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent e)
	{}

	@Override
	public void windowClosed(WindowEvent e)
	{}

	@Override
	public void windowClosing(WindowEvent e)
	{
		// TODO Auto-generated method stub
		if(frmAltaFactura.isActive())
		{
			frmAltaFactura.setVisible(false);
		}
		else if(dlgConfirmarAltaFactura.isActive())
		{
			txtFechaFactura.setText("");
			txtHoraFactura.setText("");
			txtFechaFactura.requestFocus();
			dlgConfirmarAltaFactura.setVisible(false);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e){}

	@Override
	public void windowDeiconified(WindowEvent e){}

	@Override
	public void windowIconified(WindowEvent e){}

	@Override
	public void windowOpened(WindowEvent e){}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnAltaFactura))
		{
			bd = new BaseDatos();
			connection = bd.conectar();
			try
			{
				String[] fechaEuropea = txtFechaFactura.getText().split("-");
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				if(((txtFechaFactura.getText().length())!=0)&&((txtHoraFactura.getText().length())!=0))
				{
					sentencia = "INSERT INTO facturas VALUES(null, '" + fechaEuropea[2] + "-" + fechaEuropea[1] + "-" + fechaEuropea[0] + "', '" + txtHoraFactura.getText() + "')";
					System.out.println(sentencia);
					Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
					statement.executeUpdate(sentencia);
					lblMensajeAltaFactura.setText("Alta de Factura Correcta");
				}
				else
				{
					lblMensajeAltaFactura.setText("Faltan datos");
				}
			}
			catch (SQLException sqle)
			{
				lblMensajeAltaFactura.setText("Error en ALTA");
			}
			finally
			{
				dlgConfirmarAltaFactura.setLayout(new FlowLayout());
				dlgConfirmarAltaFactura.addWindowListener(this);
				dlgConfirmarAltaFactura.setSize(200,100);
				dlgConfirmarAltaFactura.setResizable(false);
				dlgConfirmarAltaFactura.setLocationRelativeTo(null);
				dlgConfirmarAltaFactura.add(lblMensajeAltaFactura);
				dlgConfirmarAltaFactura.setVisible(true);
				bd.desconectar(connection);
			}
		}
		if(evento.getSource().equals(btnCancelarAltaFactura))
		{
			frmAltaFactura.setVisible(false);

		}
	}
}