package fr.insee.bar.view;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import fr.insee.bar.model.Client;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ClientsPdfView extends AbstractPdfView{

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Client> clients = (List<Client>) model.get("clients");
        for (Client client : clients) {
            document.add(new Paragraph(String.format("%s — %s", client.getNom(), client.getEmail())));
        }

        clients.stream()
                .map(c -> c.getNom() + " — " + c.getEmail())
                .map(Paragraph::new)
                .forEach((element) -> {
                    try {
                        document.add(element);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                });


    }
}
