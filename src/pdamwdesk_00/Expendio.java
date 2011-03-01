/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pdamwdesk_00;

import java.util.Vector;

/**
 *
 * @author Admin
 */
public class Expendio {

    private int Id;
    private String Nombre;
    private String Direccion;
    private String CodigoCliente;
    private int Compendio;
    private Vector VectorFechas;
    private int Visitado;


    public Expendio() {

    }


    public int GetId() {
        return this.Id;
    }

    public void SetId(int id) {
        this.Id = id;
    }


    public String GetNombre() {
        return this.Nombre;
    }

    public void SetNombre(String nombre) {
        this.Nombre = nombre;
    }


    public String GetDireccion() {
        return this.Direccion;
    }

    public void SetDireccion(String direccion) {
        this.Direccion = direccion;
    }


    public String GetCodigoCliente() {
        return this.CodigoCliente;
    }

    public void SetCodigoCliente(String codigocliente) {
        this.CodigoCliente = codigocliente;
    }


    public int GetCompendio() {
        return this.Compendio;
    }

    public void SetCompendio(int compendio) {
        this.Compendio = compendio;
    }


    public Vector GetVectorFechas() {
        return this.VectorFechas;
    }

    public void SetVectorFechas(Vector fechas) {
        this.VectorFechas = fechas;
    }


    public int GetVisitado() {
        return this.Visitado;
    }

    public void SetVisitado(int visitado) {
        this.Visitado = visitado;
    }

}
