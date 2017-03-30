package dgt;

public class Conductor 
{
    private int dni;
    private int puntos;

    public Conductor() 
    {
        
    }

    public Conductor(int dni, int puntos) 
    {
        this.dni = dni;
        this.puntos = puntos;
    }

    public int getDni() 
    {
        return dni;
    }

    public void setDni(int dni) 
    {
        this.dni = dni;
    }

    public int getPuntos() 
    {
        return puntos;
    }

    public void setPuntos(int puntos) 
    {
        this.puntos = puntos;
    }
}
