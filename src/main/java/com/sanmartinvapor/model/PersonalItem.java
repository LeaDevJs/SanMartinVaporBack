package com.sanmartinvapor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "personal_item")
public class PersonalItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    @JsonFormat(pattern = "yyyy-MM-dd")  // 👈 ahora sí
    private LocalDate fecha;

    @Column(nullable=false)
    private String nombre;

    @Column(nullable=false)
    private String apellido;

    @Column(nullable=false)
    private String legajo;

    @Column(name="disponible_desde", nullable=false)
    @JsonFormat(pattern = "HH:mm:ss")   // 👈 ya lo tenías
    private LocalTime disponibleDesde;

    private String notas;

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getLegajo() { return legajo; }
    public void setLegajo(String legajo) { this.legajo = legajo; }

    public LocalTime getDisponibleDesde() { return disponibleDesde; }
    public void setDisponibleDesde(LocalTime disponibleDesde) { this.disponibleDesde = disponibleDesde; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}
