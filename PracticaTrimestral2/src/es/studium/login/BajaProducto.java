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

public class BajaProducto implements ActionListener, WindowListener
{
	// Ventana de Borrado de Cliente
		Frame frmBajaProducto = new Frame("Baja de Producto");
		Label lblMensajeBajaProducto = new Label("Seleccionar el producto:");
		Choice choProductos = new Choice();
		Button btnBorrarProductos = new Button("Borrar");
		Dialog dlgSeguroProducto = new Dialog(frmBajaProducto, "¿Seguro?", true);
		Label lblSeguroProducto = new Label("¿Está seguro de borrar?");
		Button btnSiSeguroProducto = new Button("Sí");
		Button btnNoSeguroProducto = new Button("No");
		Dialog dlgConfirmacionBajaProducto = new Dialog(frmBajaProducto, "Baja Producto", true);
		Label lblConfirmacionBajaProducto = new Label("Baja de producto correcta");

		BaseDatos bd;
		String sentencia = "";
		String sentenciaDistribuidor = "";
		String sentenciaProducto = "";
		Connection connection = null;
		Statement statement = null;
		Statement statementDistribuidorFK = null;
		Statement statementProductoFK = null;
		ResultSet rs = null;
		ResultSet rsDistribuidor = null;
		ResultSet rsProducto = null;

		public BajaProducto()
		{
			frmBajaProducto.setLayout(new FlowLayout());
			frmBajaProducto.add(lblMensajeBajaProducto);
			// Rellenar el Choice
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			// Hacer un SELECT * FROM clientes
			sentencia = "SELECT * FROM productos";
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);		
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				rs = statement.executeQuery(sentencia);
				choProductos.removeAll();
				while(rs.next())
				{
					choProductos.add(rs.getInt("idProducto") +"-"+rs.getString("nombreProducto") + "-" +rs.getString("precioProducto")+ "-" +rs.getString("ivaProducto")+ "-" +rs.getString("idDistribuidorFK"));
				}
			}
			catch (SQLException sqle)
			{

			}
			
			frmBajaProducto.add(choProductos);
			btnBorrarProductos.addActionListener(this);
			frmBajaProducto.add(btnBorrarProductos);

			frmBajaProducto.setSize(250,140);
			frmBajaProducto.setResizable(false);
			frmBajaProducto.setLocationRelativeTo(null);
			frmBajaProducto.addWindowListener((WindowListener) this);
			frmBajaProducto.setVisible(true);
		}
		
		
		public void windowActivated(WindowEvent e)
		{
			// TODO Auto-generated method stub

		}
		
		public void windowClosed(WindowEvent e)
		{
			// TODO Auto-generated method stub

		}
		
		
		public void windowClosing(WindowEvent e)
		{
			// TODO Auto-generated method stub
			if(frmBajaProducto.isActive())
			{
				frmBajaProducto.setVisible(false);
			}
			else if(dlgSeguroProducto.isActive())
			{
				dlgSeguroProducto.setVisible(false);
			}
			else if(dlgConfirmacionBajaProducto.isActive())
			{
				btnSiSeguroProducto.removeActionListener(this);
				btnNoSeguroProducto.removeActionListener(this);
				btnBorrarProductos.removeActionListener(this);
				dlgConfirmacionBajaProducto.setVisible(false);
				dlgSeguroProducto.setVisible(false);
				frmBajaProducto.setVisible(false);
			}
		}
		
		public void windowDeactivated(WindowEvent e)
		{
			// TODO Auto-generated method stub

		}
		
		public void windowDeiconified(WindowEvent e)
		{
			// TODO Auto-generated method stub

		}
		
		public void windowIconified(WindowEvent e)
		{
			// TODO Auto-generated method stub

		}
		
		public void windowOpened(WindowEvent e)
		{
			// TODO Auto-generated method stub

		}
		
		public void actionPerformed(ActionEvent evento)
		{
			// TODO Auto-generated method stub
			if(evento.getSource().equals(btnBorrarProductos))
			{
				dlgSeguroProducto.setLayout(new FlowLayout());
				dlgSeguroProducto.addWindowListener(this);
				dlgSeguroProducto.setSize(180,100);
				dlgSeguroProducto.setResizable(false);
				dlgSeguroProducto.setLocationRelativeTo(null);
				dlgSeguroProducto.add(lblSeguroProducto);
				btnSiSeguroProducto.addActionListener(this);
				dlgSeguroProducto.add(btnSiSeguroProducto);
				btnNoSeguroProducto.addActionListener(this);
				dlgSeguroProducto.add(btnNoSeguroProducto);
				dlgSeguroProducto.setVisible(true);
			}
			else if(evento.getSource().equals(btnNoSeguroProducto))
			{
				dlgSeguroProducto.setVisible(false);
			}
			else if(evento.getSource().equals(btnSiSeguroProducto))
			{
				// Conectar
				bd = new BaseDatos();
				connection = bd.conectar();
				// Hacer un DELETE FROM clientes WHERE idCliente = X
				String[] elegido = choProductos.getSelectedItem().split("-");
				sentenciaDistribuidor = "UPDATE productos SET idDistribuidorFK=null WHERE idProducto = " +elegido[4];
				sentenciaProducto = "UPDATE detallesfacturas set idProductoFK=null WHERE idProductoFK = " +elegido[0];
				sentencia = "DELETE FROM productos WHERE idProducto = "+elegido[0];
				try
				{
					//Crear una sentencia
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					statementDistribuidorFK = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					statementProductoFK = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					Guardarlog.guardar(Login.txtUsuario.getText(),sentenciaDistribuidor);
					Guardarlog.guardar(Login.txtUsuario.getText(),sentenciaProducto);
					Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
					statement.executeUpdate(sentenciaDistribuidor);
					statement.executeUpdate(sentenciaProducto);
					statement.executeUpdate(sentencia);
					lblConfirmacionBajaProducto.setText("Baja de Producto Correcta");
				}
				catch (SQLException sqle)
				{
					lblConfirmacionBajaProducto.setText("Error en Baja");
				}
				finally
				{
					dlgConfirmacionBajaProducto.setLayout(new FlowLayout());
					dlgConfirmacionBajaProducto.addWindowListener(this);
					dlgConfirmacionBajaProducto.setSize(200,100);
					dlgConfirmacionBajaProducto.setResizable(false);
					dlgConfirmacionBajaProducto.setLocationRelativeTo(null);
					dlgConfirmacionBajaProducto.add(lblConfirmacionBajaProducto);
					dlgConfirmacionBajaProducto.setVisible(true);
					
					
					
				}
			}
		}	
	}

