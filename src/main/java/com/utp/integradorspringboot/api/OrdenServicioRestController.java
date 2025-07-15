package com.utp.integradorspringboot.api;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.utp.integradorspringboot.dto.OrdenServicioResponseDTO;
import com.utp.integradorspringboot.models.OrdenServicio;
import com.utp.integradorspringboot.services.OrdenServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ordenes-servicio")
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5500"})
public class OrdenServicioRestController {

    @Autowired
    private OrdenServicioService ordenServicioService;

    @GetMapping
    public List<OrdenServicioResponseDTO> listarOrdenesServicio() {
        return ordenServicioService.getAllOrdenesServicio().stream()
                .map(OrdenServicioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenServicioResponseDTO> obtenerOrdenPorId(@PathVariable Long id) {
        return ordenServicioService.getOrdenServicioById(id)
                .map(orden -> new ResponseEntity<>(new OrdenServicioResponseDTO(orden), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generateOrderPdf(@PathVariable Long id) {
        OrdenServicio orden = ordenServicioService.getOrdenServicioById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con id: " + id));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            // Título
            Paragraph titulo = new Paragraph("FACTURA N° " + String.format("%03d", orden.getId()));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Información del Cliente
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String rucCliente = orden.getCliente() != null && orden.getCliente().getRuc() != null ? orden.getCliente().getRuc() : "N/A";
            String clienteInfo = "\n\nCliente: " + orden.getCliente().getNombres() + " " + orden.getCliente().getApellidos() + "\n" +
                                 "RUC: " + rucCliente + "\n" +
                                 "Fecha: " + sdf.format(orden.getFechaCreacion()) + "\n\n";
            document.add(new Paragraph(clienteInfo));

            // Tabla de Detalles
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 5, 2, 2});

            // Cabeceras
            table.addCell("Cant.");
            table.addCell("Descripción");
            table.addCell("P. Unitario");
            table.addCell("Importe");

            // Contenido de la tabla
            DecimalFormat df = new DecimalFormat("#,##0.00");
            if (orden.getDetalles() != null) {
                orden.getDetalles().forEach(detalle -> {
                    double importe = detalle.getCantidad() * detalle.getPrecioUnitario();
                    table.addCell(String.valueOf(detalle.getCantidad()));
                    table.addCell(detalle.getDescripcion());
                    table.addCell("S/ " + df.format(detalle.getPrecioUnitario()));
                    table.addCell("S/ " + df.format(importe));
                });
            }
            document.add(table);
            
            // Totales
            double total = orden.getMontoTotal() != null ? orden.getMontoTotal() : 0.0;
            double subtotal = total / 1.18;
            double igv = total - subtotal;

            Paragraph totales = new Paragraph(
                "\nSubtotal: S/ " + df.format(subtotal) + "\n" +
                "I.G.V. (18%): S/ " + df.format(igv) + "\n" +
                "Total: S/ " + df.format(total)
            );
            totales.setAlignment(Element.ALIGN_RIGHT);
            document.add(totales);

            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "factura-" + orden.getId() + ".pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}