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

import com.itextpdf.text.Document;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ConsultaFactura implements ActionListener, WindowListener
{
	// Ventana Consulta de Clientes
	Frame frmConsultaFacturas = new Frame("Consulta Facturas");
	TextArea listadoFacturas = new TextArea(5, 40);
	Button btnPdfFacturas = new Button("PDF");
	
	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	ConsultarPDFFactura cpdf;
	

	public ConsultaFactura()
	{
		frmConsultaFacturas.setLayout(new FlowLayout());
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM facturas";
		// La información está en ResultSet
		// Recorrer el RS y por cada registro,
		// meter una línea en el TextArea
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
			listadoFacturas.selectAll();
			listadoFacturas.setText("");
			listadoFacturas.append("id\tFecha\t\t\tHora\n");
			while(rs.next())
			{
				String[] fechaEuropea = rs.getString("fechaFactura").split("-");
				listadoFacturas.append(rs.getInt("idFactura")+"\t"+fechaEuropea[2]+"\t"+fechaEuropea[1]+"\t"+fechaEuropea[0]+"\t"+rs.getString("horaFactura")+"\n");
			}
		}
		catch (SQLException sqle)
		{
			listadoFacturas.setText("Se ha producido un error en la consulta");
		}
		finally
		{

		}
		btnPdfFacturas.addActionListener(this);
		listadoFacturas.setEditable(false);
		frmConsultaFacturas.add(listadoFacturas);
		frmConsultaFacturas.add(btnPdfFacturas);
		frmConsultaFacturas.setSize(350,175);
		frmConsultaFacturas.setResizable(false);
		frmConsultaFacturas.setLocationRelativeTo(null);
		frmConsultaFacturas.addWindowListener(this);
		frmConsultaFacturas.setVisible(true);
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
		if(frmConsultaFacturas.isActive())
		{
			frmConsultaFacturas.setVisible(false);
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
		if(evento.getSource().equals(btnPdfFacturas))
		{
			connection = bd.conectar();
			cpdf = new ConsultarPDFFactura();
			// Realizar consulta, y sacar información
			ArrayList<String> datos = cpdf.consultarPDF(connection);
			// Crear el PDF
			Document documento = new Document();
			try
			{
				// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
				FileOutputStream ficheroPdf = new FileOutputStream("ConsultaFactura.pdf");
				PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
				documento.open();
				documento.add(new Paragraph("Listado de Facturas",
						FontFactory.getFont("arial", // fuente 
								22, // tamaño 
								Font.ITALIC, // estilo 
								BaseColor.CYAN)));
				documento.add(new Paragraph());
				PdfPTable tabla = new PdfPTable(3);
				tabla.addCell("Nº Factura");
				tabla.addCell("Fecha");
				tabla.addCell("Hora Factura");
				for(int i = 0; i < datos.size(); i++)
				{
					tabla.addCell(datos.get(i));
				}
				documento.add(tabla);
				documento.close();
				//Abrimos el archivo PDF recién creado
				try
				{
					File path = new File ("ConsultaFactura.pdf");
					Desktop.getDesktop().open(path);
					Guardarlog.guardar(Login.txtUsuario.getText(),"PDF de la consulta de las facturas creado");
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