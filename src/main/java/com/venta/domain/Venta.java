package com.venta.domain;

import java.util.Date;
import java.util.Objects;


public class Venta {
	
	private Integer idVenta;
    private Date fecha;
    private String pago;
    private Integer cantidad;
    private long precio;
	/**
	 * @return the idVenta
	 */
	public Integer getIdVenta() {
		return idVenta;
	}
	/**
	 * @param idVenta the idVenta to set
	 */
	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the pago
	 */
	public String getPago() {
		return pago;
	}
	/**
	 * @param pago the pago to set
	 */
	public void setPago(String pago) {
		this.pago = pago;
	}
	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the precio
	 */
	public long getPrecio() {
		return precio;
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(long precio) {
		this.precio = precio;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cantidad, fecha, idVenta, pago, precio);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venta other = (Venta) obj;
		return Objects.equals(cantidad, other.cantidad) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(idVenta, other.idVenta) && Objects.equals(pago, other.pago) && precio == other.precio;
	}
	@Override
	public String toString() {
		return "Venta [idVenta=" + idVenta + ", fecha=" + fecha + ", pago=" + pago + ", cantidad=" + cantidad
				+ ", precio=" + precio + "]";
	}
        

}
