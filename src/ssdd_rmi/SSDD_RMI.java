package ssdd_rmi;

import dgt.Multa;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Scanner;
import servidor.*;

public class SSDD_RMI 
{
    public static void main(String[] args) 
    {
        try 
        {  
            int Puerto;  
            Scanner Teclado = new Scanner(System.in);
            System.out.print("Introduce el nº de puerto para comunicarse: ");
            Puerto = Teclado.nextInt();
            
            Registry registry = LocateRegistry.createRegistry(Puerto);  
            ServidorMultas servidor = new ServidorMultas();  
            
            InterfazMultas stub = (InterfazMultas) UnicastRemoteObject.exportObject(servidor, Puerto);  
  
            registry = LocateRegistry.getRegistry(Puerto);  
            registry.bind("Calculadora", stub);
            
            int DNI1 = 45134789, DNI2 = 45111786;
            String mat1 = "7861-HJI", mat2 = "5661-LLK", mat3 = "0988-HKP";
            
            // El servidor, al dar de alta a los vehículos, crea el mismo a los conductores:
            
            servidor.altaVehiculo(DNI1, mat1);
            servidor.altaVehiculo(DNI1, mat2);
            servidor.altaVehiculo(DNI2, mat3);
            
            // Pongo algunas multas:
            
            servidor.ponerMulta(mat2, "01/04/2017-13:00", 6);
            servidor.ponerMulta(mat3, "01/04/2017-13:00", 2);
            
            // Para probar que todo ha ido bien, compruebo puntos y multas:
            
            LinkedList<Multa> m1 = servidor.comprobarMultas(DNI1, mat1);
            LinkedList<Multa> m2 = servidor.comprobarMultas(DNI1, mat2);
            LinkedList<Multa> m3 = servidor.comprobarMultas(DNI2, mat3);
            
            System.out.println("\nSe han puesto las siguientes multas:\n");
            
            for(Multa m: m1)
                System.out.println(m.getMat() + " " + m.getFecha() + " " + m.getPuntos());
            
            for(Multa m: m2)
                System.out.println(m.getMat() + " " + m.getFecha() + " " + m.getPuntos());
            
            for(Multa m: m3)
                System.out.println(m.getMat() + " " + m.getFecha() + " " + m.getPuntos());
            
            System.out.print("\nEl conductor de DNI " + DNI1 + " tiene ");
            System.out.println(servidor.comprobarPuntos(DNI1, mat1));
            
            System.out.print("El conductor de DNI " + DNI1 + " tiene ");
            System.out.println(servidor.comprobarPuntos(DNI1, mat2));
            
            System.out.print("El conductor de DNI " + DNI2 + " tiene ");
            System.out.println(servidor.comprobarPuntos(DNI2, mat3));
        } 
        
        catch (Exception e) 
        {
            System.out.println("Error en servidor multas: " + e);
        } 
    }
}
