package es.studium.login;

import java.awt.Button;
import java.awt.Choice;
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

public class AltaProducto implements WindowListener, ActionListener
{
	// Atributos o Componentes
	Frame ventanaAltaProducto = new Frame("Alta de Producto");
	Label lblNombre = new Label("Nombre");
	Label lblPrecio = new Label("Precio");
	Label lblIVA = new Label("IVA");
	Label lblDistribuidor = new Label("Distribuidor");
	Choice choDistribuidores = new Choice();
	TextField txtNombre = new TextField(20);
	TextField txtPrecio = new TextField(20);
	TextField txtIVA = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");

	Dialog dlgMensajeAltaProducto = new Dialog(ventanaAltaProducto, "Confirmación", true);
	Label lblMensaje = new Label("Alta de Producto Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public AltaProducto()
	{
		ventanaAltaProducto.setLayout(new FlowLayout());
		ventanaAltaProducto.add(lblNombre);
		ventanaAltaProducto.add(txtNombre);
		ventanaAltaProducto.add(lblPrecio);
		ventanaAltaProducto.add(txtPrecio);
		ventanaAltaProducto.add(lblIVA);
		ventanaAltaProducto.add(txtIVA);
		ventanaAltaProducto.add(lblDistribuidor);
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
			choDistribuidores.add("Seleccionar un distribuidor");
			while(rs.next())
			{
				choDistribuidores.add(rs.getInt("idDistribuidor")
						+"-"+rs.getString("nombreDistribuidor")
						+"-"+rs.getString("ubicacionDistribuidor"));
			}
		}
		catch (SQLException sqle)
		{}
		ventanaAltaProducto.add(choDistribuidores);
		btnAceptar.addActionListener(this);
		ventanaAltaProducto.add(btnAceptar);
		btnCancelar.addActionListener(this);
		ventanaAltaProducto.add(btnCancelar);
		ventanaAltaProducto.setSize(250,250);
		ventanaAltaProducto.setResizable(false);
		ventanaAltaProducto.setLocationRelativeTo(null);
		ventanaAltaProducto.addWindowListener(this);
		ventanaAltaProducto.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		// TODO Auto-generated method stub
		if(evento.getSource().equals(btnCancelar))
		{
			ventanaAltaProducto.setVisible(false);
		}
		else
		{
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			try
			{
				
				sentencia = "INSERT INTO productos VALUES(null, '"
						+txtNombre.getText()+"','"
						+txtPrecio.getText()+"','"
						+txtIVA.getText()+"','"
						+choDistribuidores.getSelectedItem().split("-")[0]
						+"')";
				System.out.println(sentencia);
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
				statement.executeUpdate(sentencia);
			}
			catch (SQLException sqle)
			{
				lblMensaje.setText("Error en Alta");
			}
			finally
			{
				dlgMensajeAltaProducto.setLayout(new FlowLayout());
				dlgMensajeAltaProducto.addWindowListener(this);
				dlgMensajeAltaProducto.setSize(150,100);
				dlgMensajeAltaProducto.setResizable(false);
				dlgMensajeAltaProducto.setLocationRelativeTo(null);
				dlgMensajeAltaProducto.add(lblMensaje);
				dlgMensajeAltaProducto.setVisible(true);
				bd.desconectar(connection);
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		ventanaAltaProducto.setVisible(false);
		dlgMensajeAltaProducto.setVisible(false);
		ventanaAltaProducto.removeWindowListener(this);
		dlgMensajeAltaProducto.removeWindowListener(this);
		btnAceptar.removeActionListener(this);
		btnCancelar.removeActionListener(this);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}
}