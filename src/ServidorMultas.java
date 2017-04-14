import dgt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ServidorMultas implements InterfazMultas
{
    private final ArrayList<Vehiculo> vehiculos; 
    private final ArrayList<Conductor> conductores; 
    private final ArrayList<Multa> multas; 
    
    // Métodos privados del servidor:
    
    /**
     * Se usará cuando haga falta saber si un vehículo está dado de alta.
     * @param mat Matrícula del vehículo que se quiere comprobar si está o no dado de alta.
     * @return La posición del vehículo en el array 'vehiculos' o -1 si no existe el vehículo.
     */
    private int existeVehiculo(String mat)
    {
        int pos = 0;
        
        while(pos < vehiculos.size() && !mat.equalsIgnoreCase(vehiculos.get(pos).getMat()))
            pos++;
        
        if(pos < vehiculos.size())
            return pos;
        
        return -1;
    }
    
    /**
     * Dará de alta a un conductor, en el caso de que no lo esté.
     * @param dni DNI del conductor que daremos de alta.
     */
    private void altaUsuario(int dni)
    {
        if(buscarUsuario(dni) == -1)
            conductores.add(new Conductor(dni, 12));
    }
    
    /**
     * Busca a un conductor.
     * @param dni DNI del conductor a buscar.
     * @return Posición del conductor en el array 'conductores' o -1 si no existe.
     */
    private int buscarUsuario(int dni)
    {
        int pos = 0;
        
        while(pos < conductores.size() && dni != conductores.get(pos).getDni())
            pos++;
        
        if(pos < conductores.size())
            return pos;
        
        return -1;
    }
    
    /**
     * Se utilizará para saber si un vehículo tiene alguna multa.
     * @param mat Matrócula del vehículo.
     * @return TRUE si el vehículo tiene alguna multa, FALSE en caso contrario.
     */
    private boolean tieneMulta(String mat)
    {
        int i = 0;

	while(i < multas.size() && !mat.equalsIgnoreCase(multas.get(i).getMat()))
		i++;

	return !(i == multas.size());
    }
    
    /**
     * Sanciona al conductor que sea propietario del vehículo con matrícula 'mat' quitándole 'puntos' puntos.
     * @param mat Matrícula del vehículo que ha sido multado.
     * @param puntos Puntos de la multa.
     */
    private void quitarPuntos(String mat, int puntos)
    {
        int dni = this.propietarioVehiculo(mat);

	if(dni != -1)
	{
		int pos = this.buscarUsuario(dni);

		if(pos != -1)
                {
                    Conductor sancionado = conductores.get(pos);
                    int puntosActuales = sancionado.getPuntos();
                    
                    if(puntosActuales - puntos > 0)
                        sancionado.setPuntos(puntosActuales - puntos);
                    
                    else
                        sancionado.setPuntos(0);
                }
	}
    }
    
    /**
     * Averigua quien es dueño del vehículo de matrícula 'mat'.
     * @param mat Matrícula del vehículo en cuestión.
     * @return El DNI del propietario de vehículo o -1 si no se encuentra al propietario.
     */
    private int propietarioVehiculo(String mat)
    {
        int pos = 0;
        
        while(pos < vehiculos.size() && !mat.equalsIgnoreCase(vehiculos.get(pos).getMat()))
            pos++;
        
        if(pos < vehiculos.size())
            return vehiculos.get(pos).getDni();
        
        return -1;
    }
    
    /**
     * Busca una multa en el array 'multas'.
     * @param mat Matrícula del vehículo multado.
     * @param fecha Fecha de la multa.
     * @return La posición de la multa en el array 'multas' o -1 si no está la multa.
     */
    private int buscarMulta(String mat, String fecha)
    {
        int pos = 0;
        boolean encontrado = false;
        
        while(!encontrado && pos < multas.size())
            if(mat.equalsIgnoreCase(multas.get(pos).getMat()) && fecha.equalsIgnoreCase(multas.get(pos).getFecha()))
                encontrado = true;
        
            else
                pos++;
        
        if(encontrado)
            return pos;
        
        return -1;
    }
    
    /**
     * Devuelve puntos a un conductor.
     * @param mat Matrícula del vehículo a cuyo dueño se le devolverá los puntos.
     * @param puntos Puntos a devolver.
     */
    private void devolverPuntos(String mat, int puntos)
    {
        int dni = propietarioVehiculo(mat);

	if(dni != -1)
	{
		int pos = buscarUsuario(dni);

		if(pos != -1)
                {
                    Conductor absuelto = conductores.get(pos);
                    int puntosActuales = absuelto.getPuntos();
                    
                    absuelto.setPuntos(puntosActuales + puntos);
                }
	}
    }
    
    /**
     * Comprueba si una multa es de un conductor en concreto.
     * @param dni DNI del conductor.
     * @param mat Matrícula del vehículo multado.
     * @return TRUE si el conductor es dueño del vehículo, FALSE en caso contrario.
     */
    private boolean esMiMulta(int dni, String mat)
    {
        int pos = 0;
        boolean encontrado = false;

	while(!encontrado && pos < vehiculos.size())
            if(vehiculos.get(pos).getDni() == dni && mat.equalsIgnoreCase(vehiculos.get(pos).getMat()))
                encontrado = true;
        
            else
                pos++;

	return encontrado;
    }
    
    // -----------------------------------------------------------------------------------------------------------------
    
    // Métodos públicos del servidor:
    
    public ServidorMultas()
    {
        vehiculos = new ArrayList();
        conductores = new ArrayList();
        multas = new ArrayList();
    }
    
    @Override
    public int comprobarPuntos(int dni, String mat) throws RemoteException 
    {
        int result, dniPropietario, pos = -1;

	if(this.existeVehiculo(mat) != -1)
	{
            dniPropietario = propietarioVehiculo(mat);
            
            if(dni == dniPropietario)   // Si el vehículo es mío.
                pos = buscarUsuario(dniPropietario);
	}

	if(pos == -1)
		result = -1;

	else
            result = conductores.get(pos).getPuntos();

	return result;
    }

    @Override
    public LinkedList comprobarMultas(int dni, String mat) throws RemoteException 
    {
        LinkedList<Multa> misMultas = new LinkedList();

	if(this.existeVehiculo(mat) != -1)
	{
            for(Multa actual : multas) 
                if(mat.equalsIgnoreCase(actual.getMat()) && this.esMiMulta(dni, actual.getMat()))
                    misMultas.add(actual);
	}

	return misMultas;
    }

    @Override
    public int indentificacion(String pass) throws RemoteException 
    {
        if(pass.equalsIgnoreCase("541293AGP"))
            return 1;
        
        return 0;
    }

    @Override
    public int ponerMulta(String mat, String fecha, int puntos) throws RemoteException 
    {
        int result = 0;

	if(this.existeVehiculo(mat) != -1)
	{
		multas.add(new Multa(mat, fecha, puntos));
		this.quitarPuntos(mat, puntos);   
		result = 1;
	}

	return result;
    }

    @Override
    public int quitarMulta(String mat, String fecha) throws RemoteException 
    {
        int result = 0, pos = this.buscarMulta(mat, fecha);

	if(pos != -1)
	{
            int puntos = multas.get(pos).getPuntos();
            
            multas.remove(pos);
            this.devolverPuntos(mat, puntos);  
            result = 1;
	}

	return result;
    }

    @Override
    public int altaVehiculo(int dni, String mat) throws RemoteException 
    {
        int result = 0;

	if(this.existeVehiculo(mat) == -1)
	{
		vehiculos.add(new Vehiculo(dni, mat));
		this.altaUsuario(dni);  
		result = 1;
	}

	return result;
    }

    @Override
    public int bajaVehiculo(int dni, String mat) throws RemoteException 
    {
        int result = 0, pos = this.existeVehiculo(mat);

	if(pos != -1 && !this.tieneMulta(mat))
	{
            vehiculos.remove(pos);
            result = 1;
	}		

	return result;
    }
}
