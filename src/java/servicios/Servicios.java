/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import conf.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import modelo.Usuario;

/**
 * REST Web Service
 *
 * @author dana
 */
@Path("webservice")
public class Servicios {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Servicios
     */
    public Servicios() {
        
        
    }

    /**
     * Retrieves representation of an instance of servicios.Servicios
     * @param nombre
     * @param contraseña
     * @param edad
     * @return an instance of java.lang.String
     * @throws java.sql.SQLException
     */
    @GET
    @Path("/registrar")
    @Produces(MediaType.APPLICATION_JSON)
    public String registrarUsuario(@QueryParam("nombre") String nombre,
                                    @QueryParam("contraseña") String contraseña,
                                    @QueryParam("edad") int edad) throws SQLException {
        
       String mensaje = "";
        int insertados=0;
        
        PreparedStatement ps = null;
        Conexion conexion = new Conexion();
        try {
            String sql="insert into usuarios (nombre,contraseña,edad) values(?,?,?)";
            System.out.println(sql);
            ps = conexion.getConexion().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, contraseña);
            ps.setInt(3, edad);
            insertados = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        finally{
            try {
                if(conexion.getConexion() == null)conexion.getConexion().close();
                if(ps != null)ps.close();
            } catch (SQLException ex) {
                System.out.println("errorrrrrrrrrrr" + ex);
            }
        }
        
        if(insertados==1){
            System.out.println(insertados);
            mensaje = "{\"mensaje\":\"Registro Exitoso\"}";
        }else{
             mensaje = "{\"mensaje\":\"Fallo al intentar reistrar\"}";
        }
        return mensaje;
    }

    @GET
    @Path("/listarusuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        Conexion conexion = new Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        
        
        String sql = "select * from usuarios";
        
            
            ps = conexion.getConexion().prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setContaseña(rs.getString("contraseña"));
                usuario.setEdad(rs.getInt("edad"));
                usuarios.add(usuario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                if(conexion.getConexion() == null)conexion.getConexion().close();
                if(ps != null)ps.close();
                if(rs != null)rs.close();
            } catch (SQLException ex) {
                System.out.println("errorrrrrrrrrrr" + ex);
                Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
            
        return usuarios;
    }
@GET
    @Path("/usuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUsuario(@QueryParam("id") int id) throws SQLException {
        Usuario usuario = new Usuario();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "select * from usuarios where id = ?";
        Conexion conexion = new Conexion();
        
        try {
            ps = conexion.getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setContaseña(rs.getString("contraseña"));
                usuario.setEdad(rs.getInt("edad"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                if(conexion.getConexion() == null)conexion.getConexion().close();
                if(ps != null)ps.close();
                if(rs != null)rs.close();
            } catch (SQLException ex) {
                System.out.println("errorrrrrrrrrrr" + ex);
                Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return usuario;
    }
    
    @GET
    @Path("/deleteusuario")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUsuario(@QueryParam("id") int id) throws SQLException {
         String mensaje = null;
        int afectados = 0;
        Conexion conexion = new Conexion();
        PreparedStatement ps = null;
        String sql = "delete from usuarios where id = ?";
        try {
            ps = conexion.getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            afectados = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                if(conexion.getConexion() == null)conexion.getConexion().close();
                if(ps != null)ps.close();
            } catch (SQLException ex) {
                System.out.println("errorrrrrrrrrrr" + ex);
                Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(afectados==1){
            System.out.println(afectados);
            mensaje = "{\"mensaje\":\"Delete Exitoso\"}";
        }else{
            System.out.println(afectados);
            mensaje = "{\"mensaje\":\"Fallo al eliminar registro\"}";
        }
        return mensaje;
    }
    
@GET
    @Path("/updateusuario")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUsuario(@QueryParam("id") int id,
                                @QueryParam("nombre") String nombre,
                               @QueryParam("contraseña") String contraseña,
                               @QueryParam("edad") int edad) throws SQLException {
        
        String mensaje = null;
        Conexion conexion = new Conexion();
        int afectados = 0;
        PreparedStatement ps = null;
        String sql = "update usuarios set nombre = ?, contraseña = ?, edad = ? where id = ?";
        try {
            ps = conexion.getConexion().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, contraseña);
            ps.setInt(3, edad);
            ps.setInt(4, id);
            afectados = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                if(conexion.getConexion() == null)conexion.getConexion().close();
                if(ps != null)ps.close();
            } catch (SQLException ex) {
                System.out.println("errorrrrrrrrrrr" + ex);
                Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(afectados==1){
            System.out.println(afectados);
            mensaje = "{\"mensaje\":\"update exitoso\"}";
        }else{
            System.out.println(afectados);
            mensaje = "{\"mensaje\":\"fallo al actualizar el registro\"}";
        }
       return mensaje;
    }    
    public static void main(String[] args) throws SQLException {
        Servicios s = new Servicios();
        System.out.println(s.updateUsuario(3, "yeral", "yeralcoqueco", 22));
    }
}
