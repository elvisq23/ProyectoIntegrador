package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.*;
import com.utp.integradorspringboot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.awt.Color;
import java.io.InputStream;

@RestController
@RequestMapping("/api/cotizaciones")
public class CotizacionApiController {

    @Autowired
    private OrdenServicioRepository ordenServicioRepository;
    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RepuestoRepository repuestoRepository;

    // Listar cotizaciones adaptado a DTO
    @GetMapping
    public List<CotizacionDTO> listarCotizaciones() {
        List<OrdenServicio> ordenes = ordenServicioRepository.findAll();
        List<CotizacionDTO> dtos = new ArrayList<>();
        for (OrdenServicio o : ordenes) {
            String estado = o.getEstado();
            if (!"ACTIVO".equalsIgnoreCase(estado) && !"EMITIDO".equalsIgnoreCase(estado) && !"APROBADO".equalsIgnoreCase(estado)) continue;
            String cliente = "";
            String vehiculo = "";
            // Buscar vehículo y cliente
            if (o.getIdVehiculo() != null) {
                Optional<Vehiculo> v = vehiculoRepository.findById(o.getIdVehiculo());
                if (v.isPresent()) {
                    vehiculo = v.get().getMarca() + " " + v.get().getModelo();
                    if (v.get().getClienteId() != null) {
                        Optional<Usuario> user = usuarioRepository.findById(v.get().getClienteId());
                        if (user.isPresent()) {
                            cliente = user.get().getNombres() + " " + user.get().getApellidos();
                        }
                    }
                }
            }
            String estadoFrontend = estado;
            if ("ACTIVO".equalsIgnoreCase(estadoFrontend)) estadoFrontend = "PENDIENTE";
            dtos.add(new CotizacionDTO(o.getId(), cliente, vehiculo, estadoFrontend));
        }
        return dtos;
    }

    // Emitir cotización (agregar detalles y cambiar estado)
    @PutMapping("/{id}/emitir")
    public ResponseEntity<?> emitir(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        Optional<OrdenServicio> optional = ordenServicioRepository.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        OrdenServicio orden = optional.get();
        orden.setEstado("EMITIDO");
        ordenServicioRepository.save(orden);

        // Recibir detalles como lista de { repuestoId, cantidad }
        List<Map<String, Object>> detalles = (List<Map<String, Object>>) body.get("detalles");
        for (Map<String, Object> detalle : detalles) {
            Integer repuestoId = (Integer) detalle.get("repuestoId");
            Integer cantidad = (Integer) detalle.get("cantidad");
            Optional<Repuesto> repOpt = repuestoRepository.findById(repuestoId);
            if (repOpt.isPresent()) {
                Repuesto rep = repOpt.get();
                DetalleOrden det = new DetalleOrden();
                det.setOrdenServicioId(id);
                det.setCantidad(cantidad);
                det.setDescripcion(rep.getNombre());
                det.setPrecioUnitario(rep.getPrecioUnitario());
                detalleOrdenRepository.save(det);
            }
        }
        return ResponseEntity.ok().build();
    }

    // Aprobar cotización (cambiar estado)
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobar(@PathVariable Integer id) {
        Optional<OrdenServicio> optional = ordenServicioRepository.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        OrdenServicio orden = optional.get();
        orden.setEstado("APROBADO");
        ordenServicioRepository.save(orden);
        return ResponseEntity.ok().build();
    }

    // Eliminar cotización (solo si está en ACTIVO o EMITIDO)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<OrdenServicio> optional = ordenServicioRepository.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        OrdenServicio orden = optional.get();
        if (!"ACTIVO".equalsIgnoreCase(orden.getEstado()) && !"EMITIDO".equalsIgnoreCase(orden.getEstado())) {
            return ResponseEntity.status(403).body("Solo se puede eliminar cotizaciones en estado ACTIVO o EMITIDO");
        }
        // Eliminar detalles asociados
        List<DetalleOrden> detalles = detalleOrdenRepository.findByOrdenServicioId(id);
        detalleOrdenRepository.deleteAll(detalles);
        ordenServicioRepository.delete(orden);
        return ResponseEntity.ok().build();
    }

    // Descargar PDF de cotización (solo si está en EMITIDO o APROBADO)
    @GetMapping("/{id}/pdf")
    public ResponseEntity<?> descargarPdf(@PathVariable Integer id, HttpServletResponse response) {
        try {
            Optional<OrdenServicio> optional = ordenServicioRepository.findById(id);
            if (!optional.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            OrdenServicio orden = optional.get();
            if (!"EMITIDO".equalsIgnoreCase(orden.getEstado()) && !"APROBADO".equalsIgnoreCase(orden.getEstado())) {
                return ResponseEntity.status(403).body("Solo se puede descargar PDF de cotizaciones EMITIDAS o APROBADAS");
            }
            // Obtener datos para el PDF
            List<DetalleOrden> detalles = detalleOrdenRepository.findByOrdenServicioId(id);
            Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(orden.getIdVehiculo());
            Vehiculo vehiculo = vehiculoOpt.orElse(null);
            Usuario cliente = null;
            if (vehiculo != null && vehiculo.getClienteId() != null) {
                cliente = usuarioRepository.findById(vehiculo.getClienteId()).orElse(null);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            com.lowagie.text.Document document = new com.lowagie.text.Document();
            com.lowagie.text.pdf.PdfWriter writer = com.lowagie.text.pdf.PdfWriter.getInstance(document, baos);
            document.open();
            // Logo
            try {
                InputStream logoStream = getClass().getResourceAsStream("/static/images/logo.png");
                if (logoStream != null) {
                    byte[] logoBytes = logoStream.readAllBytes();
                    com.lowagie.text.Image logo = com.lowagie.text.Image.getInstance(logoBytes);
                    logo.scaleToFit(100, 100);
                    logo.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
                    document.add(logo);
                }
            } catch (Exception e) { /* Si falla el logo, continuar */ }
            // Datos principales centrados
            com.lowagie.text.Paragraph datos = new com.lowagie.text.Paragraph();
            datos.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
            datos.add("Cotización N° " + orden.getId() + "\n");
            datos.add("Fecha: " + (orden.getFechaCreacion() != null ? orden.getFechaCreacion().toLocalDate() : "") + "\n\n");
            if (cliente != null) {
                datos.add("Cliente: " + cliente.getNombres() + " " + cliente.getApellidos() + "\n");
                datos.add("DNI: " + (cliente.getDni() != null ? cliente.getDni() : "") + "\n");
            }
            if (vehiculo != null) {
                datos.add("Vehículo: " + vehiculo.getMarca() + " " + vehiculo.getModelo() + "\n");
                datos.add("Placa: " + vehiculo.getPlaca() + "\n");
            }
            datos.add("\n");
            document.add(datos);
            // Tabla profesional
            com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(5);
            table.setWidthPercentage(100);
            float[] widths = {2, 3, 1, 2, 2};
            table.setWidths(widths);
            com.lowagie.text.pdf.PdfPCell cell;
            String[] headers = {"Repuesto", "Descripción", "Cantidad", "Precio Unitario", "Subtotal"};
            for (String h : headers) {
                cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(h));
                cell.setBackgroundColor(new Color(230,230,230));
                cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
                cell.setPadding(6f);
                table.addCell(cell);
            }
            double total = 0;
            for (DetalleOrden d : detalles) {
                String nombre = d.getDescripcion();
                String descripcion = "";
                Double precio = d.getPrecioUnitario();
                cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(nombre));
                cell.setPadding(5f);
                table.addCell(cell);
                cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(descripcion));
                cell.setPadding(5f);
                table.addCell(cell);
                cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(String.valueOf(d.getCantidad())));
                cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_CENTER);
                cell.setPadding(5f);
                table.addCell(cell);
                cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase("S/ " + String.format("%.2f", precio)));
                cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                cell.setPadding(5f);
                table.addCell(cell);
                double subtotal = precio * d.getCantidad();
                cell = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase("S/ " + String.format("%.2f", subtotal)));
                cell.setHorizontalAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                cell.setPadding(5f);
                table.addCell(cell);
                total += subtotal;
            }
            document.add(table);
            document.add(new com.lowagie.text.Paragraph(" "));
            com.lowagie.text.Paragraph totalP = new com.lowagie.text.Paragraph("Total: S/ " + String.format("%.2f", total), new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 14, com.lowagie.text.Font.BOLD));
            totalP.setAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
            document.add(totalP);
            document.close();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=metalcars-cotizacion-" + id + ".pdf");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error generando PDF: " + e.getMessage());
        }
    }

    // Ver detalles de una cotización
    @GetMapping("/{id}/detalle")
    public List<DetalleOrden> obtenerDetalles(@PathVariable Integer id) {
        return detalleOrdenRepository.findByOrdenServicioId(id);
    }
}