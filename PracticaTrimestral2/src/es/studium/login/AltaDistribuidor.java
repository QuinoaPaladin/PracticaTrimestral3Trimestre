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

public class AltaDistribuidor implements ActionListener, WindowListener
{
	Frame frmAltaDistribuidor = new Frame("Alta de Distribuidor");
	Label lblNombreDistribuidor = new Label("Nombre:");
	TextField txtNombreDistribuidor = new TextField(20);
	Label lblUbicacionDistribuidor = new Label("Ubicación:");
	TextField txtUbicacionDistribuidor = new TextField(20);
	Button btnAltaDistribuidor = new Button("Alta");
	Button btnCancelarAltaDistribuidor = new Button("Cancelar");

	Dialog dlgConfirmarAltaDistribuidor = new Dialog(frmAltaDistribuidor, "Alta Distribuidor", true);
	Label lblMensajeAltaDistribuidor = new Label("Alta de Distribuidor Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public AltaDistribuidor()
	{
		frmAltaDistribuidor.setLayout(new FlowLayout());
		frmAltaDistribuidor.add(lblNombreDistribuidor);
		txtNombreDistribuidor.setText("");
		frmAltaDistribuidor.add(txtNombreDistribuidor);
		frmAltaDistribuidor.add(lblUbicacionDistribuidor);
		txtUbicacionDistribuidor.setText("");
		frmAltaDistribuidor.add(txtUbicacionDistribuidor);		
		btnAltaDistribuidor.addActionListener(this);
		frmAltaDistribuidor.add(btnAltaDistribuidor);
		btnCancelarAltaDistribuidor.addActionListener(this);
		frmAltaDistribuidor.add(btnCancelarAltaDistribuidor);

		frmAltaDistribuidor.setSize(300,140);
		frmAltaDistribuidor.setResizable(false);
		frmAltaDistribuidor.setLocationRelativeTo(null);
		frmAltaDistribuidor.addWindowListener(this);
		txtNombreDistribuidor.requestFocus();
		frmAltaDistribuidor.setVisible(true);
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
		if(frmAltaDistribuidor.isActive())
		{
			frmAltaDistribuidor.setVisible(false);
		}
		else if(dlgConfirmarAltaDistribuidor.isActive())
		{
			txtNombreDistribuidor.setText("");
			txtUbicacionDistribuidor.setText("");
			txtNombreDistribuidor.requestFocus();
			dlgConfirmarAltaDistribuidor.setVisible(false);
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
		if(evento.getSource().equals(btnAltaDistribuidor))
		{
			bd = new BaseDatos();
			connection = bd.conectar();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				if(((txtNombreDistribuidor.getText().length())!=0)&&((txtUbicacionDistribuidor.getText().length())!=0))
				{
					sentencia = "INSERT INTO distribuidores VALUES (null, '"+ txtNombreDistribuidor.getText()+ "', '" +txtUbicacionDistribuidor.getText() + "')";
					Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
					statement.executeUpdate(sentencia);
					lblMensajeAltaDistribuidor.setText("Alta de Distribuidor Correcta");
				} //(null, '"+ txtNombreDistribuidor.getText()+ "', '" +txtUbicacionDistribuidore.getText() + "')";
				else
				{
					lblMensajeAltaDistribuidor.setText("Faltan datos");
				}
			}
			catch (SQLException sqle)
			{
				lblMensajeAltaDistribuidor.setText("Error en ALTA");
			}
			finally
			{
				dlgConfirmarAltaDistribuidor.setLayout(new FlowLayout());
				dlgConfirmarAltaDistribuidor.addWindowListener(this);
				dlgConfirmarAltaDistribuidor.setSize(200,100);
				dlgConfirmarAltaDistribuidor.setResizable(false);
				dlgConfirmarAltaDistribuidor.setLocationRelativeTo(null);
				dlgConfirmarAltaDistribuidor.add(lblMensajeAltaDistribuidor);
				dlgConfirmarAltaDistribuidor.setVisible(true);
				bd.desconectar(connection);
			}
		}
		if(evento.getSource().equals(btnCancelarAltaDistribuidor))
		{
			frmAltaDistribuidor.setVisible(false);
		}
	}
}