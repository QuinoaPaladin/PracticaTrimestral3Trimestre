package es.studium.login;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AltaDetallesFactura implements ActionListener, WindowListener
{
	Frame frmAltaDetallesPaso1 = new Frame("Alta Detalles de Factura (1 de 2)");
	Frame frmAltaDetallesPaso2 = new Frame("Alta Detalles de Factura (2 de 2)");
	Label lblTotalIva = new Label("Total del IVA:");
	Label lblTotalCoste = new Label("Coste Total:");
	Label lblElegirFactura = new Label("Elegir Factura:");
	Label lblElegirProducto = new Label("Elegir Producto:");
	Label lblDetallesFactura = new Label("XXXXXXXXXXXXXXXX");
	TextField txtTotalIva = new TextField(20);
	TextField txtTotalCoste = new TextField(20);
	Choice choFacturas = new Choice();
	Choice choProductos = new Choice();
	Button btnSiguiente = new Button("Siguiente");
	Button btnFinalizar = new Button("Finalizar");
	Button btnAnadir = new Button("Añadir");
	TextArea txaListado = new TextArea(4,20);

	Dialog dlgMensaje = new Dialog(frmAltaDetallesPaso2, "Confirmación", true);
	Label lblMensaje = new Label("Alta de los Detalles Correcta");
	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	int idFacturaFK;

	public AltaDetallesFactura()
	{
		frmAltaDetallesPaso1.setLayout(new FlowLayout());
		frmAltaDetallesPaso1.add(lblElegirFactura);

		// Rellenar el Choice
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM proyectos
		sentencia = "SELECT * FROM facturas";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			choFacturas.removeAll();
			choFacturas.add("Seleccionar una factura");
			while(rs.next())
			{
				String[] fechaEuropea = rs.getString("fechaFactura").split("-");
				choFacturas.add(rs.getInt("idFactura")
						+"-"+fechaEuropea[2]
								+"-"+fechaEuropea[1]
										+"-"+fechaEuropea[0]
												+"-"+rs.getString("horaFactura"));
			}
		}
		catch (SQLException sqle)
		{
			System.out.println(sqle.getMessage());
		}
		frmAltaDetallesPaso1.add(choFacturas);
		btnAnadir.addActionListener(this);
		btnSiguiente.addActionListener(this);
		frmAltaDetallesPaso1.add(btnSiguiente);
		frmAltaDetallesPaso1.setSize(300,140);
		frmAltaDetallesPaso1.setResizable(false);
		frmAltaDetallesPaso1.setLocationRelativeTo(null);
		frmAltaDetallesPaso1.addWindowListener(this);
		frmAltaDetallesPaso1.setVisible(true);
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
		if(frmAltaDetallesPaso1.isActive())
		{
			frmAltaDetallesPaso1.setVisible(false);
		}
		if(frmAltaDetallesPaso2.isActive())
		{
			frmAltaDetallesPaso2.setVisible(false);
		}
		if(dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
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
		if(evento.getSource().equals(btnSiguiente))
		{
			// TODO Auto-generated method stub
			lblDetallesFactura.setText(choFacturas.getSelectedItem().split("-")[0]);
			idFacturaFK = Integer.parseInt(choFacturas.getSelectedItem().split("-")[0]);
			frmAltaDetallesPaso2.setLayout(new FlowLayout());
			frmAltaDetallesPaso2.add(lblDetallesFactura);


			// Rellenar el Choice
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			// Hacer un SELECT * FROM empleados
			sentencia = "SELECT * FROM productos";
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				rs = statement.executeQuery(sentencia);
				choProductos.removeAll();
				choProductos.add("Seleccionar un producto");
				while(rs.next())
				{
					choProductos.add(rs.getInt("idProducto")
							+"-"+rs.getString("nombreProducto")
							+"-"+rs.getString("precioProducto")
							+"-"+rs.getString("ivaProducto"));
				}
			}
			catch (SQLException sqle)
			{
				System.out.println(sqle.getMessage());
			}


			frmAltaDetallesPaso2.add(lblTotalIva);
			frmAltaDetallesPaso2.add(txtTotalIva);
			frmAltaDetallesPaso2.add(lblTotalCoste);
			frmAltaDetallesPaso2.add(txtTotalCoste);
			frmAltaDetallesPaso2.add(lblElegirProducto);
			frmAltaDetallesPaso2.add(choProductos);
			frmAltaDetallesPaso2.add(btnAnadir);
			// Hacer un SELECT * FROM empleados
			sentencia = "SELECT * FROM detallesFacturas WHERE idFacturaFK = "+idFacturaFK;
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				rs = statement.executeQuery(sentencia);
				txaListado.selectAll();
				txaListado.setText("");
				while(rs.next())
				{
					txaListado.append(rs.getInt("idDetallesFactura")
							+"-"+rs.getString("totalIVA")
							+"-"+rs.getString("totalCoste")
							+"-"+rs.getString("idFacturaFK")
							+"-"+rs.getString("idProductoFK"));
				}
			}
			catch (SQLException sqle)
			{
				System.out.println(sqle.getMessage());
			}

			frmAltaDetallesPaso2.add(txaListado);
			frmAltaDetallesPaso2.setSize(230,350);
			frmAltaDetallesPaso2.setResizable(false);
			frmAltaDetallesPaso2.setLocationRelativeTo(null);
			frmAltaDetallesPaso2.addWindowListener(this);
			frmAltaDetallesPaso2.setVisible(true);
		}

		if(evento.getSource().equals(btnAnadir))
		{
			if(choProductos.getSelectedIndex()!=0)
			{
				bd = new BaseDatos();
				connection = bd.conectar();
				try
				{
					//Crear una sentencia
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					//Crear un objeto ResultSet para guardar lo obtenido
					//y ejecutar la sentencia SQL
					sentencia = "INSERT INTO detallesFacturas VALUES (null, "+ txtTotalIva.getText() +","+ txtTotalCoste.getText() +", " + idFacturaFK + ", "+ choProductos.getSelectedItem().split("-")[0] + ")";
					Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
					statement.executeUpdate(sentencia);
					lblMensaje.setText("Asignación Correcta");
				}
				catch (SQLException sqle)
				{
					lblMensaje.setText("Error en Alta");
				}
				finally
				{
					dlgMensaje.setLayout(new FlowLayout());
					dlgMensaje.addWindowListener(this);
					dlgMensaje.setSize(150,100);
					dlgMensaje.setResizable(false);
					dlgMensaje.setLocationRelativeTo(null);
					dlgMensaje.add(lblMensaje);
					dlgMensaje.setVisible(true);
					bd.desconectar(connection);
				}
			}
			//dialogo diciendo que faltan datos
			else
			{}
		}
	}
}