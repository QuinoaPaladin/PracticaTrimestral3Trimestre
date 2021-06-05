package es.studium.login;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ConsultaDetallesFactura implements ActionListener, WindowListener
{
	// Ventana Consulta de Proyectos
	Frame frmConsultaDetallesFactura = new Frame("Consulta Detalles Factura");
	TextArea listadoDetallesFactura = new TextArea(4, 50);
	Button btnPdfDetallesFactura = new Button("PDF");
	
	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	ConsultarPDFDetalles cpdf;
	
	public ConsultaDetallesFactura()
	{
		frmConsultaDetallesFactura.setLayout(new FlowLayout());
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM Proyectos
		sentencia = "SELECT * FROM detallesFacturas";
		// La información está en ResultSet
		// Recorrer el RS y por cada registro,
		// meter una línea en el TextArea
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
			rs = statement.executeQuery(sentencia);
			listadoDetallesFactura.selectAll();
			listadoDetallesFactura.setText("");
			listadoDetallesFactura.append("id\tTotalIva\tTotalCoste\tidFacturaFK\tidProductoFK\n");
			while(rs.next())
			{
				listadoDetallesFactura.append(rs.getInt("idDetallesFactura")
				+"\t"+rs.getString("totalIVA")
				+"\t"+rs.getString("totalCoste")
				+"\t"+"\t"+rs.getString("idFacturaFK")
				+"\t"+"\t"+rs.getString("idProductoFK"
				)+"\n");
			}
			
			
			
		}
		catch (SQLException sqle)
		{
			listadoDetallesFactura.setText("Se ha producido un error en la consulta");
		}
		finally
		{

		}
		btnPdfDetallesFactura.addActionListener(this);
		listadoDetallesFactura.setEditable(false);
		frmConsultaDetallesFactura.add(listadoDetallesFactura);
		frmConsultaDetallesFactura.add(btnPdfDetallesFactura);
		frmConsultaDetallesFactura.setSize(400,180);
		frmConsultaDetallesFactura.setResizable(false);
		frmConsultaDetallesFactura.setLocationRelativeTo(null);
		frmConsultaDetallesFactura.addWindowListener(this);
		frmConsultaDetallesFactura.setVisible(true);
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
		if(frmConsultaDetallesFactura.isActive())
		{
			frmConsultaDetallesFactura.setVisible(false);
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
		if(evento.getSource().equals(btnPdfDetallesFactura))
		{
			connection = bd.conectar();
			cpdf = new ConsultarPDFDetalles();
			// Realizar consulta, y sacar información
			ArrayList<String> datos = cpdf.consultarPDF(connection);
			// Crear el PDF
			Document documento = new Document();
			try
			{
				// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
				FileOutputStream ficheroPdf = new FileOutputStream("ConsultaDetalles.pdf");
				PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
				documento.open();
				documento.add(new Paragraph("Listado de Facturas",
						FontFactory.getFont("arial", // fuente 
								22, // tamaño 
								Font.ITALIC, // estilo 
								BaseColor.CYAN)));
				documento.add(new Paragraph());
				PdfPTable tabla = new PdfPTable(5);
				tabla.addCell("Nº Detalles");
				tabla.addCell("Total IVA");
				tabla.addCell("Total Coste");
				tabla.addCell("NºFactura");
				tabla.addCell("NºProducto");
				for(int i = 0; i < datos.size(); i++)
				{
					tabla.addCell(datos.get(i));
				}
				documento.add(tabla);
				documento.close();
				//Abrimos el archivo PDF recién creado
				try
				{
					File path = new File ("ConsultaDetalles.pdf");
					Desktop.getDesktop().open(path);
					Guardarlog.guardar(Login.txtUsuario.getText(),"PDF de la consulta de los detalles de la factura creado");
				}
				catch(IOException ex)
				{
					System.out.println("Se ha producido un error al abrir el archivo PDF");
				}
			}
			catch (Exception e)
			{
				System.out.println("Se ha producido un error al abrir el archivo PDF");
			}
			// Cerrar la conexión
			bd.desconectar(connection);
		}
	}
}