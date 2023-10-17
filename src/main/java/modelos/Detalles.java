package modelos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class Detalles {
    private int codigo_matricula;
    private int codigo_curso;
    private Float monto;
    private int asistencias;
    private int nota;
    private String estado;
    private String nombre_curso = "";
    
    public Detalles(){}

    public Detalles(int codigo_matricula, int codigo_curso, Float monto, int asistencias, int nota, String estado, String nombre_curso) {
        this.codigo_matricula = codigo_matricula;
        this.codigo_curso = codigo_curso;
        this.monto = monto;
        this.asistencias = asistencias;
        this.nota = nota;
        this.estado = estado;
        this.nombre_curso = nombre_curso;
    }

    public int getCodigo_matricula() {
        return codigo_matricula;
    }

    public String getNombre_curso() {
        return nombre_curso;
    }

    public void setNombre_curso(String nombre_curso) {
        this.nombre_curso = nombre_curso;
    }

    public void setCodigo_matricula(int codigo_matricula) {
        this.codigo_matricula = codigo_matricula;
    }

    public int getCodigo_curso() {
        return codigo_curso;
    }

    public void setCodigo_curso(int codigo_curso) {
        this.codigo_curso = codigo_curso;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
