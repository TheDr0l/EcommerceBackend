package com.dilan.ecommerce.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            // Convertir MultipartFile a File
            File uploadedFile = convertMultiPartToFile(file);
            // Opciones de subida (ej. guardar en una carpeta "ecommerce-products")
            Map<Object, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("folder", "ecommerce-products");
            
            // Subir a Cloudinary
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, uploadOptions);
            
            // Eliminar el archivo temporal
            uploadedFile.delete();
            
            // Devolver la URL segura
            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al subir la imagen a Cloudinary", e);
        }
    }

    // Método auxiliar para convertir MultipartFile a File (requerido por Cloudinary)
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}