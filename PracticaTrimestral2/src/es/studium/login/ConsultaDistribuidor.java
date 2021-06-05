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

public class ConsultaDistribuidor implements ActionListener, WindowListener
{
	// Ventana Consulta de Clientes
	Frame frmConsultaDistribuidores = new Frame("Consulta Distribuidores");
	TextArea listadoDistribuidores = new TextArea(4, 30);
	Button btnPdfDistribuidores = new Button("PDF");
	
	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	ConsultarPDFDistribuidor cpdf;

	public ConsultaDistribuidor()
	{
		frmConsultaDistribuidores.setLayout(new FlowLayout());
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM distribuidores";
		// La información está en ResultSet
		// Recorrer el RS y por cada registro,
		// meter una línea en el TextArea
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			Guardarlog.guardar(Login.txtUsuario.getText(),sentencia);
			rs = statement.executeQuery(sentencia);
			listadoDistribuidores.selectAll();
			listadoDistribuidores.setText("");
			listadoDistribuidores.append("id\tNombre\tUbicacion\n");
			while(rs.next())
			{
				listadoDistribuidores.append(rs.getInt("idDistribuidor")+"\t"+rs.getString("nombreDistribuidor")+"\t"+rs.getString("ubicacionDistribuidor")+"\n");
			}
		}
		catch (SQLException sqle)
		{
			listadoDistribuidores.setText("Se ha producido un error en la consulta");
		}
		finally
		{

		}
		btnPdfDistribuidores.addActionListener(this);
		listadoDistribuidores.setEditable(false);
		frmConsultaDistribuidores.add(listadoDistribuidores);
		frmConsultaDistribuidores.add(btnPdfDistribuidores);
		frmConsultaDistribuidores.setSize(250,175);
		frmConsultaDistribuidores.setResizable(false);
		frmConsultaDistribuidores.setLocationRelativeTo(null);
		frmConsultaDistribuidores.addWindowListener(this);
		frmConsultaDistribuidores.setVisible(true);
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
		if(frmConsultaDistribuidores.isActive())
		{
			frmConsultaDistribuidores.setVisible(false);
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
		if(evento.getSource().equals(btnPdfDistribuidores))
		{
			connection = bd.conectar();
			cpdf = new ConsultarPDFDistribuidor();
			// Realizar consulta, y sacar información
			ArrayList<String> datos = cpdf.consultarPDF(connection);
			// Crear el PDF
			Document documento = new Document();
			try
			{
				// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
				FileOutputStream ficheroPdf = new FileOutputStream("ConsultaDistribuidor.pdf");
				PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
				documento.open();
				documento.add(new Paragraph("Listado de Facturas",
						FontFactory.getFont("arial", // fuente 
								22, // tamaño 
								Font.ITALIC, // estilo 
								BaseColor.CYAN)));
				documento.add(new Paragraph());
				PdfPTable tabla = new PdfPTable(3);
				tabla.addCell("Nº Distribuidor");
				tabla.addCell("Nombre");
				tabla.addCell("Ubicacion");
				for(int i = 0; i < datos.size(); i++)
				{
					tabla.addCell(datos.get(i));
				}
				documento.add(tabla);
				documento.close();
				//Abrimos el archivo PDF recién creado
				try
				{
					File path = new File ("ConsultaDistribuidor.pdf");
					Desktop.getDesktop().open(path);
					Guardarlog.guardar(Login.txtUsuario.getText(),"PDF de la consulta de los distribuidores creado");
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