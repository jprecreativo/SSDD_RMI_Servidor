package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface InterfazMultas extends Remote
{
    int comprobarPuntos(int dni, String mat) throws RemoteException;
    LinkedList comprobarMultas(int dni, String mat) throws RemoteException;
    int indentificacion(String pass) throws RemoteException;
    int ponerMulta(String mat, String fecha, int puntos) throws RemoteException;
    int quitarMulta(String mat, String fecha) throws RemoteException;
    int altaVehiculo(int dni, String mat) throws RemoteException;
    int bajaVehiculo(int dni, String mat) throws RemoteException;
}
