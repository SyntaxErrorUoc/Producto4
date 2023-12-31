package modelo;


import com.mysql.cj.Session;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SyntaxError
 * @version 2.0.1
 */
@Entity
@Table(name = "pedido")
public class Pedido {
	@Id
	@Column(name = "numeroPedido")
	private int numeroPedido;
	@Column(name = "fechaHoraPedido")
	private LocalDateTime fechaHoraPedido;
	@Column(name = "costeEnvio")
	private double costeEnvio;
	@Column(name = "cantidad")
	private int cantidad;
	@Column(name = "enviado")
	private boolean enviado;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "Articulo_CP")
	private  Articulo articulo;
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ClienteStandard_mail")
	private Cliente cliente;


	/**
	 * Constructor de la clase con 7 parametros
	 * @param numeroPedido recibe un tipo int
	 * @param fechaHoraPedido recibe un tipo LocalDateTime
	 * @param cliente recibe un tipo Cliente
	 * @param articulo recibe un tipo Articulo
	 * @param cantidad recibe un tipo int
	 * @param enviado recibe un tipo boolean
	 * @param costeEnvio recibe un tipo double
	 */
	public Pedido(int numeroPedido, LocalDateTime fechaHoraPedido, Cliente cliente, Articulo articulo, int cantidad,
				  boolean enviado, double costeEnvio) {
		super();
		this.numeroPedido = numeroPedido;
		this.fechaHoraPedido = fechaHoraPedido;
		this.cliente = cliente;
		this.articulo = articulo;
		this.cantidad = cantidad;
		this.enviado = enviado;
		this.costeEnvio = costeEnvio;
	}

	/**
	 * Constructor de la clase vacio
	 */
	public Pedido() {
	}

	/**
	 * Getter del numeroPedido
	 * @return devuelve un int
	 */
	public int getNumeroPedido() {
		return numeroPedido;
	}

	/**
	 * Getter del fechaHoraPedido
	 * @return devuelve Un LocalDateTime
	 */
	public LocalDateTime getFechaHoraPedido() {
		return fechaHoraPedido;
	}

	/**
	 * Getter del cliente
	 * @return devuelve un tipo Cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}


	/**
	 * Getter del articulo
	 * @return devuelve un tipo Articulo
	 */
	public Articulo getArticulo() {
		return articulo;
	}

	/**
	 * Getter de cantidad
	 * @return devuelve un int
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Getter de enviado
	 * @return devuelve un tipo boolean
	 */
	public boolean isEnviado() {
		return enviado;
	}

	/**
	 * Setter de numeroPedido
	 * @param numeroPedido de tipo int
	 */

	public void setNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	/**
	 * Setter de fechaHoraPedido
	 * @param fechaHoraPedido de tipo LocalDateTime
	 */
	public void setFechaHoraPedido(LocalDateTime fechaHoraPedido) {
		this.fechaHoraPedido = fechaHoraPedido;
	}

	/**
	 * Setter de cliente
	 * @param cliente de tipo Cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Setter de articulo
	 * @param articulo de tipo Articulo
	 */
	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	/**
	 * Setter de cantidad
	 * @param cantidad de tipo int
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Setter de enviado
	 * @param enviado de tipo boolean
	 */
	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}

	/**
	 * Metodo para pedidoEniado
	 * @return devuelve un boolean
	 */
	public boolean pedidoEnviado() {
		return enviado;
	}

	/**
	 * Metodo de calculo para precioEnvio
	 * @param costeEnvio recibe un double
	 * @return devuelve un tipo doble
	 */
	public double precioEnvio(double costeEnvio ) {

		if (cliente instanceof ClientePremium) {
			costeEnvio -= (costeEnvio * cliente.descuentoEnv()/100);
		}
		return costeEnvio;
	}

	public double getCosteEnvio() {
		return costeEnvio;
	}

	public void setCosteEnvio(double costeenvio) {
		this.costeEnvio = costeenvio;
	}

	/**
	 * Metodo para el calculo del precioTotal
	 * @return devuele un double
	 */
	public double precioTotal (){

		double total= 0.0;
		total = precioEnvio(cantidad * articulo.getPrecio())+precioEnvio(costeEnvio);
		return total;
	}

	/**
	 * Metodo para mostrar MailCliente
	 * @return devuelve un String
	 */
	public String getMailCliente(){
		return cliente.getCorreoElectronico();
	}

	/**
	 * Metodo para convertir fecha y Hora a un String
	 * @return devuelve un String
	 */
	public String fechaHoraReturn(){

		String fechahora, fecha, hora;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String formatHoraDate = fechaHoraPedido.format(formatter);
		String[] parts = formatHoraDate.split("T");
		fecha = parts[0];
		hora = parts[1];
		fechahora = fecha + "-"+hora;
		return fechahora;

	}

	/**
	 * Metodo para que enviado nos muestre un String y no un boolean
	 * @return devuelve un String
	 */
	public String enviadoParse(){
		if (enviado){
			return "Enviado";
		}else{
			return "Falta de envio";
		}
	}


	@Override
	public String toString() {
		return "\n-----------------" + "\nPedido numero : " + numeroPedido + "\n-----------------" + "\nFecha y hora del pedido :" + fechaHoraReturn() + "\nCliente del pedido :"
				+ cliente.getNombre() + "\nMail del cliente :" + cliente.getCorreoElectronico() + "\n" + "\t" + articulo.toString().replaceAll("\n", "\n\t") + "\nCantidad del articulo :" + cantidad + "\nEstado del envío :" + enviadoParse() + "\nCosteEnvío :"
				+ precioEnvio(costeEnvio) + "\nPrecio total del pedido :" + precioTotal() + "\n";
	}

}