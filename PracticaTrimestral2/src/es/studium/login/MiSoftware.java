package es.studium.login;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class MiSoftware implements WindowListener, ActionListener
{
	// Ventana Principal
	Frame ventana = new Frame("Mi Software");

	MenuBar mnBar = new MenuBar();

	Menu mnuFacturas = new Menu("Facturas");
	MenuItem mniAltaFactura = new MenuItem("Alta");
	MenuItem mniBajaFactura = new MenuItem("Baja");
	MenuItem mniModificacionFactura = new MenuItem("Modificación");
	MenuItem mniConsultaFactura = new MenuItem("Consulta");

	Menu mnuDistribuidores = new Menu("Distribuidores");
	MenuItem mniAltaDistribuidor = new MenuItem("Alta");
	MenuItem mniBajaDistribuidor = new MenuItem("Baja");
	MenuItem mniModificacionDistribuidor = new MenuItem("Modificación");
	MenuItem mniConsultaDistribuidor = new MenuItem("Consulta");

	Menu mnuProductos = new Menu("Productos");
	MenuItem mniAltaProductos = new MenuItem("Alta");
	MenuItem mniBajaProducto = new MenuItem("Baja");
	MenuItem mniModificacionProducto = new MenuItem("Modificación");
	MenuItem mniConsultaProductos= new MenuItem("Consulta");

	Menu mnuDetallesFacturas = new Menu("Detalles de facturas");
	MenuItem mniAltaDetallesFactura = new MenuItem("Alta");
	MenuItem mniBajaDetallesFactura = new MenuItem("Baja");
	MenuItem mniModificacionDetallesFactura = new MenuItem("Modificación");
	MenuItem mniConsultaDetallesFactura = new MenuItem("Consulta");
	
	Menu mnuOpciones = new Menu("Opciones");
	MenuItem mniAyuda = new MenuItem("Ayuda");

	public MiSoftware(int tipo)
	{
		ventana.setLayout(new FlowLayout());
		mniAltaFactura.addActionListener(this);
		mnuFacturas.add(mniAltaFactura);
		
		if(tipo==0) // ¿Es administrador?
		{
			mniBajaFactura.addActionListener(this);
			mnuFacturas.add(mniBajaFactura);
			mniModificacionFactura.addActionListener(this);
			mnuFacturas.add(mniModificacionFactura);
			mniConsultaFactura.addActionListener(this);
			mnuFacturas.add(mniConsultaFactura);
		}
		mnBar.add(mnuFacturas);

		mniAltaDistribuidor.addActionListener(this);
		mnuDistribuidores.add(mniAltaDistribuidor);
		
		if(tipo==0)
		{
			mniBajaDistribuidor.addActionListener(this);
			mnuDistribuidores.add(mniBajaDistribuidor);
			mniModificacionDistribuidor.addActionListener(this);
			mnuDistribuidores.add(mniModificacionDistribuidor);
			mniConsultaDistribuidor.addActionListener(this);
			mnuDistribuidores.add(mniConsultaDistribuidor);
		}
		mnBar.add(mnuDistribuidores);

		mniAltaProductos.addActionListener(this);
		mnuProductos.add(mniAltaProductos);
	
		if(tipo==0) // ¿Es administrador?
		{
			mniBajaProducto.addActionListener(this);
			mnuProductos.add(mniBajaProducto);
			mniModificacionProducto.addActionListener(this);
			mnuProductos.add(mniModificacionProducto);
			mniConsultaProductos.addActionListener(this);
			mnuProductos.add(mniConsultaProductos);
		}
		mnBar.add(mnuProductos);

		mniAltaDetallesFactura.addActionListener(this);
		mnuDetallesFacturas.add(mniAltaDetallesFactura);
	
		if(tipo==0) // ¿Es administrador?
		{	
			mniBajaDetallesFactura.addActionListener(this);
			mnuDetallesFacturas.add(mniBajaDetallesFactura);
			mniModificacionDetallesFactura.addActionListener(this);
			mnuDetallesFacturas.add(mniModificacionDetallesFactura);
			mniConsultaDetallesFactura.addActionListener(this);
			mnuDetallesFacturas.add(mniConsultaDetallesFactura);
		}
		mnBar.add(mnuDetallesFacturas);
		
		mniAyuda.addActionListener(this);
		mnuOpciones.add(mniAyuda);
		mnBar.add(mnuOpciones);
		

		ventana.setMenuBar(mnBar);

		ventana.setSize(500,180);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setVisible(true);
	}

	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		Guardarlog.guardar(Login.txtUsuario.getText(),"Salida del sistema");
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
	public void actionPerformed(ActionEvent evento)
	{
		// Facturas
		if(evento.getSource().equals(mniAltaFactura))
		{
			new AltaFactura();
		}
		else if(evento.getSource().equals(mniBajaFactura))
		{
			new BajaFactura();	
		}
		else if(evento.getSource().equals(mniModificacionFactura))
		{
			new ModificarFactura();
		}
		else if(evento.getSource().equals(mniConsultaFactura))
		{
			new ConsultaFactura();
		}
		// Distribuidores
		else if(evento.getSource().equals(mniAltaDistribuidor))
		{
			new AltaDistribuidor();
		}
		else if(evento.getSource().equals(mniBajaDistribuidor))
		{
			new BajaDistribuidor();	
		}
		else if(evento.getSource().equals(mniModificacionDistribuidor))
		{
			new ModificarDistribuidor();
		}
		else if(evento.getSource().equals(mniConsultaDistribuidor))
		{
			new ConsultaDistribuidor();
		}
		// Productos
		else if(evento.getSource().equals(mniAltaProductos))
		{
			new AltaProducto();
		}
		else if(evento.getSource().equals(mniBajaProducto))
		{
			new BajaProducto();
		}
		else if(evento.getSource().equals(mniModificacionProducto))
		{
			new ModificarProducto();
		}
		else if(evento.getSource().equals(mniConsultaProductos))
		{
			new ConsultaProducto();
		}
		// Detalles de facturas
		else if(evento.getSource().equals(mniAltaDetallesFactura))
		{
			new AltaDetallesFactura();
		}
		else if(evento.getSource().equals(mniBajaDetallesFactura))
		{
			new BajaDetallesFactura();
		}
		else if(evento.getSource().equals(mniModificacionDetallesFactura))
		{
			new ModificarDetallesFactura();
		}
		else if(evento.getSource().equals(mniConsultaDetallesFactura))
		{
			new ConsultaDetallesFactura();
		}
		else if(evento.getSource().equals(mniAyuda))
		{
			try
			{
				Runtime.getRuntime().exec("hh.exe Ayuda.chm");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}
}