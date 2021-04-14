package com.incloud.hcp.config;

import com.incloud.hcp.bean.ProveedorCustom;
import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.bean.UserSessionFront;
import com.incloud.hcp.domain.PreRegistroProveedor;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.repository.PreRegistroProveedorRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.sap.security.um.user.UnsupportedUserAttributeException;
import com.sap.security.um.user.User;
import com.sap.security.um.user.UserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

@Component("systemLoggedUser")
public class SystemLoggedUser {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${sm.portal.nameId}")
    private String nameIdScp;

    @Value("${sm.portal.dev}")
    private Boolean isDev;

    @Value("${sm.portal.nameDisplay}")
    private String nameDisplay;

    @Value("${sm.portal.email}")
    private String email;

    @Value("${sm.portal.prov.idSCP}")
    private String proveedorIdSCP;

    @Value("${sm.portal.prov.ruc}")
    private String proveedorRuc;

    @Value("${sm.portal.prov.nameDisplay}")
    private String proveedorNameDisplay;

    @Value("${sm.portal.prov.email}")
    private String proveedorEmail;

    private ProveedorRepository proveedorRepository;

    private PreRegistroProveedorRepository preRegistroProveedorRepository;
//    private UsuarioRepository usuarioRepository;
//    private UsuarioAutoService usuarioAutoService;

    @Autowired
    public SystemLoggedUser(ProveedorRepository proveedorRepository,
                            PreRegistroProveedorRepository preRegistroProveedorRepository
//                            UsuarioRepository usuarioRepository,
//                            UsuarioAutoService usuarioAutoService
                            ) {
        this.proveedorRepository = proveedorRepository;
        this.preRegistroProveedorRepository = preRegistroProveedorRepository;
//        this.usuarioRepository = usuarioRepository;
//        this.usuarioAutoService = usuarioAutoService;
    }

    public User getUserSCP(HttpServletRequest request) {
        String keyAttribute = "com.sap.security.auth.login.User." + request.getUserPrincipal().getName().toLowerCase();
        return (User) request.getSession().getAttribute(keyAttribute);
    }

    // Temporal
    public UserSession getUserSessionProveedor() throws PortalException {

        try {
            PreRegistroProveedor preProveedor = preRegistroProveedorRepository.findTop1ByOrderByIdRegistroDesc();
            //proveedorNameDisplay=preRegistroProveedorRepository.findTopByOrderByIdRegistroDesc().getDatospersonales();
            //proveedorIdSCP = preRegistroProveedorRepository.findTopByOrderByIdRegistroDesc().getIdHcp();
            //proveedorEmail  = "mmonsalvecsticorp.biz";
            //proveedorRuc = preRegistroProveedorRepository.findTopByOrderByIdRegistroDesc().getRuc();

            UserSession session = new UserSession();
            if(preProveedor == null) {
                session.setDisplayName(this.proveedorNameDisplay);
                session.setId(this.proveedorIdSCP);
                session.setMail(this.proveedorEmail);
                session.setRuc(this.proveedorRuc);
                return session;
            } else {
                session.setDisplayName("Proveedor DEV");
                session.setId(preProveedor.getIdHcp());
                session.setMail(preProveedor.getEmail());
                session.setRuc(preProveedor.getRuc());
                return session;
            }

        } catch (Exception ex) {
            logger.error("Error al obtener el usuario de la sesión", ex);
            throw new PortalException("Error al obtener el usuario de la sesión");
        }
    }

    public UserSession getUserSession(UserSessionFront userfront) throws PortalException {
        UserSession session = new UserSession();
        if(userfront == null) {
            session.setDisplayName(this.proveedorNameDisplay);
            session.setId(this.proveedorIdSCP);
            session.setMail(this.proveedorEmail);
            session.setRuc(this.proveedorRuc);
            return session;
        } else {
            session.setUserName(userfront.getUserName());
            session.setId(userfront.getId());
            session.setFirstName(userfront.getGivenName());
            session.setLastName(userfront.getFamilyName());
            session.setDisplayName(userfront.getDisplayName());
            session.setMail(userfront.getUserName());

            String loginName = userfront.getRuc();
            session.setRuc(loginName);
            if (loginName != null && !loginName.isEmpty() && (loginName.startsWith("1") || loginName.startsWith("2"))) // loginName es un RUC
                this.guardarUsuarioIdpDeProveedor(session);
            return session;
        }
    }

    public UserSession getUserSession() throws PortalException {
        try {
            if (!isDev) {
                UserSession session = new UserSession();
                session.setDisplayName(this.nameDisplay);
                session.setId(this.nameIdScp);
                session.setMail(this.email);
                session.setRuc(this.nameIdScp);
                return session;
            }
            InitialContext ctx = new InitialContext();
            UserProvider userProvider;
            userProvider = (UserProvider) ctx.lookup("java:comp/env/user/Provider");

            User u = userProvider.getCurrentUser();
            //logger.error("Usuario u: " + u.toString());
            UserSession session = new UserSession();
            session.setUserName(u.getName());
            session.setId(this.getAttributeOfUser(u, "id"));
            session.setFirstName(this.getAttributeOfUser(u,"first_name"));
            session.setLastName(this.getAttributeOfUser(u,"last_name"));
            session.setDisplayName(this.getAttributeOfUser(u, "display_name"));
            session.setMail(this.getAttributeOfUser(u, "mail"));

            String loginName = this.getAttributeOfUser(u, "login_name");
            session.setRuc(loginName);

            if (loginName != null && !loginName.isEmpty() && (loginName.startsWith("1") || loginName.startsWith("2"))) // loginName es un RUC
                this.guardarUsuarioIdpDeProveedor(session);

//            if (loginName != null && !this.stringIsLong(loginName)) // loginName no es null ni es un RUC
//                session.setSapUsername(loginName.toUpperCase());

            session.setGroupList(new ArrayList<>(u.getGroups()));
            session.setRoleList(new ArrayList<>(u.getRoles()));
            //logger.error("Usuario session: " + session.toString());

//            if (loginName != null)
//                this.guardarEditar(session);

            return session;
        } catch (Exception ex) {
            logger.error("Error al obtener el usuario de la sesión", ex);
            throw new PortalException("Error al obtener el usuario de la sesión");
        }
    }

    private boolean stringIsLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

//    private void guardarEditar(UserSession userSession) {
//        String nombreLogin = userSession.getRuc();
//        String correo = userSession.getMail().toLowerCase();
//        String nombre = Optional.ofNullable(userSession.getFirstName()).orElse("");
//        String apellido = Optional.ofNullable(userSession.getLastName()).orElse("");
//        String codigoSap = Optional.ofNullable(userSession.getSapUsername()).orElse("");
//
////        Usuario usuarioExistente = usuarioRepository.findByCodigoSap(nombreLogin.toUpperCase());
//        Usuario usuarioExistente = null;
//        boolean correoEsUnico;
//        List<Usuario> usuarioExistenteList = usuarioRepository.findByCodigoUsuarioIdp(nombreLogin);
//        if (usuarioExistenteList.size() == 1)
//            usuarioExistente = usuarioExistenteList.get(0);
//
//        try{
//            if (usuarioExistente == null){ // NO existe usuario en la tabla con ese mismo login_name
//                logger.error("USUARIO NUEVO: " + userSession.toString());
//                Usuario usuarioConMismoCorreo = usuarioRepository.findByEmail(correo);
//                correoEsUnico = usuarioConMismoCorreo == null;
//
//                if(correoEsUnico) { // NO existe usuario en la tabla con ese mismo correo
//                    logger.error("USUARIO NUEVO Y CORREO ES NUEVO: " + userSession.toString());
//                }
//                else{ // SI existe usuario en la tabla con ese mismo correo
//                    logger.error("USUARIO NUEVO Y CORREO EXISTE CON OTRO USUARIO: " + userSession.toString());
//                    logger.error("USUARIO EXISTENTE CON MISMO CORREO (A BORRAR): " + usuarioConMismoCorreo.toString());
//                    usuarioAutoService.delete(usuarioConMismoCorreo);
//                }
//
//                Usuario usuarioNuevo = new Usuario();
//                Cargo defaultCargo = new Cargo();
//                defaultCargo.setIdCargo(0);
//
//                usuarioNuevo.setCodigoUsuarioIdp(nombreLogin);
//                usuarioNuevo.setEmail(correo);
//                usuarioNuevo.setCargo(defaultCargo);
//                usuarioNuevo.setApellido(apellido);
//                usuarioNuevo.setNombre(nombre);
//
//                if (!codigoSap.isEmpty())
//                    usuarioNuevo.setCodigoSap(codigoSap);
//
//                usuarioNuevo = usuarioAutoService.save(usuarioNuevo);
//
//                if (correoEsUnico)
//                    logger.error("USUARIO NUEVO Y CORREO ES NUEVO Y SE INSERTO EN BD CON: " + usuarioNuevo.toString());
//                else
//                    logger.error("USUARIO NUEVO Y CORREO AHORA ES UNICO Y SE INSERTO EN BD CON: " + usuarioNuevo.toString());
//            }
//            else { // SI existe usuario en la tabla con ese mismo login_name
//                if(!usuarioExistente.getEmail().equals(correo)
//                        || !usuarioExistente.getNombre().equals(nombre)
//                        || !usuarioExistente.getApellido().equals(apellido)
//                        || !(usuarioExistente.getCodigoSap() != null ? usuarioExistente.getCodigoSap() : "").equals(codigoSap)){
//                    logger.error("USUARIO EXISTE Y FUE MODIFICADO: " + usuarioExistente.toString());
//                    List<Usuario> otroUsuarioCodigoSapList = usuarioRepository.getUsuarioCodigoSapDistingbyId(usuarioExistente.getIdUsuario(), codigoSap);
//
//                    if (!(otroUsuarioCodigoSapList != null && !otroUsuarioCodigoSapList.isEmpty())) {
//                        logger.error("USUARIO EXISTE Y FUE MODIFICADO Y CODIGO_SAP ES UNICO: " + usuarioExistente.toString());
//                        List<Usuario> otroUsuarioCorreoList = usuarioRepository.getUsuarioCorreoDistingbyId(usuarioExistente.getIdUsuario(), correo);
//                        correoEsUnico = !(otroUsuarioCorreoList != null && !otroUsuarioCorreoList.isEmpty());
//
//                        if (correoEsUnico) {
//                            logger.error("USUARIO EXISTE Y FUE MODIFICADO Y CODIGO_SAP ES UNICO Y CORREO ES UNICO: " + usuarioExistente.toString());
//                        }
//                        else{
//                            logger.error("USUARIO EXISTE Y FUE MODIFICADO Y CODIGO_SAP ES UNICO Y CORREO EXISTE CON OTRO(S) USUARIO(S): " + userSession.toString());
//                            for(Usuario usuarioConMismoCorreo : otroUsuarioCorreoList) {
//                                logger.error("USUARIO EXISTENTE CON MISMO CORREO (A BORRAR): " + usuarioConMismoCorreo.toString());
//                                usuarioAutoService.delete(usuarioConMismoCorreo);
//                            }
//                        }
//
//                        usuarioExistente.setEmail(correo);
//                        usuarioExistente.setNombre(nombre);
//                        usuarioExistente.setApellido(apellido);
//
//                        if(!codigoSap.isEmpty())
//                            usuarioExistente.setCodigoSap(codigoSap);
//
//                        usuarioExistente = usuarioAutoService.update(usuarioExistente);
//
//                        if (correoEsUnico)
//                            logger.error("USUARIO EXISTE Y FUE MODIFICADO Y CODIGO_SAP ES UNICO Y CORREO ES UNICO Y SE ACTUALIZO EN BD CON: " + usuarioExistente.toString());
//                        else
//                            logger.error("USUARIO EXISTE Y FUE MODIFICADO Y CODIGO_SAP ES UNICO Y CORREO AHORA ES UNICO Y SE ACTUALIZO EN BD CON: " + usuarioExistente.toString());
//                    }
//                }
//            }
//        }catch (Exception e){
//            logger.error("Excepción al guardar/editar usuario " + nombreLogin + ": " + e.getClass().getName() + " -- " + e.getMessage());
//        }
//    }

    private void guardarUsuarioIdpDeProveedor(UserSession userSession) {
        String ruc = userSession.getRuc();
        String usuarioIdp = userSession.getId();

        try{
//            Optional<String> opCurrentUsuarioIdp = proveedorRepository.getUsuarioIdpProveedorByRuc(ruc);
            Optional<ProveedorCustom> opProveedorCustom = proveedorRepository.getProveedorIdHcpByRuc(ruc);

            if(opProveedorCustom.isPresent() && opProveedorCustom.get().getIdHCP() == null) {
                logger.error("ACTUALIZANDO UsuarioIdp DE PROVEEDOR // ruc: " + ruc + " / usuarioIdp: " + usuarioIdp );
                proveedorRepository.updateIdHCP(usuarioIdp, ruc);
            }
        }catch (Exception e){
            logger.error("EXCEPCION AL VERIFICAR/GUARDAR UsuarioIdp DE PROVEEDOR / ruc: " + ruc + " / usuarioIdp: " + usuarioIdp + " // " + e.getClass().getName() + " -- " + e.getMessage());
        }
    }

    private String getAttributeOfUser(User user, String key) {
        try {
            if (user == null) {
                return null;
            }
            //logger.error("Usuario getAttributeOfUser: " + user.toString());
            //logger.error("Usuario getAttributeOfUser key: " + key);
            String atributo = user.getAttribute(key);
            //logger.error("Usuario getAttributeOfUser atributo: " + atributo);
            return atributo;
        } catch (UnsupportedUserAttributeException ex) {
            logger.error("Error al leer el atributo " + key + " de la sesión", ex);
        }
        return null;
    }
}
