package ssdd_rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
            InterfazMultas obj = new Multas();  
            
            Multas stub = (Multas) UnicastRemoteObject.exportObject(obj, Puerto);  
  
            registry = LocateRegistry.getRegistry(Puerto);  
            registry.bind("Calculadora", stub);  
  
            System.out.println("Servidor multas esperando peticiones...");             
        } 
        
        catch (Exception e) 
        {
            System.out.println("Error en servidor multas: " + e);
        } 
    }
}
