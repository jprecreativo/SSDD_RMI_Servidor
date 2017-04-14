import java.rmi.RemoteException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jose_
 */
public class ServidorMultasTest 
{ 
    private final ServidorMultas servidor;
    
    public ServidorMultasTest() 
    {
        servidor = new ServidorMultas();
    }
    
    @Before
    public void setUp() throws RemoteException 
    {
        int DNI1 = 45134789, DNI2 = 45111786;
        String mat1 = "7861-HJI", mat2 = "5661-LLK", mat3 = "0988-HKP";

        // El servidor, al dar de alta a los vehículos, crea el mismo a los conductores:

        servidor.altaVehiculo(DNI1, mat1);
        servidor.altaVehiculo(DNI1, mat2);
        servidor.altaVehiculo(DNI2, mat3);

        // Pongo algunas multas:

        servidor.ponerMulta(mat2, "01/04/2017-13:00", 6);
        servidor.ponerMulta(mat3, "01/04/2017-13:00", 2);
    }
    
    @After
    public void tearDown() throws RemoteException 
    {
        int DNI1 = 45134789, DNI2 = 45111786;
        String mat1 = "7861-HJI", mat2 = "5661-LLK", mat3 = "0988-HKP";

        // El servidor, al dar de alta a los vehículos, crea el mismo a los conductores:

        servidor.bajaVehiculo(DNI1, mat1);
        servidor.bajaVehiculo(DNI1, mat2);
        servidor.bajaVehiculo(DNI2, mat3);

        // Pongo algunas multas:

        servidor.quitarMulta(mat2, "01/04/2017-13:00");
        servidor.quitarMulta(mat3, "01/04/2017-13:00");    
    }

    @Test
    public void testComprobarPuntos() throws Exception 
    {
        System.out.println("comprobarPuntos");
        
        String mat = "0988-HKP";
        int dni = 45111786, expResult = 10, result = servidor.comprobarPuntos(dni, mat);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testComprobarMultas() throws Exception 
    {
        System.out.println("comprobarMultas");
        
        int dni = 45111786;
        String mat = "0988-HKP";
        int expResult = 1, result = servidor.comprobarMultas(dni, mat).size();
        
        assertEquals(expResult, result);
    }

    @Test
    public void testIndentificacion() throws Exception 
    {
        System.out.println("indentificacion");
        
        String pass = "541293agP";
        int expResult = 1, result = servidor.indentificacion(pass);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testPonerMulta() throws Exception 
    {
        System.out.println("ponerMulta");
        
        String mat = "7861-HJI", fecha = "";
        int puntos = 0, expResult = 1, result = servidor.ponerMulta(mat, fecha, puntos);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testQuitarMulta() throws Exception 
    {
        System.out.println("quitarMulta");
        
        String mat = "5661-LLK", fecha = "01/04/2017-13:00";
        int expResult = 1, result = servidor.quitarMulta(mat, fecha);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testAltaVehiculo() throws Exception 
    {
        System.out.println("altaVehiculo");
      
        String mat = "5901-LOP";
        int dni = 0, expResult = 1, result = servidor.altaVehiculo(dni, mat);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testBajaVehiculo() throws Exception 
    {
        System.out.println("bajaVehiculo");
       
        String mat = "7861-HJI";
        int dni = 0, expResult = 1, result = servidor.bajaVehiculo(dni, mat);
       
        assertEquals(expResult, result); 
    }
}
