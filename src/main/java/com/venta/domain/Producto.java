package com.venta.domain;

import java.util.Objects;

public class Producto {
	
	private String idProd;
    private String nombre;
    private int cantidad;
    private String precioUnitario;
    private long subtotal;
	/**
	 * @return the idProd
	 */
	public String getIdProd() {
		return idProd;
	}
	/**
	 * @param idProd the idProd to set
	 */
	public void setIdProd(String idProd) {
		this.idProd = idProd;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the precioUnitario
	 */
	public String getPrecioUnitario() {
		return precioUnitario;
	}
	/**
	 * @param precioUnitario the precioUnitario to set
	 */
	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	/**
	 * @return the subtotal
	 */
	public long getSubtotal() {
		return subtotal;
	}
	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(long subtotal) {
		this.subtotal = subtotal;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cantidad, idProd, nombre, precioUnitario, subtotal);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return cantidad == other.cantidad && Objects.equals(idProd, other.idProd)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(precioUnitario, other.precioUnitario)
				&& subtotal == other.subtotal;
	}
	@Override
	public String toString() {
		return "Producto [idProd=" + idProd + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precioUnitario="
				+ precioUnitario + ", subtotal=" + subtotal + "]";
	}
    
        
 
	

}
