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

public class BajaDetallesFactura implements ActionListener, WindowListener
{
	
		// Ventana de Borrado de Cliente
		Frame frmBajaDetallesFactura = new Frame("Baja de Detalles de la Factura");
		Label lblMensajeBajaDetallesFactura = new Label("Seleccionar Detalles de la Factura:");
		Choice choDetallesFactura = new Choice();
		Button btnBorrarDetallesFactura = new Button("Borrar");
		Dialog dlgSeguroDetallesFactura = new Dialog(frmBajaDetallesFactura, "¿Seguro?", true);
		Label lblSeguroDetallesFactura = new Label("¿Está seguro de borrar?");
		Button btnSiSeguroDetallesFactura = new Button("Sí");
		Button btnNoSeguroDetallesFactura = new Button("No");
		Dialog dlgConfirmacionBajaDetallesFactura = new Dialog(frmBajaDetallesFactura, "Baja Producto", true);
		Label lblConfirmacionBajaDetallesFactura = new Label("Baja de Detalles de la Factura correcta");

		BaseDatos bd;
		String sentencia = "";
		String sentenciaFactura = "";
		String sentenciaProducto = "";
		Connection connection = null;
		Statement statement = null;
		Statement statementFacturaFK = null;
		Statement statementProductoFK = null;
		ResultSet rs = null;
		ResultSet rsFactura = null;
		ResultSet rsProducto = null;

		public BajaDetallesFactura()
		{
			frmBajaDetallesFactura.setLayout(new FlowLayout());
			frmBajaDetallesFactura.add(lblMensajeBajaDetallesFactura);
			// Rellenar el Choice
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
				choDetallesFactura.removeAll();
				while(rs.next())
				{
					choDetallesFactura.add(rs.getInt("idDetallesFactura") +"-"+rs.getString("totalIVA") + "-" +rs.getString("totalCoste")+ "-" +rs.getString("idFacturaFK")+ "-" +rs.getString("idProductoFK"));
				}
			}
			catch (SQLException sqle)
			{

			}

			frmBajaDetallesFactura.add(choDetallesFactura);
			btnBorrarDetallesFactura.addActionListener(this);
			frmBajaDetallesFactura.add(btnBorrarDetallesFactura);

			frmBajaDetallesFactura.setSize(250,140);
			frmBajaDetallesFactura.setResizable(false);
			frmBajaDetallesFactura.setLocationRelativeTo(null);
			frmBajaDetallesFactura.addWindowListener((WindowListener) this);
			frmBajaDetallesFactura.setVisible(true);
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
			if(frmBajaDetallesFactura.isActive())
			{
				frmBajaDetallesFactura.setVisible(false);
			}
			else if(dlgSeguroDetallesFactura.isActive())
			{
				dlgSeguroDetallesFactura.setVisible(false);
			}
			else if(dlgConfirmacionBajaDetallesFactura.isActive())
			{
				btnSiSeguroDetallesFactura.removeActionListener(this);
				btnNoSeguroDetallesFactura.removeActionListener(this);
				btnBorrarDetallesFactura.removeActionListener(this);
				dlgConfirmacionBajaDetallesFactura.setVisible(false);
				dlgSeguroDetallesFactura.setVisible(false);
				frmBajaDetallesFactura.setVisible(false);
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
			if(evento.getSource().equals(btnBorrarDetallesFactura))
			{
				dlgSeguroDetallesFactura.setLayout(new FlowLayout());
				dlgSeguroDetallesFactura.addWindowListener(this);
				dlgSeguroDetallesFactura.setSize(180,100);
				dlgSeguroDetallesFactura.setResizable(false);
				dlgSeguroDetallesFactura.setLocationRelativeTo(null);
				dlgSeguroDetallesFactura.add(lblSeguroDetallesFactura);
				btnSiSeguroDetallesFactura.addActionListener(this);
				dlgSeguroDetallesFactura.add(btnSiSeguroDetallesFactura);
				btnNoSeguroDetallesFactura.addActionListener(this);
				dlgSeguroDetallesFactura.add(btnNoSeguroDetallesFactura);
				dlgSeguroDetallesFactura.setVisible(true);
			}
			else if(evento.getSource().equals(btnNoSeguroDetallesFactura))
			{
				dlgSeguroDetallesFactura.setVisible(false);
			}
			else if(evento.getSource().equals(btnSiSeguroDetallesFactura))
			{
				// Conectar
				bd = new BaseDatos();
				connection = bd.conectar();
				// Hacer un DELETE FROM clientes WHERE idCliente = X
				String[] elegido = choDetallesFactura.getSelectedItem().split("-");
				sentenciaFactura = "UPDATE detallesfacturas SET idFacturaFK=null WHERE idFacturaFK = " +elegido[3];
				sentenciaProducto = "UPDATE detallesfacturas SET idProductoFK=null WHERE idProductoFK = " +elegido[4];
				sentencia = "DELETE FROM detallesfacturas WHERE idDetallesFactura = "+elegido[0];
				try
				{
					//Crear una sentencia
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					statementFacturaFK = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					statementProductoFK = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					Guardarlog.guardar(Login.txtUsuario.getText(),sentenciaFactura);
					Guardarlog.guardar(Login.txtUsuario.getText(),sentenciaProducto);
					Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
					statement.executeUpdate(sentenciaFactura);
					statement.executeUpdate(sentenciaProducto);
					statement.executeUpdate(sentencia);
					lblConfirmacionBajaDetallesFactura.setText("Baja de Detalles de la Factura Correcta");
				}
				catch (SQLException sqle)
				{
					lblConfirmacionBajaDetallesFactura.setText("Error en Baja");
				}
				finally
				{
					dlgConfirmacionBajaDetallesFactura.setLayout(new FlowLayout());
					dlgConfirmacionBajaDetallesFactura.addWindowListener(this);
					dlgConfirmacionBajaDetallesFactura.setSize(200,100);
					dlgConfirmacionBajaDetallesFactura.setResizable(false);
					dlgConfirmacionBajaDetallesFactura.setLocationRelativeTo(null);
					dlgConfirmacionBajaDetallesFactura.add(lblConfirmacionBajaDetallesFactura);
					dlgConfirmacionBajaDetallesFactura.setVisible(true);
					


				}
			}
		}
	}

