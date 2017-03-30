package dgt;

public class Vehiculo 
{
    private int dni;
    private String mat;

    public Vehiculo() 
    {
        
    }

    public Vehiculo(int dni, String mat)
    {
        this.dni = dni;
        this.mat = mat;
    }

    public int getDni() 
    {
        return dni;
    }

    public void setDni(int dni) 
    {
        this.dni = dni;
    }

    public String getMat() 
    {
        return mat;
    }

    public void setMat(String mat) 
    {
        this.mat = mat;
    }
}
