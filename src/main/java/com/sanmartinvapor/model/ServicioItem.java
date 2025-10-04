package com.sanmartinvapor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "servicio_item")
public class ServicioItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @Column(nullable=false)
    private String descripcion;

    @Column(name="hora_inicio", nullable=false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horaInicio;

    @Column(nullable=false)
    private String detalle;

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
}
