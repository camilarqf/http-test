/*
package http.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/file")
@Slf4j
public class SaveController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo não enviado");
        }

        // Criar o diretório de destino, se não existir
        File destDir = new File(uploadDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        // Salvar o arquivo no diretório de destino
        String fileAbsolutePath = uploadDir + File.separator + file.getOriginalFilename();
        Files.copy(file.getInputStream(), new File(fileAbsolutePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        log.info("OK!");
        return ResponseEntity.ok("Arquivo salvo com sucesso");


    }

    @GetMapping("/read/{filename}")
    public ResponseEntity<String> readFile(@PathVariable String filename) {
        try {
            // Obter o recurso do arquivo
            Resource resource = new ClassPathResource(filename);
            System.out.println(resource);

            // Obter o caminho absoluto do arquivo
            String filePath = uploadDir + "/" + filename;
            System.out.println(filePath);

            // Criar o recurso do arquivo
            resource = new FileSystemResource(filePath);
            System.out.println(resource);

            // Verificar se o arquivo existe
            if (resource.exists()) {
                // Ler o conteúdo do arquivo
                InputStream inputStream = resource.getInputStream();
                byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
                String content = new String(bytes, StandardCharsets.UTF_8);

                return ResponseEntity.ok(content);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo não encontrado");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao ler o arquivo");
        }
    }
}
*/
