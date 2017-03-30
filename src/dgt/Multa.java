package dgt;

public class Multa 
{
    private String mat;
    private String fecha;
    private int puntos;

    public Multa() 
    {
        
    }

    public Multa(String mat, String fecha, int puntos) 
    {
        this.mat = mat;
        this.fecha = fecha;
        this.puntos = puntos;
    }

    public String getMat() 
    {
        return mat;
    }

    public void setMat(String mat) 
    {
        this.mat = mat;
    }

    public String getFecha() 
    {
        return fecha;
    }

    public void setFecha(String fecha) 
    {
        this.fecha = fecha;
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
