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

public class ModificarDetallesFactura implements ActionListener, WindowListener
{
	Frame ventana = new Frame("Editar Detalles de la Factura");
	Label lblDetallesFacturas = new Label("Elegir Detalles");
	List listDetallesFactura = new List(8, false);
	Button btnEditar = new Button("Editar");
	Button btnCancelar = new Button("Cancelar");

	Frame ventanaEdicion = new Frame("Editando Detalles");
	Label lblidDetallesFactura = new Label("idDetallesFactura:");
	Label lblTotalIva = new Label("Total IVA:");
	Label lblTotalCoste = new Label("Total Coste:");
	Label lblidFacturaFK = new Label("idFacturaFK:");
	Label lblidProductoFK = new Label("idProductoFK:");
	TextField txtId = new TextField(20);
	TextField txtTotalIva = new TextField(20);
	TextField txtTotalCoste = new TextField(20);
	TextField txtFacturaFK = new TextField(20);
	TextField txtidProductoFK = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar2 = new Button("Cancelar");

	Dialog dlgMensajeModificacionDetallesFactura = new Dialog(ventanaEdicion, "Confirmación", true);
	Label lblMensaje = new Label("Modificación de Detalles Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ModificarDetallesFactura()
	{
		ventana.setLayout(new FlowLayout());
		// Listeners
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventanaEdicion.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnCancelar2.addActionListener(this);
		dlgMensajeModificacionDetallesFactura.addWindowListener(this);
		
		ventana.add(lblDetallesFacturas);
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM detallesfacturas";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			listDetallesFactura.removeAll();
			while(rs.next())
			{
				listDetallesFactura.add(rs.getInt("idDetallesFactura")+"-"+rs.getInt("totalIVA")+"-"+rs.getDouble("totalCoste")+"-"+rs.getInt("idFacturaFK")+"-"+rs.getInt("idProductoFK"));
			}
		}
		catch (SQLException sqle)
		{

		}
		ventana.add(listDetallesFactura);
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
			String[] valores = listDetallesFactura.getSelectedItem().split("-");
			
			ventanaEdicion.add(lblidDetallesFactura);
			txtId.setEnabled(false);
			txtId.setText(valores[0]);
			ventanaEdicion.add(txtId);
			
			ventanaEdicion.add(lblTotalIva);
			txtTotalIva.setText(valores[1]);
			ventanaEdicion.add(txtTotalIva);
			
			ventanaEdicion.add(lblTotalCoste);
			txtTotalCoste.setText(valores[2]);
			ventanaEdicion.add(txtTotalCoste);
			
			ventanaEdicion.add(lblidFacturaFK);
			txtFacturaFK.setEnabled(false);
			txtFacturaFK.setText(valores[3]);
			ventanaEdicion.add(txtFacturaFK);
			
			ventanaEdicion.add(lblidProductoFK);
			txtidProductoFK.setEnabled(false);
			txtidProductoFK.setText(valores[4]);
			ventanaEdicion.add(txtidProductoFK);
			
			ventanaEdicion.add(btnAceptar);
			ventanaEdicion.add(btnCancelar2);
			ventanaEdicion.setSize(200,350);
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
			sentencia = "UPDATE detallesfacturas SET totalIVA='"+txtTotalIva.getText() + "', totalCoste='"+ txtTotalCoste.getText()+"'  WHERE idDetallesFactura="+txtId.getText();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
				statement.executeUpdate(sentencia);
				lblMensaje.setText("Actualización de detalles Correcta");
			}
			catch (SQLException sqle)
			{
				lblMensaje.setText("Error");
			}
			finally
			{
				dlgMensajeModificacionDetallesFactura.setLayout(new FlowLayout());
				dlgMensajeModificacionDetallesFactura.setSize(150,100);
				dlgMensajeModificacionDetallesFactura.setResizable(false);
				dlgMensajeModificacionDetallesFactura.setLocationRelativeTo(null);
				dlgMensajeModificacionDetallesFactura.add(lblMensaje);
				dlgMensajeModificacionDetallesFactura.setVisible(true);
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