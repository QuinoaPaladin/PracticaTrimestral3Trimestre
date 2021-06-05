package es.studium.login;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModificarDistribuidor implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Distribuidor");
	Label lblFactura = new Label("Elegir Distribuidor");
	List listDetallesDistribuidor = new List(8, false);
	Button btnEditar = new Button("Editar");
	Button btnCancelar = new Button("Cancelar");

	Frame ventanaEdicion = new Frame("Editando Distribuidor");
	Label lblIdDistribuidor = new Label("idDistribuidor:");
	Label lblNombreDistribuidor = new Label("Nombre:");
	Label lblUbicacionDistribuidor = new Label("Ubicación:");
	TextField txtId = new TextField(20);
	TextField txtNombre = new TextField(20);
	TextField txtUbicacion = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar2 = new Button("Cancelar");

	Dialog dlgMensajeModificacionDistribuidor = new Dialog(ventanaEdicion, "Confirmación", true);
	Label lblMensaje = new Label("Modificación de Distribuidor Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ModificarDistribuidor()
	{
		ventana.setLayout(new FlowLayout());
		// Listeners
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventanaEdicion.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnCancelar2.addActionListener(this);
		dlgMensajeModificacionDistribuidor.addWindowListener(this);
		
		ventana.add(lblFactura);
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
			listDetallesDistribuidor.removeAll();
			while(rs.next())
			{
				listDetallesDistribuidor.add(rs.getInt("idDistribuidor")+"-"+rs.getString("nombreDistribuidor")+"-"+rs.getString("ubicacionDistribuidor"));
			}
		}
		catch (SQLException sqle)
		{

		}
		ventana.add(listDetallesDistribuidor);
		ventana.add(btnEditar);
		ventana.add(btnCancelar);
		ventana.setSize(200,250);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		// TODO Auto-generated method stub
		if(evento.getSource().equals(btnCancelar))
		{
			ventana.setVisible(false);
		}
		else if(evento.getSource().equals(btnCancelar2))
		{
			ventana.setVisible(false);
			ventanaEdicion.setVisible(false);
		}
		else if(evento.getSource().equals(btnEditar))
		{
			ventanaEdicion.setLayout(new FlowLayout());
			
			// Capturar los datos del elemento elegido del List
			String[] valores = listDetallesDistribuidor.getSelectedItem().split("-");
			ventanaEdicion.add(lblIdDistribuidor);
			txtId.setEnabled(false);
			txtId.setText(valores[0]);
			ventanaEdicion.add(txtId);
			ventanaEdicion.add(lblNombreDistribuidor);
			txtNombre.setText(valores[1]);
			ventanaEdicion.add(txtNombre);
			ventanaEdicion.add(lblUbicacionDistribuidor);
			txtUbicacion.setText(valores[2]);
			ventanaEdicion.add(txtUbicacion);
			ventanaEdicion.add(btnAceptar);
			ventanaEdicion.add(btnCancelar2);
			ventanaEdicion.setSize(200,300);
			ventanaEdicion.setResizable(false);
			ventanaEdicion.setLocationRelativeTo(null);
			
			ventanaEdicion.setVisible(true);
		}
		else if(evento.getSource().equals(btnAceptar))
		{
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			// Hacer un SELECT * FROM clientes
			sentencia = "UPDATE distribuidores SET nombreDistribuidor='"+txtNombre.getText() + "', ubicacionDistribuidor='"+ txtUbicacion.getText()+"' WHERE idDistribuidor="+txtId.getText();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
				statement.executeUpdate(sentencia);
				lblMensaje.setText("Actualización de distribuidor Correcta");
			}
			catch (SQLException sqle)
			{
				lblMensaje.setText("Error");
			}
			finally
			{
				dlgMensajeModificacionDistribuidor.setLayout(new FlowLayout());
				dlgMensajeModificacionDistribuidor.setSize(150,100);
				dlgMensajeModificacionDistribuidor.setResizable(false);
				dlgMensajeModificacionDistribuidor.setLocationRelativeTo(null);
				dlgMensajeModificacionDistribuidor.add(lblMensaje);
				dlgMensajeModificacionDistribuidor.setVisible(true);
				bd.desconectar(connection);
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{}

	@Override
	public void windowClosed(WindowEvent arg0)
	{}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		ventana.setVisible(false);
		ventanaEdicion.setVisible(false);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{}

	@Override
	public void windowIconified(WindowEvent arg0)
	{}

	@Override
	public void windowOpened(WindowEvent arg0)
	{}
}