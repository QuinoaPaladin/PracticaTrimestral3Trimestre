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

public class ModificarFactura implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Factura");
	Label lblFactura = new Label("Elegir Factura");
	List listDetallesFactura1 = new List(8, false);
	Button btnEditar = new Button("Editar");
	Button btnCancelar = new Button("Cancelar");

	Frame ventanaEdicion1 = new Frame("Editando Factura 1/2");
	Label lblIdFactura = new Label("idFactura:");
	Label lblAnyoFactura = new Label("Año:");
	Label lblMesFactura = new Label("Mes:");
	Label lblDiaFactura = new Label("Día:");
	TextField txtId = new TextField(20);
	TextField txtAnyo = new TextField(20);
	TextField txtMes = new TextField(20);
	TextField txtDia = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar2 = new Button("Cancelar");
	
	Frame ventanaEdicion2 = new Frame ("Editando Factura 2/2");
	Label lblHoraFactura = new Label("Hora:");
	Label lblMinutoFactura = new Label("Minuto:");
	Label lblSegundoFactura = new Label("Segundo:");
	TextField txtHora = new TextField(20);
	TextField txtMinuto = new TextField(20);
	TextField txtSegundo = new TextField(20);
	Button btnAceptar2 = new Button("Aceptar");
	Button btnCancelar3 = new Button("Cancelar"); 
	

	Dialog dlgMensajeModificacionFactura = new Dialog(ventanaEdicion1, 
			"Confirmación", true);
	Label lblMensaje = new Label("Modificación de Factura Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ModificarFactura()
	{
		ventana.setLayout(new FlowLayout());
		// Listeners
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventanaEdicion1.addWindowListener(this);
		ventanaEdicion2.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnAceptar2.addActionListener(this);
		btnCancelar2.addActionListener(this);
		btnCancelar3.addActionListener(this);
		dlgMensajeModificacionFactura.addWindowListener(this);
		
		ventana.add(lblFactura);
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM facturas";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			listDetallesFactura1.removeAll();
			while(rs.next())
			{
				String[] fechaEuropea = rs.getString("fechaFactura").split("-");
				listDetallesFactura1.add(rs.getInt("idFactura")+"-"+fechaEuropea[2]+"-"+fechaEuropea[1]+"-"+fechaEuropea[0]+"-: "+rs.getString("horaFactura"));
			}
		}
		catch (SQLException sqle)
		{

		}
		ventana.add(listDetallesFactura1);
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
			ventanaEdicion1.setVisible(false);
		}
		else if(evento.getSource().equals(btnCancelar3))
		{
			ventana.setVisible(false);
			ventanaEdicion2.setVisible(false);
		}
		else if(evento.getSource().equals(btnEditar))
		{
			ventanaEdicion1.setLayout(new FlowLayout());
			
			// Capturar los datos del elemento elegido del List
			String[] valores = listDetallesFactura1.getSelectedItem().split("-");			
			ventanaEdicion1.add(lblIdFactura);
			txtId.setEnabled(false);
			txtId.setText(valores[0]);
			ventanaEdicion1.add(txtId);
			ventanaEdicion1.add(lblDiaFactura);
			txtDia.setText(valores[1]);
			ventanaEdicion1.add(txtDia);
			ventanaEdicion1.add(lblMesFactura);
			txtMes.setText(valores[2]);
			ventanaEdicion1.add(txtMes);
			ventanaEdicion1.add(lblAnyoFactura);
			txtAnyo.setText(valores[3]);			
			ventanaEdicion1.add(txtAnyo);
			ventanaEdicion1.add(btnAceptar);
			ventanaEdicion1.add(btnCancelar2);
			ventanaEdicion1.setSize(200,300);
			ventanaEdicion1.setResizable(false);
			ventanaEdicion1.setLocationRelativeTo(null);
			ventanaEdicion1.setVisible(true);
		}
		else if(evento.getSource().equals(btnAceptar))
		{
			
			ventanaEdicion2.setLayout(new FlowLayout());
			
			// Capturar los datos del elemento elegido del List
			String[] tiempo = listDetallesFactura1.getSelectedItem().split(":");			
			ventanaEdicion2.add(lblHoraFactura);
			txtHora.setText(tiempo[1]);
			ventanaEdicion2.add(txtHora);
			ventanaEdicion2.add(lblMinutoFactura);
			txtMinuto.setText(tiempo[2]);
			ventanaEdicion2.add(txtMinuto);
			ventanaEdicion2.add(lblSegundoFactura);
			txtSegundo.setText(tiempo[3]);			
			ventanaEdicion2.add(txtSegundo);
			ventanaEdicion2.add(btnAceptar2);
			ventanaEdicion2.add(btnCancelar3);
			ventanaEdicion2.setSize(200,300);
			ventanaEdicion2.setResizable(false);
			ventanaEdicion2.setLocationRelativeTo(null);
			ventanaEdicion2.setVisible(true);
		}
		else if(evento.getSource().equals(btnAceptar2))
		{
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			// Hacer un SELECT * FROM clientes
			sentencia = "UPDATE facturas SET fechaFactura='"+txtAnyo.getText()+ "-" + txtMes.getText()+ "-" + txtDia.getText()
			+"', horaFactura='"+ txtHora.getText() +":" + txtMinuto.getText() +":" + txtSegundo.getText() +"' WHERE idFactura="+txtId.getText();
			Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
			System.out.println(sentencia);
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				statement.executeUpdate(sentencia);
				lblMensaje.setText("Actualización de cliente Correcta");
			}
			catch (SQLException sqle)
			{
				lblMensaje.setText("Error");
			}
			finally
			{
				dlgMensajeModificacionFactura.setLayout(new FlowLayout());
				
				dlgMensajeModificacionFactura.setSize(150,100);
				dlgMensajeModificacionFactura.setResizable(false);
				dlgMensajeModificacionFactura.setLocationRelativeTo(null);
				dlgMensajeModificacionFactura.add(lblMensaje);
				dlgMensajeModificacionFactura.setVisible(true);
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
		ventanaEdicion1.setVisible(false);
		ventanaEdicion2.setVisible(false);
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